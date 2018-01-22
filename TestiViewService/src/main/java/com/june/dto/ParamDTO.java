package com.june.dto;

import com.june.enums.ProParamType;

public class ParamDTO {
	private ProParamType paramType;

    private Object paramValue;
    
    private byte[] paramBtye;

    public ParamDTO() {

    }

//    public ParamDTO(ProParamType pType, String pValue) {
//        paramType = pType;
//        paramValue = pValue;
//    }
    
    public ParamDTO(ProParamType pType,Object pValue) {
    	 paramType = pType;
         paramValue = pValue;
    }
    
    public ParamDTO(ProParamType pType,byte[] pValue) {
   	 paramType = pType;
   	 paramBtye = pValue;
}

    public ProParamType getParamType() {
        return paramType;
    }

    public void setParamType(ProParamType paramType) {
        this.paramType = paramType;
    }

    public Object getParamValue() {
        return paramValue;
    }

    public void setParamValue(Object paramValue) {
        this.paramValue = paramValue;
    }

	public byte[] getParamBtye() {
		return paramBtye;
	}

	public void setParamBtye(byte[] paramBtye) {
		this.paramBtye = paramBtye;
	}
}
