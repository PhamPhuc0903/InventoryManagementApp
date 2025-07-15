package com.example.com.inventorymanagement.data.database.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.javatime.datetime
import java.util.UUID

object UserTable : Table("users") {
    val userId = uuid("user_id").clientDefault { UUID.randomUUID() }
    val userName = varchar("user_name", 50).uniqueIndex()
    val password = varchar("password", 255)
    val phoneNumber = varchar("phone_number", 20).uniqueIndex()
    val email = varchar("email", 150).uniqueIndex().nullable()
    val dob = date("dob").nullable()
    val firstName = varchar("first_name", 100).nullable()
    val lastName = varchar("last_name", 100).nullable()
    val roleName = reference("role_name", RoleTable.roleName)
    val isActive = bool("is_active").default(true)
    override val primaryKey = PrimaryKey(userId)
}
