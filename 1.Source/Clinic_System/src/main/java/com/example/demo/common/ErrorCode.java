package com.example.demo.common;

import lombok.Getter;

@Getter
public enum ErrorCode {

    PROCESS_SUCCESS("00000", "All thing done"),
    AUTHENTICATION_FAILED("00002", "Authentication failed"),

    NOT_FOUND_ID("U001", "Not found ID = "),
    USER_EXIST("U002", "User exist"),
    NOT_FOUND_ROLE_ID("R001", "Not found role ID = "),
    NOT_FOUND_VILLAGE_ID("V001", "Not village ID = "),


    INTERNAL_SERVER_ERROR("00001", "Internal server error"),
    SUCCESS("2000", "SUCCESS"),
    FAIL("9999", "FAIL"),
    BAD_REQUEST("4004", "BAD_REQUEST"),
    DATA_NOT_FOUND("7007", "DATA_NOT_FOUND"),
    ERROR_DELETE_BOOKING("1005", "Không thể xóa lịch trực khi bác sỹ còn lịch hẹn với khách hàng"),
    ERROR_ADD_DOCTOR_CALENDER("1006", "Không thể thêm lịch trực khi phòng có đã có bác sỹ trực");

    private String key;
    private String value;

    private ErrorCode(String key, String value){
        this.key = key;
        this.value = value;
    }
}
