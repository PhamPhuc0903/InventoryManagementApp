package com.example.com.inventorymanagement.data.repositories.implementations

import com.example.com.inventorymanagement.data.database.tables.RolePermissionTable
import com.example.com.inventorymanagement.data.models.RolePermission
import com.example.com.inventorymanagement.data.repositories.interfaces.IRolePermissionRepository
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class RolePermissionRepository : IRolePermissionRepository {

    private fun rowToRolePermission(row: ResultRow): RolePermission = RolePermission(
        roleName = row[RolePermissionTable.roleName],
        permissionName = row[RolePermissionTable.permissionName]
    )

    override suspend fun getAll(): List<RolePermission> = newSuspendedTransaction(Dispatchers.IO) {
        RolePermissionTable.selectAll().map { rowToRolePermission(it) }
    }

    override suspend fun getByRoleName(roleName: String): List<RolePermission> = newSuspendedTransaction(Dispatchers.IO) {
        RolePermissionTable
            .selectAll()
            .where { RolePermissionTable.roleName eq roleName }
            .map { rowToRolePermission(it) }
    }

    override suspend fun getByPermissionName(permissionName: String): List<RolePermission> = newSuspendedTransaction(Dispatchers.IO) {
        RolePermissionTable
            .selectAll()
            .where { RolePermissionTable.permissionName eq permissionName }
            .map { rowToRolePermission(it) }
    }

    override suspend fun getByRoleAndPermission(roleName: String, permissionName: String): RolePermission? = newSuspendedTransaction(Dispatchers.IO) {
        RolePermissionTable
            .selectAll()
            .where {
                (RolePermissionTable.roleName eq roleName) and
                        (RolePermissionTable.permissionName eq permissionName)
            }
            .map { rowToRolePermission(it) }
            .singleOrNull()
    }

    override suspend fun create(rolePermission: RolePermission): RolePermission? = newSuspendedTransaction(Dispatchers.IO) {
        RolePermissionTable.insert {
            it[roleName] = rolePermission.roleName
            it[permissionName] = rolePermission.permissionName
        }
        getByRoleAndPermission(rolePermission.roleName, rolePermission.permissionName)
    }


    override suspend fun delete(roleName: String, permissionName: String): Boolean = newSuspendedTransaction(Dispatchers.IO) {
        RolePermissionTable.deleteWhere {
            (RolePermissionTable.roleName eq roleName) and
                    (RolePermissionTable.permissionName eq permissionName)
        } > 0
    }
}
