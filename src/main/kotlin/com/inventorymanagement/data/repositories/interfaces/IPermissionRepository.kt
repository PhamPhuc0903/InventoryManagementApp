package com.example.com.inventorymanagement.data.repositories.interfaces

import com.example.com.inventorymanagement.data.models.Permission

interface IPermissionRepository {
    suspend fun getAll(): List<Permission>
    suspend fun getByName(permissionName: String): Permission?
    suspend fun create(permission: Permission): Permission?
    suspend fun update(permission: Permission): Boolean
    suspend fun delete(permissionName: String): Boolean
}