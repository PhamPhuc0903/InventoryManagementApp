package com.example.com.inventorymanagement.data.database.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object AuditLogTable : Table("audit_logs") {
    val logId = integer("log_id").autoIncrement()
    val targetTable = varchar("target_table", 100)
    val recordId = varchar("record_id", 100)
    val action = varchar("action", 20) // "INSERT", "UPDATE", "DELETE"
    val oldValues = text("old_values").nullable()
    val newValues = text("new_values").nullable()
    val changedBy = reference("changed_by", UserTable.userId)
    val changedAt = datetime("changed_at").clientDefault { LocalDateTime.now() }
    override val primaryKey = PrimaryKey(logId)
}
