package com.hk.complier.syntax.parser.ll.ast;

import com.hk.complier.lexer.constant.Word;
import com.hk.complier.lexer.token.TokenPool;
import lombok.Data;

import java.util.List;

/**
 * @author : HK意境
 * @ClassName : SyntaxNode
 * @date : 2022/4/20 13:18
 * @description : 语法节点
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Data
public class SyntaxNode {

    // 当前节点
    private Word token;

    // 当前节点类型
    private String type ;

    // 当前节点缩进
    private int indent ;

    private TokenPool tokenPool ;

    // 当前节点的层次
    private int level ;

    // 父亲节点
    private Word parent;

    // 孩子节点
    private List<Word> children;

    // 节点属性
    private String attribute ;

    // 节点属于的表达式类型
    private String expType ;

}
