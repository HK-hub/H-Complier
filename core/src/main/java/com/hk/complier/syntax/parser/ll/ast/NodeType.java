package com.hk.complier.syntax.parser.ll.ast;

/**
 * @author : HK意境
 * @ClassName : NodeType
 * @date : 2022/5/9 15:42
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public enum NodeType {

    IntK ,IdK ,VoidK ,ConstK ,Var_DeclK , Array_DeclK, FunK ,ParamsK ,
    ParamK ,CompK, Selection_StmtK, Iteration_StmtK ,Return_StmtK ,AssginK ,
    OpK ,Array_ElemK ,CallK ,ArgsK ,UnknownK ,

}
