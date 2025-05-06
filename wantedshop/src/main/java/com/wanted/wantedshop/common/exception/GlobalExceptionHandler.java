package com.wanted.wantedshop.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
    private static final String field = "${field}";

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.OK)
    public BaseResDto exception(ServiceException e){
        BaseResDto baseResDto = new BaseResDto();
        baseResDto.setResultCode(e.getResultCode());
        baseResDto.setResultMessage(e.getResultMessage());
        log.info("ServiceException: code[{}], message[{}]" , baseResDto.getResultCode(), baseResDto.getResultMessage());
        return baseResDto;
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.OK)
    public BaseResDto exception(BindException e){
        BaseResDto baseResDto = new BaseResDto();
        FieldError fieldError = e.getBindingResult().getFieldError();

        if(fieldError == null){
            baseResDto.setResultCode(ResultCode.INTERNAL_ERROR.getResultCode());
            baseResDto.setResultMessage(ResultCode.INTERNAL_ERROR.getResultMessage());
            log.info("Internal Exception: code[{}]", ExceptionUtils.getStackTrace(e));
        }

    String code = fieldError.getCode();

    if ("NotNull".equals(code) || "NotEmpty".equals(code) || "NotBlank".equals(code)) {
        baseResDto.setResultCode(ResultCode.VALID_NOT_NULL.getResultCode());
        baseResDto.setResultMessage(ResultCode.VALID_NOT_NULL.getResultMessage().replace(field, fieldError.getField()));
    } else if ("Pattern".equals(code)) {
        baseResDto.setResultCode(ResultCode.VALID_NOT_REGEXP.getResultCode());
        baseResDto.setResultMessage(ResultCode.VALID_NOT_REGEXP.getResultMessage().replace(field, fieldError.getField()));
    }  else if ("MaxByte".equals(code)) {
        baseResDto.setResultCode(ResultCode.PARAM_NOT_VALID.getResultCode());
        baseResDto.setResultMessage(String.format("%s 값이 %dbyte 보다 큽니다.", fieldError.getRejectedValue(), fieldError.getArguments()[1]));
    } else {
        baseResDto.setResultCode(ResultCode.PARAM_NOT_VALID.getResultCode());
        baseResDto.setResultMessage(ResultCode.PARAM_NOT_VALID.getResultMessage());
    }
		log.error("ValidationException: message[{}]", e.getMessage());

        return baseResDto;
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.OK)
    public BaseResDto exception(MissingServletRequestParameterException e) {
        BaseResDto baseResDto = new BaseResDto();
        baseResDto.setResultCode(ResultCode.VALID_NOT_NULL.getResultCode());
        baseResDto.setResultMessage(ResultCode.VALID_NOT_NULL.getResultMessage().replace(field, e.getParameterName()));
        log.error("ValidationException: message[{}]", ExceptionUtils.getStackTrace(e));

        return baseResDto;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public BaseResDto exception(Exception e) {
        BaseResDto baseResDto = new BaseResDto();
        baseResDto.setResultCode(ResultCode.INTERNAL_ERROR.getResultCode());
        baseResDto.setResultMessage(ResultCode.INTERNAL_ERROR.getResultMessage());
        log.error("Internal Exception: {}", ExceptionUtils.getStackTrace(e));

        return baseResDto;
    }
}
