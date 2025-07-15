package com.example.com.inventorymanagement.data.repositories.implementations

import com.example.com.inventorymanagement.data.database.tables.PasswordResetTokenTable
import com.example.com.inventorymanagement.data.models.PasswordResetToken
import com.example.com.inventorymanagement.data.repositories.interfaces.IPasswordResetTokenRepository
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.UUID
import java.time.LocalDateTime

class PasswordResetTokenRepository : IPasswordResetTokenRepository {

    private fun rowToToken(row: ResultRow): PasswordResetToken = PasswordResetToken(
        passwordResetId = row[PasswordResetTokenTable.passwordResetId],
        userId = row[PasswordResetTokenTable.userId],
        contact = row[PasswordResetTokenTable.contact],
        otp = row[PasswordResetTokenTable.otp],
        expiredAt = row[PasswordResetTokenTable.expiredAt],
        isUsed = row[PasswordResetTokenTable.isUsed],
        createdAt = row[PasswordResetTokenTable.createdAt]
    )

    override suspend fun getAll(): List<PasswordResetToken> = newSuspendedTransaction(Dispatchers.IO) {
        PasswordResetTokenTable.selectAll().map { rowToToken(it) }
    }

    override suspend fun getById(passwordResetId: Int): PasswordResetToken? = newSuspendedTransaction(Dispatchers.IO) {
        PasswordResetTokenTable.selectAll()
            .where { PasswordResetTokenTable.passwordResetId eq passwordResetId }
            .map { rowToToken(it) }
            .singleOrNull()
    }

    override suspend fun getByUserId(userId: UUID): List<PasswordResetToken> = newSuspendedTransaction(Dispatchers.IO) {
        PasswordResetTokenTable.selectAll()
            .where { PasswordResetTokenTable.userId eq userId }
            .map { rowToToken(it) }
    }

    override suspend fun getActiveByContact(contact: String): PasswordResetToken? = newSuspendedTransaction(Dispatchers.IO) {
        PasswordResetTokenTable.selectAll()
            .where {
                (PasswordResetTokenTable.contact eq contact) and
                        (PasswordResetTokenTable.isUsed eq false) and
                        (PasswordResetTokenTable.expiredAt greaterEq LocalDateTime.now())
            }
            .orderBy(PasswordResetTokenTable.createdAt to SortOrder.DESC)
            .map { rowToToken(it) }
            .firstOrNull()
    }

    override suspend fun create(token: PasswordResetToken): PasswordResetToken? = newSuspendedTransaction(Dispatchers.IO) {
        PasswordResetTokenTable.insert {
            it[userId] = token.userId
            it[contact] = token.contact
            it[otp] = token.otp
            it[expiredAt] = token.expiredAt
            it[isUsed] = token.isUsed
            it[createdAt] = token.createdAt
        }
        PasswordResetTokenTable.selectAll()
            .orderBy(PasswordResetTokenTable.passwordResetId to SortOrder.DESC)
            .limit(1)
            .map { rowToToken(it) }
            .singleOrNull()
    }

    override suspend fun markUsed(passwordResetId: Int): Boolean = newSuspendedTransaction(Dispatchers.IO) {
        PasswordResetTokenTable.update({ PasswordResetTokenTable.passwordResetId eq passwordResetId }) {
            it[isUsed] = true
        } > 0
    }

    override suspend fun delete(passwordResetId: Int): Boolean = newSuspendedTransaction(Dispatchers.IO) {
        PasswordResetTokenTable.deleteWhere { PasswordResetTokenTable.passwordResetId eq passwordResetId } > 0
    }
}
