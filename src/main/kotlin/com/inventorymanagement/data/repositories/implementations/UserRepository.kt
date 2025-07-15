package com.example.com.inventorymanagement.data.repositories.implementations

import com.example.com.inventorymanagement.data.database.tables.UserTable
import com.example.com.inventorymanagement.data.models.User
import com.example.com.inventorymanagement.data.repositories.interfaces.IUserRepository
import com.example.com.inventorymanagement.domain.dto.request.user.UserFilterRequest
import com.example.com.inventorymanagement.domain.mappers.UserMapper.toFilterConditions
import com.example.com.inventorymanagement.domain.mappers.UserMapper.toUser
import com.example.com.inventorymanagement.domain.mappers.UserMapper.toUserTable
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.UUID
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class UserRepository : IUserRepository {

    override suspend fun getAll(): List<User> = newSuspendedTransaction(Dispatchers.IO) {
        UserTable.selectAll().map { it.toUser() }
    }

    override suspend fun getById(userId: UUID): User? = newSuspendedTransaction(Dispatchers.IO) {
        UserTable.select ( UserTable.userId eq userId )
            .map { it.toUser() }
            .singleOrNull()
    }

    override suspend fun getByPhone(phone: String): User? = newSuspendedTransaction(Dispatchers.IO) {
        UserTable.select ( UserTable.phoneNumber eq phone )
            .map { it.toUser() }
            .singleOrNull()
    }

    override suspend fun create(user: User): User? = newSuspendedTransaction(Dispatchers.IO) {
        UserTable.insert {it.toUserTable(user)}
        // Trả về User vừa insert
        user.copy(userId = user.userId)
    }

    override suspend fun filterUser(filter: UserFilterRequest): List<User> = newSuspendedTransaction(Dispatchers.IO) {
        val conditions = filter.toFilterConditions()

        val query = if (conditions.isNotEmpty()) {
            UserTable.selectAll().where { conditions.reduce { acc, op -> acc and op } }
        } else {
            UserTable.selectAll()
        }

        query.map { it.toUser() }
    }


    override suspend fun update(user: User): Boolean = newSuspendedTransaction(Dispatchers.IO) {
        val updatedRows = UserTable.update({ UserTable.userId eq user.userId }) {it.toUserTable(user)}
        updatedRows > 0
    }

    override suspend fun delete(userId: UUID): Boolean = newSuspendedTransaction(Dispatchers.IO) {
        UserTable.deleteWhere { UserTable.userId eq userId } > 0
    }
}
