package com.hk.complier.syntax;

/**
 * @author : HK意境
 * @ClassName : ArithmeticGrammar
 * @date : 2022/4/16 19:34
 * @description : 算数表达式的上下文无关文法：
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public enum ArithmeticGrammar {

    /**
     * 上下文无关文法
     *
     *
     *
     */


    // 语句
    STMT ,
    // 表达式
    EXPR ,
    // 表达式'
    EXPR_PRIME,
    // （数学运算中的）项
    TERM,
    // 项'
    TERM_PRIME,
    // 因子
    FACTOR,

    // 数值常量_标识符
    NUM_OR_ID,

    // 加法运算符 +
    PLUS ,

    // 分号;
    SEMI ,
    // 乘法运算符 *
    MULTIPLE ,

    //
    LEFT_PARENT ,
    RIGHT_PARENT ,

}
