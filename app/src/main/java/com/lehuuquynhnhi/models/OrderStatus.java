package com.lehuuquynhnhi.models;

public enum OrderStatus {
    ALL("tất cả loại hóa đơn"),
    COMPLETED("Các hóa đơn đã hoàn tất hành trình"),
    NOT_YET_PAYMENT("Hóa đơn chưa thanh toán"),
    GOING_LOGISTIC("Hóa đơn đang xử li logistic"),
    CUSTOMER_COMPLAIN("Khiếu nại khách hàng");

    private String description;

    private OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
