package com.example.com.inventorymanagement.domain.dto.response.invoice

import com.example.com.inventorymanagement.utils.serializers.BigDecimalSerializer
import java.math.BigDecimal
import kotlinx.serialization.Serializable

@Serializable
data class ItemInvoiceResponse(
    val itemId: Int,
    val quantity: Int,
    @Serializable(with = BigDecimalSerializer::class)
    val sellingPrice: BigDecimal,
    @Serializable(with = BigDecimalSerializer::class)
    val total: BigDecimal
)
