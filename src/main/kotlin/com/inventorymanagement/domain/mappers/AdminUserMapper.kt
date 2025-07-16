package com.example.com.inventorymanagement.domain.mappers

import com.example.com.inventorymanagement.data.database.tables.AdminUserTable
import com.example.com.inventorymanagement.data.models.AdminUser
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateStatement

object AdminUserMapper {
    fun ResultRow.toAdminUser(): AdminUser = AdminUser(
        adminId = this[AdminUserTable.adminId],
        userId = this[AdminUserTable.userId]
    )

    fun InsertStatement<Number>.toAdminUserTable(adminUser: AdminUser) {
        this[AdminUserTable.adminId] = adminUser.adminId
        this[AdminUserTable.userId] = adminUser.userId
    }
}