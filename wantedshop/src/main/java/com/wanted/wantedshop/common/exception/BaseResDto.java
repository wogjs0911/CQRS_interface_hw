package com.wanted.wantedshop.common.exception;

import com.wanted.wantedshop.common.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class BaseResDto extends BaseEntity {
    @Builder.Default
    private int resultCode = ResultCode.SUCCESS.getResultCode();

    @Builder.Default
    private String resultMessage = ResultCode.SUCCESS.getResultMessage();
}