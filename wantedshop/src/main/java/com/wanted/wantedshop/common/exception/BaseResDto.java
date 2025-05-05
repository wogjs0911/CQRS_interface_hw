package com.wanted.wantedshop.common.exception;

import com.wanted.wantedshop.common.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BaseResDto extends BaseEntity {
    private int resultCode = ResultCode.SUCCESS.getResultCode();
    private String resultMessage = ResultCode.SUCCESS.getResultMessage();
}