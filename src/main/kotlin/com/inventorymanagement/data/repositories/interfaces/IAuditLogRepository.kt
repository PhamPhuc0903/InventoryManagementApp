package com.example.com.inventorymanagement.data.repositories.interfaces

import com.example.com.inventorymanagement.data.models.AuditLog
import java.util.UUID

interface IAuditLogRepository {
    suspend fun getAll(): List<AuditLog>
    suspend fun getById(logId: Int): AuditLog?
    suspend fun getByTable(tableName: String): List<AuditLog>
    suspend fun getByRecordId(recordId: String): List<AuditLog>
    suspend fun getByUser(changedBy: UUID): List<AuditLog>
    suspend fun create(auditLog: AuditLog): AuditLog?
    suspend fun delete(logId: Int): Boolean
}
