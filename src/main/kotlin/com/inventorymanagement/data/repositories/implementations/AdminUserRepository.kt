package com.example.com.inventorymanagement.data.repositories.implementations

import com.example.com.inventorymanagement.data.database.tables.AdminUserTable
import com.example.com.inventorymanagement.data.models.AdminUser
import com.example.com.inventorymanagement.data.repositories.interfaces.IAdminUserRepository
import com.example.com.inventorymanagement.domain.mappers.AdminUserMapper.toAdminUser
import com.example.com.inventorymanagement.domain.mappers.AdminUserMapper.toAdminUserTable
import com.example.com.inventorymanagement.domain.mappers.UserMapper.toUserTable
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.UUID
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq


class AdminUserRepository: IAdminUserRepository {

    override suspend fun getAll(): List<AdminUser> = newSuspendedTransaction(Dispatchers.IO) {
        AdminUserTable.selectAll().map { it.toAdminUser() }
    }

    override suspend fun getByAdminId(adminId: UUID): List<AdminUser> = newSuspendedTransaction(Dispatchers.IO) {
        AdminUserTable.selectAll().where{AdminUserTable.adminId eq adminId}.map { it.toAdminUser() }
    }

    override suspend fun getByUserId(userId: UUID): List<AdminUser> = newSuspendedTransaction(Dispatchers.IO) {
        AdminUserTable.selectAll().where {AdminUserTable.userId eq userId}.map { it.toAdminUser() }
    }

    override suspend fun getByAdminIdAndUserId(adminId: UUID, userId: UUID): AdminUser? = newSuspendedTransaction(Dispatchers.IO) {
        AdminUserTable.selectAll().where{(AdminUserTable.adminId eq adminId) and (AdminUserTable.userId eq userId)}.map { it.toAdminUser() }.singleOrNull()
    }

    override suspend fun create(adminUser: AdminUser): AdminUser? = newSuspendedTransaction(Dispatchers.IO) {
        val insertResult = AdminUserTable.insert { it.toAdminUserTable(adminUser) }
        adminUser
    }

    override suspend fun delete(adminId: UUID, userId: UUID): Boolean = newSuspendedTransaction(Dispatchers.IO) {
        AdminUserTable.deleteWhere { (AdminUserTable.adminId eq adminId) and (AdminUserTable.userId eq userId) } > 0
    }
}