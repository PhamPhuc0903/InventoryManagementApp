package com.example.com.inventorymanagement.data.database

import com.example.com.inventorymanagement.data.database.tables.UserTable
import com.example.com.inventorymanagement.data.database.tables.AdminUserTable
import com.example.com.inventorymanagement.data.database.tables.AuditLogTable
import com.example.com.inventorymanagement.data.database.tables.InvoiceTable
import com.example.com.inventorymanagement.data.database.tables.ItemInvoiceTable
import com.example.com.inventorymanagement.data.database.tables.ItemTable
import com.example.com.inventorymanagement.data.database.tables.MonthlyDebtReportDetailTable
import com.example.com.inventorymanagement.data.database.tables.MonthlyDebtReportTable
import com.example.com.inventorymanagement.data.database.tables.PasswordResetTokenTable
import com.example.com.inventorymanagement.data.database.tables.PaymentReceiptTable
import com.example.com.inventorymanagement.data.database.tables.PermissionTable
import com.example.com.inventorymanagement.data.database.tables.RolePermissionTable
import com.example.com.inventorymanagement.data.database.tables.RoleTable
import com.example.com.inventorymanagement.data.database.tables.SpecialPriceTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction


object DatabaseFactory {

    /**
     * Khởi tạo kết nối database và tùy chọn tạo bảng tự động cho môi trường dev/local.
     * @param url: JDBC URL của database.
     * @param driver: Driver JDBC (ví dụ MySQL là "com.mysql.cj.jdbc.Driver").
     * @param user: Tên user kết nối DB.
     * @param password: Mật khẩu DB.
     * @param autoCreateTables: true nếu muốn tự động tạo bảng (chỉ nên dùng khi dev/test).
     */
    fun init(
        url: String = "jdbc:mysql://localhost:3307/inventory_management_app",
        driver: String = "com.mysql.cj.jdbc.Driver",
        user: String = "root",
        password: String = "root",
        autoCreateTables: Boolean = true
    ) {
        // Kết nối tới database
        Database.connect(
            url = url,
            driver = driver,
            user = user,
            password = password
        )

        if (autoCreateTables) {
            // Tự động tạo bảng nếu chưa có (dev/local)
            transaction {
                SchemaUtils.create(
                    // Đưa tên các bảng ở đây, ví dụ:
                    UserTable,
                    AdminUserTable,
                    RoleTable,
                    PermissionTable,
                    RolePermissionTable,
                    ItemTable,
                    SpecialPriceTable,
                    InvoiceTable,
                    ItemInvoiceTable,
                    PaymentReceiptTable,
                    MonthlyDebtReportTable,
                    MonthlyDebtReportDetailTable,
                    AuditLogTable,
                    PasswordResetTokenTable
                )
            }
        }
        // Nếu production, KHÔNG tự động tạo bảng ở đây!
    }
}
