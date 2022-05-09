package com.hk.complier.syntax.parser.ll.excption;

import com.hk.complier.lexer.constant.Word;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author : HK意境
 * @ClassName : SyntaxException
 * @date : 2022/4/20 11:22
 * @description : 语法分析中的错误
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Data
@ToString
@NoArgsConstructor
public class SyntaxException {

    // 出现错误的 token
    private Word errorToken ;

    // 第多少个 token 出现错误
    private int position ;

    // 错误信息
    private String errorMsg ;

    public SyntaxException(Word errorToken, int position, String errorMsg) {
        this.errorToken = errorToken;
        this.position = position;
        this.errorMsg = errorMsg;
    }
}
