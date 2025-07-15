package com.example.com.inventorymanagement.data.repositories.implementations

import com.example.com.inventorymanagement.data.database.tables.AdminUserTable
import com.example.com.inventorymanagement.data.models.AdminUser
import com.example.com.inventorymanagement.data.repositories.interfaces.IAdminUserRepository
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.UUID
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq


class AdminUserRepository: IAdminUserRepository {
    private fun rowToAdminUser(row: ResultRow): AdminUser = AdminUser(
        adminId = row[AdminUserTable.adminId],
        userId = row[AdminUserTable.userId]
    )

    override suspend fun getAll(): List<AdminUser> = newSuspendedTransaction(Dispatchers.IO) {
        AdminUserTable.selectAll().map { rowToAdminUser(it) }
    }

    override suspend fun getByAdminId(adminId: UUID): List<AdminUser> = newSuspendedTransaction(Dispatchers.IO) {
        AdminUserTable.selectAll().where{AdminUserTable.adminId eq adminId}.map { rowToAdminUser(it) }
    }

    override suspend fun getByUserId(userId: UUID): List<AdminUser> = newSuspendedTransaction(Dispatchers.IO) {
        AdminUserTable.selectAll().where {AdminUserTable.userId eq userId}.map { rowToAdminUser(it) }
    }

    override suspend fun getByAdminIdAndUserId(adminId: UUID, userId: UUID): AdminUser? = newSuspendedTransaction(Dispatchers.IO) {
        AdminUserTable.selectAll().where{(AdminUserTable.adminId eq adminId) and (AdminUserTable.userId eq userId)}.map { rowToAdminUser(it) }.singleOrNull()
    }

    override suspend fun create(adminUser: AdminUser): AdminUser? = newSuspendedTransaction(Dispatchers.IO) {
        val insertResult = AdminUserTable.insert {
            it[adminId] = adminUser.adminId
            it[userId] = adminUser.userId
        }
        AdminUserTable.selectAll().where { (AdminUserTable.adminId eq adminUser.adminId) and (AdminUserTable.userId eq adminUser.userId) }.map { rowToAdminUser(it) }.singleOrNull()
    }

    override suspend fun delete(adminId: UUID, userId: UUID): Boolean = newSuspendedTransaction(Dispatchers.IO) {
        AdminUserTable.deleteWhere { (AdminUserTable.adminId eq adminId) and (AdminUserTable.userId eq userId) } > 0
    }
}