package com.hk.complier.syntax.parser.ll.ast;

import com.hk.complier.lexer.constant.Word;
import com.hk.complier.lexer.constant.WordMappingPool;
import lombok.Data;

/**
 * @author : HK意境
 * @ClassName : AbstractSyntaxTree
 * @date : 2022/4/20 13:17
 * @description : 抽象语法树
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Data
public class AbstractSyntaxTree {

    // 根节点
    private SyntaxNode rootNode ;


    public static SyntaxNode newNode(Word token){

        SyntaxNode node = new SyntaxNode();

        String weakClass = token.getWeakClass();

        if ("".equals(weakClass) || weakClass == null || weakClass.length() <= 0){
            // weakclass 为止
            weakClass = WordMappingPool.getWordClassByTokenValue(token.getToken());
        }

        node.setType(weakClass);
        node.setAttribute(token.getValue());
        node.setToken(token);


        return node ;
    }



}
