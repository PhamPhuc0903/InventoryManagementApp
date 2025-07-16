package com.example.com.inventorymanagement.domain.mappers

import com.example.com.inventorymanagement.data.database.tables.RoleTable
import com.example.com.inventorymanagement.data.models.Role
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateStatement

object RoleMapper {
    fun ResultRow.toRole(): Role = Role(
        roleName = this[RoleTable.roleName],
        roleDescription = this[RoleTable.roleDescription]
    )

    fun InsertStatement<Number>.toRoleTable(role: Role) {
        this[RoleTable.roleName] = role.roleName
        this[RoleTable.roleDescription] = role.roleDescription
    }

    fun UpdateStatement.toRoleTable(role: Role) {
        this[RoleTable.roleDescription] = role.roleDescription
    }
}