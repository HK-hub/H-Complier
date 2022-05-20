package com.hk.complier.syntax.parser.ll.ast;


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
public class AbstractSyntaxTree {

    // 根节点
    private SyntaxNode rootNode ;


    public static SyntaxNode newNode(NodeType kind){

        SyntaxNode node = new SyntaxNode();

        // 设置兄弟节点
        node.setSibling(null);

        // 设置 token 类型
        node.setKind(kind);

        if (kind == NodeType.OpK || kind == NodeType.IntK || kind == NodeType.IdK){
            node.setAttribute("");
            node.setExpType(ExpType.Integer);
        }

        if (kind ==NodeType.ConstK){
            node.setAttribute("0");
        }

        return node ;
    }

    public SyntaxNode getRootNode() {
        return rootNode;
    }

    public void setRootNode(SyntaxNode rootNode) {
        this.rootNode = rootNode;
    }
}
