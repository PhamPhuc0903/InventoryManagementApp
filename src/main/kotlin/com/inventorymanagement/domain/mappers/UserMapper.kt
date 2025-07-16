package com.example.com.inventorymanagement.domain.mappers

import com.example.com.inventorymanagement.data.database.tables.UserTable
import com.example.com.inventorymanagement.data.models.User
import com.example.com.inventorymanagement.domain.dto.request.user.CreateUserRequest
import com.example.com.inventorymanagement.domain.dto.request.user.UpdateUserRequest
import com.example.com.inventorymanagement.domain.dto.request.user.UserFilterRequest
import com.example.com.inventorymanagement.domain.dto.response.user.UserListResponse
import com.example.com.inventorymanagement.domain.dto.response.user.UserResponse
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.like
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateStatement
import java.util.UUID

object UserMapper {
    fun toUser(userId: UUID, request: CreateUserRequest): User = User(
        userId = userId,
        userName = request.userName,
        password = request.password,
        phoneNumber = request.phoneNumber,
        email = request.email,
        dob = request.dob,
        firstName = request.firstName,
        lastName = request.lastName,
        roleName = request.roleName,
        isActive = true
    )

    fun toUser(request: UpdateUserRequest, existingUser: User): User = existingUser.copy(
        userName = request.userName ?: existingUser.userName,
        phoneNumber = request.phoneNumber ?: existingUser.phoneNumber,
        email = request.email ?: existingUser.email,
        dob = request.dob ?: existingUser.dob,
        firstName = request.firstName ?: existingUser.firstName,
        lastName = request.lastName ?: existingUser.lastName,
        roleName = request.roleName ?: existingUser.roleName,
        isActive = request.isActive ?: existingUser.isActive
    )

    fun UserFilterRequest.toFilterConditions(): List<Op<Boolean>> = buildList {
        userName?.let {
            add(UserTable.userName like "%$it%")
        }
        phoneNumber?.let {
            add(UserTable.phoneNumber eq it)
        }
        email?.let {
            add(UserTable.email like "%$it%")
        }
        roleName?.let {
            add(UserTable.roleName eq it)
        }
        isActive?.let {
            add(UserTable.isActive eq it)
        }
    }

    fun User.toUserResponse(): UserResponse = UserResponse(
        userId = this.userId,
        userName = this.userName,
        phoneNumber = this.phoneNumber,
        email = this.email,
        dob = this.dob,
        firstName = this.firstName,
        lastName = this.lastName,
        roleName = this.roleName,
        isActive = this.isActive
    )

    fun toUserListResponse(userModels: List<User>, totalUser: Long): UserListResponse = UserListResponse(
        users = userModels.map { it.toUserResponse() },
        total = totalUser
    )

    fun ResultRow.toUser(): User = User(
        userId = this[UserTable.userId],
        userName = this[UserTable.userName],
        password = this[UserTable.password],
        phoneNumber = this[UserTable.phoneNumber],
        email = this[UserTable.email],
        dob = this[UserTable.dob],
        firstName = this[UserTable.firstName],
        lastName = this[UserTable.lastName],
        roleName = this[UserTable.roleName],
        isActive = this[UserTable.isActive]
    )

    fun InsertStatement<Number>.toUserTable(user: User) {
        this[UserTable.userId] = user.userId
        this[UserTable.userName] = user.userName
        this[UserTable.password] = user.password
        this[UserTable.phoneNumber] = user.phoneNumber
        this[UserTable.email] = user.email
        this[UserTable.dob] = user.dob
        this[UserTable.firstName] = user.firstName
        this[UserTable.lastName] = user.lastName
        this[UserTable.roleName] = user.roleName
        this[UserTable.isActive] = user.isActive
    }

    fun UpdateStatement.toUserTable(user: User) {
        this[UserTable.userName] = user.userName
        this[UserTable.phoneNumber] = user.phoneNumber
        this[UserTable.email] = user.email
        this[UserTable.dob] = user.dob
        this[UserTable.firstName] = user.firstName
        this[UserTable.lastName] = user.lastName
        this[UserTable.roleName] = user.roleName
        this[UserTable.isActive] = user.isActive
    }
}