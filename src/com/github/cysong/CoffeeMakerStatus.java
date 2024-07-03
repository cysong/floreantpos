package com.github.cysong;

/**
 * @author cysong
 * @date 2024/6/26 11:51
 **/
public enum CoffeeMakerStatus {

    // 咖啡机处于关闭状态
    OFF(true, "The coffee maker is off"),

    // 咖啡机正在工作
    BUSYING(false, "The coffee maker is making coffee"),

    // 咖啡已酿造完成，准备接收新的订单
    READY(false, "The coffee is ready"),

    //咖啡豆已用尽
    OUT_OF_BEANS(true, "The coffee maker is out of beans"),

    //牛奶已用尽
    OUT_OF_MILK(true, "The coffee maker is out of milk"),

    // 咖啡机需要清洁
    NEEDS_CLEANING(true, "The coffee maker needs cleaning"),

    //无法识别的咖啡机状态
    UNKNOWN(true, "The coffee maker status unrecognized");

    private String description;

    private boolean error;

    CoffeeMakerStatus(boolean error, String description) {
        this.error = error;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public boolean isError() {
        return error;
    }

    public static CoffeeMakerStatus getByName(String name) {
        for (CoffeeMakerStatus status : CoffeeMakerStatus.values()) {
            if (status.name().equals(name)) {
                return status;
            }
        }
        return null;
    }
}

