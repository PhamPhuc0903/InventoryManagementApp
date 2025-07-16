package com.example.com.inventorymanagement.data.repositories.implementations

import com.example.com.inventorymanagement.data.database.tables.RoleTable
import com.example.com.inventorymanagement.data.models.Role
import com.example.com.inventorymanagement.data.repositories.interfaces.IRoleRepository
import com.example.com.inventorymanagement.domain.mappers.RoleMapper.toRole
import com.example.com.inventorymanagement.domain.mappers.RoleMapper.toRoleTable
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class RoleRepository : IRoleRepository {

    override suspend fun getAll(): List<Role> = newSuspendedTransaction(Dispatchers.IO) {
        RoleTable.selectAll().map { it.toRole() }
    }

    override suspend fun getById(roleName: String): Role? = newSuspendedTransaction(Dispatchers.IO) {
        RoleTable
            .selectAll()
            .where { RoleTable.roleName eq roleName }
            .map { it.toRole() }
            .singleOrNull()
    }

    override suspend fun create(role: Role): Role? = newSuspendedTransaction(Dispatchers.IO) {
        RoleTable.insert { it.toRoleTable(role)}
        role
    }

    override suspend fun update(role: Role): Boolean = newSuspendedTransaction(Dispatchers.IO) {
        RoleTable.update({
            RoleTable.roleName eq role.roleName
        }) { it.toRoleTable(role)} > 0
    }

    override suspend fun delete(roleName: String): Boolean = newSuspendedTransaction(Dispatchers.IO) {
        RoleTable.deleteWhere {
            RoleTable.roleName eq roleName
        } > 0
    }
}
