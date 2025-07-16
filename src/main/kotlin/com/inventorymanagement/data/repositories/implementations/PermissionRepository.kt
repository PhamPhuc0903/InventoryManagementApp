package com.example.com.inventorymanagement.data.repositories.implementations

import com.example.com.inventorymanagement.data.database.tables.PermissionTable
import com.example.com.inventorymanagement.data.models.Permission
import com.example.com.inventorymanagement.data.repositories.interfaces.IPermissionRepository
import com.example.com.inventorymanagement.domain.mappers.PermissionMapper.toPermission
import com.example.com.inventorymanagement.domain.mappers.PermissionMapper.toPermissionTable
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class PermissionRepository : IPermissionRepository {

    override suspend fun getAll(): List<Permission> = newSuspendedTransaction(Dispatchers.IO) {
        PermissionTable.selectAll().map { it.toPermission() }
    }

    override suspend fun getByName(permissionName: String): Permission? = newSuspendedTransaction(Dispatchers.IO) {
        PermissionTable
            .selectAll()
            .where { PermissionTable.permissionName eq permissionName }
            .map { it.toPermission() }
            .singleOrNull()
    }

    override suspend fun create(permission: Permission): Permission? = newSuspendedTransaction(Dispatchers.IO) {
        PermissionTable.insert { it.toPermissionTable(permission)}
        permission
    }

    override suspend fun update(permission: Permission): Boolean = newSuspendedTransaction(Dispatchers.IO) {
        PermissionTable.update({
            PermissionTable.permissionName eq permission.permissionName
        }) { it.toPermissionTable(permission)} > 0
    }

    override suspend fun delete(permissionName: String): Boolean = newSuspendedTransaction(Dispatchers.IO) {
        PermissionTable.deleteWhere {
            PermissionTable.permissionName eq permissionName
        } > 0
    }
}
