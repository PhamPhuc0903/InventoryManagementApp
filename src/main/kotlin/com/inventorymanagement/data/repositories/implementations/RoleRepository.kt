package com.example.com.inventorymanagement.data.repositories.implementations

import com.example.com.inventorymanagement.data.database.tables.RoleTable
import com.example.com.inventorymanagement.data.models.Role
import com.example.com.inventorymanagement.data.repositories.interfaces.IRoleRepository
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class RoleRepository : IRoleRepository {

    private fun rowToRole(row: ResultRow): Role = Role(
        roleName = row[RoleTable.roleName],
        roleDescription = row[RoleTable.roleDescription]
    )

    override suspend fun getAll(): List<Role> = newSuspendedTransaction(Dispatchers.IO) {
        RoleTable.selectAll().map { rowToRole(it) }
    }

    override suspend fun getById(roleName: String): Role? = newSuspendedTransaction(Dispatchers.IO) {
        RoleTable
            .selectAll()
            .where { RoleTable.roleName eq roleName }
            .map { rowToRole(it) }
            .singleOrNull()
    }

    override suspend fun create(role: Role): Role? = newSuspendedTransaction(Dispatchers.IO) {
        RoleTable.insert {
            it[roleName] = role.roleName
            it[roleDescription] = role.roleDescription
        }

        RoleTable
            .selectAll()
            .where { RoleTable.roleName eq role.roleName }
            .map { rowToRole(it) }
            .singleOrNull()
    }

    override suspend fun update(role: Role): Boolean = newSuspendedTransaction(Dispatchers.IO) {
        RoleTable.update({
            RoleTable.roleName eq role.roleName
        }) {
            it[roleDescription] = role.roleDescription
        } > 0
    }

    override suspend fun delete(roleName: String): Boolean = newSuspendedTransaction(Dispatchers.IO) {
        RoleTable.deleteWhere {
            RoleTable.roleName eq roleName
        } > 0
    }
}
