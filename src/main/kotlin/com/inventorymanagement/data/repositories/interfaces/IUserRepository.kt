package com.example.com.inventorymanagement.data.repositories.interfaces

import com.example.com.inventorymanagement.data.models.User
import com.example.com.inventorymanagement.domain.dto.request.user.UserFilterRequest
import java.util.UUID

interface IUserRepository {
    suspend fun getAll(): List<User>
    suspend fun getById(userId: UUID): User?
    suspend fun getByPhone(phone: String): User?

    suspend fun filterUser(filter: UserFilterRequest): List<User>
    suspend fun create(user: User): User?
    suspend fun update(user: User): Boolean
    suspend fun delete(userId: UUID): Boolean
}
