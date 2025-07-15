package com.example.com.inventorymanagement.data.database.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object PasswordResetTokenTable : Table("password_reset_tokens") {
    val passwordResetId = integer("password_reset_id").autoIncrement()
    val userId = reference("user_id", UserTable.userId)
    val contact = varchar("contact", 100)
    val otp = varchar("otp", 10)
    val expiredAt = datetime("expired_at")
    val isUsed = bool("is_used").default(false)
    val createdAt = datetime("created_at").clientDefault { LocalDateTime.now() }
    override val primaryKey = PrimaryKey(passwordResetId)
}
