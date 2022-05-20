package com.hk.complier.syntax.parser.ll.ast;

import com.hk.complier.lexer.constant.Word;
import com.hk.complier.lexer.token.TokenPool;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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
public class SyntaxNode {

    // 当前节点
    private Word token;

    // 当前节点类型
    private NodeType kind ;

    // 当前表达式类型
    // 节点属于的表达式类型
    private ExpType expType ;

    // 当前节点缩进
    private int indent ;

    private TokenPool tokenPool ;

    // 当前节点的层次
    private int level ;

    // 父亲节点
    private SyntaxNode parent;

    // 孩子节点
    private List<SyntaxNode> children = new ArrayList<>(4);

    //兄弟节点
    private SyntaxNode sibling ;

    // 节点属性
    private String attribute ;

    public SyntaxNode(Word token, NodeType kind) {
        this.token = token;
        this.kind = kind;
    }

    public SyntaxNode(NodeType kind) {
        this.kind = kind;
    }

    public SyntaxNode() {
    }


    public Word getToken() {
        return token;
    }

    public void setToken(Word token) {
        this.token = token;
    }

    public NodeType getKind() {
        return kind;
    }

    public void setKind(NodeType kind) {
        this.kind = kind;
    }

    public ExpType getExpType() {
        return expType;
    }

    public void setExpType(ExpType expType) {
        this.expType = expType;
    }

    public int getIndent() {
        return indent;
    }

    public void setIndent(int indent) {
        this.indent = indent;
    }

    public TokenPool getTokenPool() {
        return tokenPool;
    }

    public void setTokenPool(TokenPool tokenPool) {
        this.tokenPool = tokenPool;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent = parent;
    }

    public List<SyntaxNode> getChildren() {
        return children;
    }

    public void setChildren(List<SyntaxNode> children) {
        this.children = children;
    }

    public SyntaxNode getSibling() {
        return sibling;
    }

    public void setSibling(SyntaxNode sibling) {
        this.sibling = sibling;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }
}
