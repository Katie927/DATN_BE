package com.DATN.Bej.enums;


public enum NotificationType {

    /**
     * Đơn hàng mới được tạo thành công.
     * Client nên điều hướng đến màn hình chi tiết đơn hàng.
     */
    ORDER_PLACED,
    
    /**
     * Trạng thái đơn hàng bị thay đổi (đang xử lý, đang giao, đã giao, đã hủy).
     */
    ORDER_STATUS_UPDATE,

    // --- Module Sửa Chữa ---
    
    /**
     * Yêu cầu sửa chữa được tiếp nhận.
     */
    REPAIR_REQUEST_RECEIVED,
    
    /**
     * Trạng thái sửa chữa bị thay đổi (đang chẩn đoán, chờ linh kiện, đã sửa xong).
     */
    REPAIR_STATUS_UPDATE,
    
    /**
     * Kỹ thuật viên gửi một tin nhắn hoặc báo giá mới.
     */
    REPAIR_TECHNICIAN_MESSAGE,

    // --- Module Chung & Khuyến Mãi ---
    
    /**
     * Có một voucher, khuyến mãi mới.
     */
    NEW_PROMOTION,
    
    /**
     * Tin nhắn từ admin, hoặc thông báo chung toàn hệ thống.
     */
    GENERAL_ANNOUNCEMENT
}
