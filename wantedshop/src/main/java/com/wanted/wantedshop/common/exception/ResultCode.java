package com.wanted.wantedshop.common.exception;

import lombok.Getter;

@Getter
public enum ResultCode {
    SUCCESS(200, ResultMessage.SUCCESS),
    SUCCESS_PRODUCTS(200, ResultMessage.SUCCESS_PRODUCTS),
    INVALID_INPUT(401, ResultMessage.INVALID_INPUT),
    UNAUTHORIZED(401, ResultMessage.UNAUTHORIZED),
    FORBIDDEN(403, ResultMessage.FORBIDDEN),
    RESOURCE_NOT_FOUND(404, ResultMessage.RESOURCE_NOT_FOUND),
    CONFLICT(409, ResultMessage.CONFLICT),
    INTERNAL_ERROR(500, ResultMessage.INTERNAL_ERROR),
    ACCESS_NO_AUTH(1_000, ResultMessage.ACCESS_NO_AUTH),
    ACCESS_TOKEN_EXPIRED(1_001, ResultMessage.ACCESS_TOKEN_EXPIRED),
    REFRESH_TOKEN_EXPIRED(1_002, ResultMessage.REFRESH_TOKEN_EXPIRED),
    VALID_NOT_NULL(1_006, ResultMessage.VALID_NOT_NULL),
    VALID_NOT_PHONE_NUM(1_007, ResultMessage.VALID_NOT_PHONE_NUM),
    VALID_NOT_PASSWORD(1_008, ResultMessage.VALID_NOT_PASSWORD),
    VALID_NOT_REGEXP(1_009, ResultMessage.VALID_NOT_REGEXP),
    DUPLICATE_INFO(1_010, ResultMessage.DUPLICATE_INFO),
    MEMBER_NOT_EXIST(1_012, ResultMessage.MEMBER_NOT_EXIST),
    LOGIN_REQUIRED(1_019, ResultMessage.LOGIN_REQUIRED),
    PARAM_NOT_VALID(2_000, ResultMessage.PARAM_NOT_VALID),
    ;

    private final int resultCode;
    private final String resultMessage;

    ResultCode(int resultCode, String resultMessage) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }

    public interface ResultMessage{
        String SUCCESS = "요청이 성공적으로 처리되었습니다.";
        String SUCCESS_PRODUCTS = "상품 목록을 성공적으로 조회했습니다.";
        String INVALID_INPUT = "잘못된 입력 데이터";
        String RESOURCE_NOT_FOUND = "인증되지 않은 요청";
        String FORBIDDEN = "권한이 없는 요청";
        String UNAUTHORIZED = "요청한 리소스를 찾을 수 없음";
        String CONFLICT = "리소스 충돌 발생";
        String INTERNAL_ERROR = "서버 내부 오류";
        String ACCESS_NO_AUTH = "접근 권한이 없습니다.";
        String ACCESS_TOKEN_EXPIRED = "Access Token이 만료되었습니다.";
        String REFRESH_TOKEN_EXPIRED = "Refresh Token이 만료되었습니다.";
        String VALID_NOT_NULL = "입력한 정보가 없습니다.";
        String VALID_NOT_PHONE_NUM = "가입되지 않은 핸드폰 번호 입니다.";
        String VALID_NOT_PASSWORD = "잘못된 비밀번호 입니다.";
        String VALID_NOT_REGEXP = "형식이 올바르지 않습니다.";
        String DUPLICATE_INFO = "중복된 정보가 있습니다.";
        String MEMBER_NOT_EXIST = "존재하지 않는 회원입니다.";
        String LOGIN_REQUIRED = "로그인이 필요합니다.";
        String PARAM_NOT_VALID = "파라미터 오류입니다.";
    }
}
