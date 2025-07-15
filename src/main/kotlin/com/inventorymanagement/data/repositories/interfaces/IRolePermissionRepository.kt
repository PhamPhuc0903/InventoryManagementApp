package com.example.com.inventorymanagement.data.repositories.interfaces

import com.example.com.inventorymanagement.data.models.RolePermission

interface IRolePermissionRepository {
    suspend fun getAll(): List<RolePermission>
    suspend fun getByRoleName(roleName: String): List<RolePermission>
    suspend fun getByPermissionName(permissionName: String): List<RolePermission>
    suspend fun getByRoleAndPermission(roleName: String, permissionName: String): RolePermission?
    suspend fun create(rolePermission: RolePermission): RolePermission?
    suspend fun delete(roleName: String, permissionName: String): Boolean
}
