package com.example.com.inventorymanagement.data.repositories.interfaces

import com.example.com.inventorymanagement.data.models.PasswordResetToken
import java.util.UUID

interface IPasswordResetTokenRepository {
    suspend fun getAll(): List<PasswordResetToken>
    suspend fun getById(passwordResetId: Int): PasswordResetToken?
    suspend fun getByUserId(userId: UUID): List<PasswordResetToken>
    suspend fun getActiveByContact(contact: String): PasswordResetToken?
    suspend fun create(token: PasswordResetToken): PasswordResetToken?
    suspend fun markUsed(passwordResetId: Int): Boolean // Đánh dấu là đã dùng
    suspend fun delete(passwordResetId: Int): Boolean // Xoá token nếu cần
}
