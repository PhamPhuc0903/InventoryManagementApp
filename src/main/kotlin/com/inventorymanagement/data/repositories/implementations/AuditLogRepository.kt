package com.example.com.inventorymanagement.data.repositories.implementations

import com.example.com.inventorymanagement.data.database.tables.AuditLogTable
import com.example.com.inventorymanagement.data.models.AuditLog
import com.example.com.inventorymanagement.data.repositories.interfaces.IAuditLogRepository
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.UUID

class AuditLogRepository : IAuditLogRepository {

    private fun rowToAuditLog(row: ResultRow): AuditLog = AuditLog(
        logId = row[AuditLogTable.logId],
        targetTable = row[AuditLogTable.targetTable],
        recordId = row[AuditLogTable.recordId],
        action = row[AuditLogTable.action],
        oldValues = row[AuditLogTable.oldValues],
        newValues = row[AuditLogTable.newValues],
        changedBy = row[AuditLogTable.changedBy],
        changedAt = row[AuditLogTable.changedAt]
    )

    override suspend fun getAll(): List<AuditLog> = newSuspendedTransaction(Dispatchers.IO) {
        AuditLogTable.selectAll().map { rowToAuditLog(it) }
    }

    override suspend fun getById(logId: Int): AuditLog? = newSuspendedTransaction(Dispatchers.IO) {
        AuditLogTable.selectAll()
            .where { AuditLogTable.logId eq logId }
            .map { rowToAuditLog(it) }
            .singleOrNull()
    }

    override suspend fun getByTable(tableName: String): List<AuditLog> = newSuspendedTransaction(Dispatchers.IO) {
        AuditLogTable.selectAll()
            .where { AuditLogTable.targetTable eq tableName }
            .map { rowToAuditLog(it) }
    }

    override suspend fun getByRecordId(recordId: String): List<AuditLog> = newSuspendedTransaction(Dispatchers.IO) {
        AuditLogTable.selectAll()
            .where { AuditLogTable.recordId eq recordId }
            .map { rowToAuditLog(it) }
    }

    override suspend fun getByUser(changedBy: UUID): List<AuditLog> = newSuspendedTransaction(Dispatchers.IO) {
        AuditLogTable.selectAll()
            .where { AuditLogTable.changedBy eq changedBy }
            .map { rowToAuditLog(it) }
    }

    override suspend fun create(auditLog: AuditLog): AuditLog? = newSuspendedTransaction(Dispatchers.IO) {
        AuditLogTable.insert {
            it[targetTable] = auditLog.targetTable
            it[recordId] = auditLog.recordId
            it[action] = auditLog.action
            it[oldValues] = auditLog.oldValues
            it[newValues] = auditLog.newValues
            it[changedBy] = auditLog.changedBy
            it[changedAt] = auditLog.changedAt
        }
        AuditLogTable.selectAll()
            .orderBy(AuditLogTable.logId to SortOrder.DESC)
            .limit(1)
            .map { rowToAuditLog(it) }
            .singleOrNull()
    }

    override suspend fun delete(logId: Int): Boolean = newSuspendedTransaction(Dispatchers.IO) {
        AuditLogTable.deleteWhere { AuditLogTable.logId eq logId } > 0
    }
}
