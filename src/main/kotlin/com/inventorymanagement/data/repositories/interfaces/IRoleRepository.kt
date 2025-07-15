package com.example.com.inventorymanagement.data.repositories.interfaces

import com.example.com.inventorymanagement.data.models.Role

interface IRoleRepository {
    suspend fun getAll(): List<Role>
    suspend fun getById(roleName: String): Role?
    suspend fun create(role: Role): Role?
    suspend fun update(role: Role): Boolean
    suspend fun delete(roleName: String): Boolean
}