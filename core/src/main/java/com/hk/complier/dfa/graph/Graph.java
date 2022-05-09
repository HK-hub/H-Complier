package com.hk.complier.dfa.graph;

import java.io.Serializable;

/**
 * @ClassName : Graph
 * @author : HK意境
 * @date : 2022/4/3
 * @description : 我们可以把正则表达式分析转换为一棵树型结构，正则表达式包括中缀符号，后缀符号，双目符号，多目符号等。
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class Graph implements Serializable{
    public enum GraphType{
        undigraph,digraph,undinetwork,dinetwork
    }
    public int vexnum;
    public int edgenum;
    public GraphType type;
    public Graph(){}
}

