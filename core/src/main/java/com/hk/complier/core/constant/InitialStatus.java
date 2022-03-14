package com.hk.complier.core.constant;

/**
 * @author : HK意境
 * @ClassName : InitialStatus
 * @date : 2022/3/13 19:43
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public enum InitialStatus {
    Space_Status(0),
    Char_Status(1),
    Number_Status(2),
    Operator_Status(3),
    Delimiter_Status(4),
    SingleQuotes_Status(5),
    DoubleQuotes_Status(6),
    Other_Status(7),
    ;

    public int statusCode;

    InitialStatus(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
