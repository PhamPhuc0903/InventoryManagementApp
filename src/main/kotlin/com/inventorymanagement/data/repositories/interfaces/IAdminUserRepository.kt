package com.example.com.inventorymanagement.data.repositories.interfaces

import java.util.UUID
import com.example.com.inventorymanagement.data.models.AdminUser

interface IAdminUserRepository {
    suspend fun getAll(): List<AdminUser>
    suspend fun getByAdminId(adminId: UUID): List<AdminUser>
    suspend fun getByUserId(userId: UUID): List<AdminUser>
    suspend fun getByAdminIdAndUserId(adminId: UUID, userId: UUID): AdminUser?
    suspend fun create(adminUser: AdminUser): AdminUser?
    suspend fun delete(adminId: UUID, userId: UUID): Boolean
}
