package com.bevelop.devbevelop.global.common.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResult {

    @ApiModelProperty(value = "응답 성공여부 : true/false")
    private boolean success;

    @ApiModelProperty(value = "응답 코드 번호 : > 0 정상, < 0 비정상")
    private int code;

    @ApiModelProperty(value = "응답 메시지")
    private String msg;
    
    @Override
    public boolean equals(Object obj) {
    	if(!(obj instanceof CommonResult)) {
    		return false;
    	}
    	
    	CommonResult o = (CommonResult) obj;
    	return o.success==this.success && o.code==this.code && o.msg.equals(this.msg);
    }

	@Override
	public String toString() {
		return "CommonResult [success=" + success + ", code=" + code + ", msg=" + msg + "]";
	}
    
    
}