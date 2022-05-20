package com.hk.complier.syntax.parser.ll;

import com.hk.complier.common.FileReaderUtil;
import com.hk.complier.lexer.constant.Word;
import com.hk.complier.lexer.constant.WordDefinition;
import com.hk.complier.lexer.constant.WordMappingPool;
import com.hk.complier.lexer.constant.WordStatus;
import com.hk.complier.lexer.handle.AnalyzerHandler;
import com.hk.complier.lexer.lexicalnalyzer.LexicalAnalyzer;
import com.hk.complier.lexer.token.TokenPool;
import com.hk.complier.syntax.parser.ll.ast.AbstractSyntaxTree;
import com.hk.complier.syntax.parser.ll.ast.NodeType;
import com.hk.complier.syntax.parser.ll.ast.SyntaxNode;
import com.hk.complier.syntax.parser.ll.excption.SyntaxException;
import lombok.Data;
import org.w3c.dom.Node;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author : HK意境
 * @ClassName : SyntaxParser
 * @date : 2022/4/17 15:48
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Data
public class SyntaxParser {


    // token 池
    private TokenPool tokenPool ;

    // 词法分析器
    private LexicalAnalyzer lexer ;

    // 记录语法树
    public AbstractSyntaxTree ast ;

    // 记录当前 token
    public Word token ;

    // 记录语法解析过程中语法推到选择的策略
    public Map<String,List<String>> parseTrance = new HashMap<String,List<String>>();

    // 记录语法分析过程中的错误
    private List<SyntaxException> syntaxErrorList = new ArrayList<>();


    // 获取下一个 token 的快捷方法
    public Word getNextToken(){
        return this.tokenPool.getNextToken();
    }

    // 期望匹配的 token
    public boolean match(Word expectedToken){

        if(this.token.getValue().equals(expectedToken.getValue())){
            this.token = this.getNextToken();
            return true;
        }else{
            this.syntaxErrorList.add(new SyntaxException(expectedToken,"当前token不是期望得到的token"));

            // 打印保存 token 信息
            return false ;
        }
    }


    // 匹配 或者 类型匹配
    public boolean matchOrType(Word expectedToken , String type){

        // 获取 当前 token 的 weakClass
        String wordClassByTokenValue = WordMappingPool.getWordClassByTokenValue(this.token.getToken());
        assert wordClassByTokenValue != null;
        // 值相等
        boolean res = this.token.getValue().equals(expectedToken.getValue());
        if (wordClassByTokenValue.equals(type) ||res){
            this.token = this.getNextToken();
            return true ;
        }else{
            this.syntaxErrorList.add(new SyntaxException(expectedToken,"当前token不是期望得到的token"));
            // 打印保存 token 信息
            return false ;
        }
    }

    // 匹配 token 值
    public boolean matchOrToken(Word expectedToken , Integer tokenValue){

        // 获取 当前 token 的 weakClass
        if (this.token.getToken().equals(tokenValue)){
            this.token = this.getNextToken();
            return true ;
        }else{
            this.syntaxErrorList.add(new SyntaxException(expectedToken,"当前token不是期望得到的token"));
            // 打印保存 token 信息
            return false ;
        }
    }

    // 词法分析
    public void doLexical() throws IOException {

        this.lexer = new LexicalAnalyzer();

        System.out.println("输入测试数据文件路径：");
        Scanner scanner = new Scanner(System.in);

        // 输入算数表达式
        String filePath = scanner.nextLine();

        StringBuilder source = FileReaderUtil.readLineToListPool(new File(filePath));

        // 执行词法分析
        AnalyzerHandler.run(source,this.lexer);

        // 添加结束符号
        this.lexer.getWordPool().getWordDefinitionList().add(new WordDefinition(new Word("#","#",10000)));

        // 设置 TokenPool
        this.tokenPool = new TokenPool(this.lexer) ;

        // 输出 token
        this.tokenPool.getWordDefinitionList().forEach((item) -> {
            System.out.println(item.getWord());
        });

    }

    
    /**
     * @methodName : 语法解析程序入口
     * @author : HK意境
     * @date : 2022/4/20 13:21
     * @description :
     * @Todo :
     * @params : 
         * @param : null 
     * @return : null
     * @throws: 
     * @Bug :
     * @Modified :
     * @Version : 1.0
     */
    // program -> declaration_list
    public AbstractSyntaxTree parse() throws IOException {

        // 先进行词法分析
        this.doLexical();

        // 抽象语法树
        SyntaxNode rootNode ;

        // 获取开始节点
        this.token = this.tokenPool.getNextToken();

        // 处理声明列表
        rootNode = this.declarationList();

        if (!this.token.getToken().equals(WordMappingPool.getTokenValue("#"))){
            // 已经结束
            this.syntaxErrorList.add(new SyntaxException(this.token ,"程序非法的文法结束"));
        }

        this.ast.setRootNode(rootNode);

        return this.ast ;
    }


    // 处理声明列表
    // delcaration_list -> declaration { declaration }
    private SyntaxNode declarationList() {

        SyntaxNode targetNode = declaration();

        SyntaxNode p = targetNode ;

        // 程序以变量声明开始
        // 获取开始 token 类型
        String value = this.token.getValue();
        while(!"int".equals(value) && !"void".equals(value) && !"#".equals(value)){
            // 声明类型错误
            this.syntaxErrorList.add(new SyntaxException(this.token, "程序开始声明类型错误"));

            this.token = this.tokenPool.getNextToken();
            if ("#".equals(this.token.getValue())){
                // 结束语法分析
                break;
            }
        }

        // 开始类型解析
        while ("int".equals(value) || "void".equals(value)){
            SyntaxNode q = new SyntaxNode();
            q = declaration();

            if (q != null) {
                if (targetNode == null) {
                    targetNode = p = q ;
                }
                else{
                    p.setSibling(q);
                    p = q ;
                }
            }
        }

        // 匹配结束
        match(new Word("#","#",10000));

        return targetNode ;
    }



    // 声明语句文法推到
    // declaration->var_declaration | function_declaration
    // var_declaration->type_specifier ID; | type_specifier ID [NUM];
    // function_declaration->type_specifier ID (params) | compount_stmt
    // type_specifier->int | void
    private SyntaxNode declaration() {

        SyntaxNode targetNode = new SyntaxNode();
        SyntaxNode p = null;
        SyntaxNode q = null;
        SyntaxNode s = null;
        SyntaxNode a = null;

        if("int".equals(this.token.getValue())){
            p = AbstractSyntaxTree.newNode(NodeType.IntK);
            this.match(Word.createToken("int", 1105));

        }else if("void".equals(this.token.getValue())){

            p = AbstractSyntaxTree.newNode(NodeType.VoidK);
            this.match(Word.createToken("void",WordMappingPool.getTokenValue("void")));

        }else {

            // 错误的声明类型
            this.syntaxErrorList.add(new SyntaxException(this.token , "错误的声明类型"));
        }

        if (p != null && "ID".equals(this.token.getWeakClass())) {

            // 标识符
            q = AbstractSyntaxTree.newNode(NodeType.IdK);
            q.setAttribute(this.token.getValue());

            // 匹配标识符
            this.matchOrType(new Word(this.token.getValue(),this.token.getValue()), "ID");

            // 匹配 左括号， 判断是否为 函数
            if("(".equals(this.token.getValue())){

                // 匹配函数
                targetNode = AbstractSyntaxTree.newNode(NodeType.FunK);

                // 设置 targetNode 节点的 孩子节点， p 是 t 的子节点
                targetNode.getChildren().set(0,p) ;
                targetNode.getChildren().set(1,q) ;

                // 匹配左括号
                this.match(new Word("(","(", WordMappingPool.getTokenValue("(")));

                // 匹配参数 获取参数节点
                SyntaxNode paramsNode =  this.params();
                targetNode.getChildren().set(2,paramsNode);

                // 匹配 右括号
                this.match(new Word(")", ")", WordMappingPool.getTokenValue(")")));

                // 处理复合语句
                SyntaxNode compoundNode = this.compoundStmt();
                targetNode.getChildren().set(3,compoundNode);

            }else if("[".equals(this.token.getValue())){
                // 中括号，数组声明
                targetNode = AbstractSyntaxTree.newNode(NodeType.Var_DeclK);
                a = AbstractSyntaxTree.newNode(NodeType.Array_DeclK);

                targetNode.getChildren().set(0,p);
                targetNode.getChildren().set(1,a);
                this.match(new Word("[","[",WordMappingPool.getTokenValue("[")));

                s = AbstractSyntaxTree.newNode(NodeType.ConstK);
                s.setAttribute(this.token.getValue());

                // 匹配一个数字
                this.matchOrToken(new Word("number","number"),4200);

                a.getChildren().set(0,q);
                a.getChildren().set(1,s);

                // 匹配 ], 分号
                this.match(new Word("]","]",WordMappingPool.getTokenValue("]")));
                this.match(new Word(";",","));

            }else if (";".equals(this.token.getValue())){
                // 分号结尾, 普通变量声明
                targetNode = AbstractSyntaxTree.newNode(NodeType.Var_DeclK);
                targetNode.getChildren().set(0,p);
                targetNode.getChildren().set(1,q);

                // 匹配分号
                this.match(new Word(";",","));

            }else {

                // 语法错误
                this.syntaxErrorList.add(new SyntaxException(this.token, "错误的类型声明符号"));

            }

        }else{

            // 不是 ID 标识符
            this.syntaxErrorList.add(new SyntaxException(this.token, "错误的声明语句"));
        }

        return targetNode;
    }




    // 处理函数参数列表
    // params->param_list | void
    private SyntaxNode params() {

        SyntaxNode targetNode = AbstractSyntaxTree.newNode(NodeType.ParamsK);
        SyntaxNode p = null ;

        if ("void".equals(this.token.getValue())){
            // 类型为 void 说明没有参数列表
            p = AbstractSyntaxTree.newNode(NodeType.VoidK);

            // 匹配
            this.match(new Word("void", "void"));

            // 判断后续 右括号 )
            if (")".equals(token.getValue())){
                if (targetNode != null) {
                    targetNode.getChildren().set(0,p);
                }
            }else {
                // 处理参数列表
                SyntaxNode paramListNode = this.paramList(p);
                targetNode.getChildren().set(0,paramListNode);
            }

        }else if("int".equals(this.token.getValue())){
            // 类型为 int
            // 处理参数列表
            SyntaxNode paramListNode = this.paramList(p);
            targetNode.getChildren().set(0,paramListNode);
        }else {

            this.syntaxErrorList.add(new SyntaxException(this.token,"错误的函数参数类型"));
        }

        return targetNode;
    }



    // 处理参数列表
    // param_list->param {, param}
    private SyntaxNode paramList(SyntaxNode parentNode) {

        SyntaxNode targetNode = this.param(parentNode);
        SyntaxNode p = targetNode ;

        // 没有要传递给 parma 的参数 ，所以为VoidK
        parentNode = null ;

        // token 为 逗号',' ，需要一个处理参数
        while (",".equals(this.token.getValue())){

            SyntaxNode q = null ;
            // 匹配 逗号
            this.match(new Word(",",","));
            q = this.param(parentNode);

            if (q != null) {
                // 下层已经成功解析了参数
                if (targetNode == null) {
                    targetNode=p=q;

                }else{
                    p.setSibling(q);
                    p = q ;
                }
            }
        }

        return targetNode ;
    }



    // 处理单个函数参数
    // param -> type_specifier ID { [] }
    private SyntaxNode param(SyntaxNode parentNode) {

        SyntaxNode target = AbstractSyntaxTree.newNode(NodeType.ParamK);

        SyntaxNode p = null ;
        SyntaxNode q = null ;

        if (parentNode == null && "void".equals(this.token.getValue())) {
            // 上层没有参数传递
            p = AbstractSyntaxTree.newNode(NodeType.VoidK);
            // 匹配
            this.match(new Word("void","void",WordMappingPool.getTokenValue("void")));

        }else if (parentNode == null && "int".equals(this.token.getValue())){
            // int 类型的参数，可能接下来还有参数列表
            p = AbstractSyntaxTree.newNode(NodeType.IntK);
            this.match(new Word("int","int",WordMappingPool.getTokenValue("int")));

        }else if (parentNode != null){
            p = parentNode ;
        }


        //
        if (p != null) {

            target.getChildren().set(0,p) ;

            // 判断 token 类型
            if("ID".equals(this.token.getWeakClass())){
                // 标识符
                q = AbstractSyntaxTree.newNode(NodeType.IdK);
                q.setAttribute(this.token.getValue());
                target.getChildren().set(1,q);
                // 匹配标识符
                this.matchOrToken(this.token, 4500);

            }else {
                // 错误
                this.syntaxErrorList.add(new SyntaxException(this.token,
                        "期望得到一个标识符，实际类型："+this.token.getWeakClass()));
            }

            // 匹配 中括号，看是否是数组
            if ("[".equals(this.token.getValue()) && (target.getChildren().get(1) != null)){

                // 匹配左中括号，
                this.match(new Word("[","["));
                target.getChildren().set(2,AbstractSyntaxTree.newNode(NodeType.IdK));
                // 匹配右中括号
                this.match(new Word("]","]"));

            }else{
                return target;
            }

        }else{

            this.syntaxErrorList.add(new SyntaxException(this.token ,"错误的声明类型"));
        }

        return target;
    }




    // 处理 复合语句
    // 复合语句可以推出 局部变变量声明，语句列表
    // compound_stmt->{ local_declaration statement_list }
    private SyntaxNode compoundStmt() {

        SyntaxNode target = AbstractSyntaxTree.newNode(NodeType.CompK);

        // 匹配 {
        this.match(new Word("{","{",WordMappingPool.getTokenValue("{")));

        // 获取 局部变量声明 节点
        SyntaxNode lcNode =  this.localDeclaration();
        target.getChildren().set(0,lcNode);

        SyntaxNode stNode = this.statementList();
        target.getChildren().set(1,stNode);

        this.match(new Word("}","}",WordMappingPool.getTokenValue("}")));

        return target;
    }



    // 处理全局变量声明
    // local_declaration->empty { var_declaration }
    private SyntaxNode localDeclaration() {

        SyntaxNode target = null ;
        SyntaxNode p = null ;
        SyntaxNode q = null ;

        while ("int".equals(this.token.getValue()) || "void".equals(this.token.getValue())){

            p = AbstractSyntaxTree.newNode(NodeType.Var_DeclK);

            if ("int".equals(this.token.getValue())){
                // int 类型的声明
                SyntaxNode q1 = AbstractSyntaxTree.newNode(NodeType.IntK);
                p.getChildren().set(0,q1) ;

                // 匹配
                this.match(new Word("int","int"));

            }else if("void".equals(this.token.getValue())){

                // void 类型的声明
                SyntaxNode q1 = AbstractSyntaxTree.newNode(NodeType.VoidK);
                p.getChildren().set(0,q1) ;

                // 匹配
                this.match(new Word("void","void"));
            }


            // 接下来是标识符
            if(p != null && this.token.getWeakClass().equals(WordStatus.ID)){
                SyntaxNode q2 = AbstractSyntaxTree.newNode(NodeType.IdK);
                q2.setAttribute(this.token.getValue());

                p.getChildren().set(1,q2);
                // 匹配标识符
                this.matchOrToken(this.token ,4500);

                // 判断是否还有后续，并且后续是 [ , 即看是否是数组声明
                if("[".equals(this.token.getValue())){
                    SyntaxNode q3 = AbstractSyntaxTree.newNode(NodeType.Var_DeclK);
                    p.getChildren().set(3,q3);

                    // 匹配
                    this.match(new Word("[","["));
                    this.match(new Word("]","]"));
                    this.match(new Word(";",";"));

                }else if (";".equals(this.token.getValue())){
                    // 普通变量声明，后面直接就是分号
                    this.match(new Word(";",";"));
                }else {
                    this.match(new Word(";",";"));
                }

            }else {
                this.syntaxErrorList.add(new SyntaxException(this.token, "非法的变量声明或语句结束"));
            }

            if (p != null) {

                if (target == null) {
                    target = q = p ;
                }else {
                    q.setSibling(p);
                    p = q ;
                }
            }
        }

        return target ;
    }


    // 语句列表
    // statement_list->{ statement }
    private SyntaxNode statementList() {

        SyntaxNode target = this.statement();
        SyntaxNode p = target ;

        // 获取 token 的  value
        String value = this.token.getValue();

        while("if".equals(value) || "while".equals(value) || "return".equals(value) ||
                "{".equals(value) || "(".equals(value) || ";".equals(value) ||
                this.token.getToken().equals(4200) || this.token.getWeakClass().equals(WordStatus.ID)){

            SyntaxNode q = null ;
            q = this.statement();

            if (q != null) {

                if (target == null) {
                    target = p = q;
                }else{
                    p.setSibling(q);
                    p = q;
                }
            }
        }

        return target ;
    }




    // 语句：语句可以分为 表达式语句，复合语句，循环语句，分支语句, return 语句
    // statement->expression_stmt | compound_stmt | selection_stmt | iteration_stmt | return_stmt
    private SyntaxNode statement() {

        SyntaxNode target = null ;

        String value = this.token.getValue();
        Integer integer = this.token.getToken();
        String weakClass = this.token.getWeakClass();

        // 根据 token 判断表达式类型
        if ("if".equals(value)){
            target = this.selectionStmt();

        }else if ("while".equals(value)){
            target = this.iterationStmt();

        }else if ("return".equals(value)){
            target = this.returnStmt();


        }else if ("{".equals(value)){
            target = this.compoundStmt();

        }else if (";".equals(value) || "(".equals(value) || integer.equals(4500) || integer.equals(4200)){
            // 分号，左括号，标识符ID，数字 Number
            // 表达式语句
            target = this.expressionStmt() ;

        }else{
            // 错误的表达式
            this.syntaxErrorList.add(new SyntaxException(this.token ,"非法的表达式开始"));
            // 跳过当前错误 token
            this.token = this.getNextToken();
        }


        return target ;
    }


    // if 表达式
    private SyntaxNode selectionStmt() {

        SyntaxNode target = AbstractSyntaxTree.newNode(NodeType.Selection_StmtK);

        // 匹配 if
        this.match(new Word("if", "if"));
        this.match(new Word("(", "("));

        if (target != null) {

            // 处理条件表达式
            SyntaxNode relNode = this.expression();
            target.getChildren().set(0,relNode);
        }

        // 匹配 )
        this.match(new Word(")",")"));

        // 处理接下来的语句
        SyntaxNode statementNode = this.statement();
        target.getChildren().set(1,statementNode);

        // 判断后续是否还有 else 语句
        if ("else".equals(this.token.getValue())){
            this.match(new Word("else", "else"));

            if (target != null) {
                SyntaxNode elseNode = this.statement();
                target.getChildren().set(2,elseNode);
            }
        }

        return target ;

    }




    // 循环表达式
    // iteration_stmt-> while ( expression )
    private SyntaxNode iterationStmt() {

        SyntaxNode target = AbstractSyntaxTree.newNode(NodeType.Iteration_StmtK);

        // 匹配
        this.match(new Word("while","while"));
        this.match(new Word("(", "("));

        if (target != null){

            // 分析表达式
            SyntaxNode expNode = this.expression();
            target.getChildren().set(0,expNode);
        }

        // 匹配右括号
        this.match(new Word(")",")"));

        // 处理语句
        if (target != null) {

            SyntaxNode stmtNode = this.statement();
            target.getChildren().set(1,stmtNode);
        }

        return target ;
    }


    // 返回语句
    private SyntaxNode returnStmt() {

        SyntaxNode target = AbstractSyntaxTree.newNode(NodeType.Return_StmtK);

        this.match(new Word("return", "return"));

        // 判断是否有返回值
        if (";".equals(this.token.getValue())) {
            // 没有返回值
            this.match(new Word(";",";"));
            return target;

        }else {
            // 有返回值
            if (target != null) {
                // 处理表达式
                SyntaxNode returnNode = this.expression();
                target.getChildren().set(0,returnNode);
            }
        }

        this.match(new Word(";",";"));
        return target ;
    }


    // 表达式处理
    // expression_stmt-> [ expression ];
    private SyntaxNode expressionStmt() {

        SyntaxNode target = null ;

        // 判断是否为分号，选择是否执行表达式解析，
        if (";".equals(this.token.getValue())){

            this.match(new Word(";",";"));
            return target;

        }else{
            // 是表达式，需要进行处理
            target = this.expression();
            this.match(new Word(";",";"));
        }

        return target ;
    }


    // 处理表达式: 变量赋值表达式，简单表达式
    // expression->var = expression | simple_expression
    private SyntaxNode expression() {

        SyntaxNode target = this.var();

        // 不是 标识符 开头，只能是 普通表达式 simple_expression  类型
        if (target == null) {
            target = this.simpleExpression(target);

        }else{
            // 以标识符开头，可能是变量赋值表达式，也可能为 函数调用表达式
            SyntaxNode p = null ;

            // 判断是否为赋值语句
            if ("=".equals(this.token.getValue())) {

                p = AbstractSyntaxTree.newNode(NodeType.AssginK) ;

                // 匹配
                this.match(new Word("=","="));

                p.getChildren().set(0,target);
                // 表达式节点
                SyntaxNode expNode = this.expression();
                p.getChildren().set(1,expNode);

                return p ;
            }else {
                // simple_expresstion 类中的 call 函数调用 和 var 变量类型
                target = this.simpleExpression(target);
            }
        }

        return target ;
    }



    // 处理变量赋值语句
    // var->ID | ID [ expression ]
    private SyntaxNode var() {

        SyntaxNode target = null ;
        SyntaxNode p = null ;
        SyntaxNode q = null ;

        if (this.token.getWeakClass().equals(WordStatus.ID)){
            // 符合规则
            p = AbstractSyntaxTree.newNode(NodeType.IdK);
            p.setAttribute(this.token.getValue());

            // 匹配
            this.matchOrToken(this.token, 4500);

            if ("[".equals(this.token.getValue())){
                // 判断是否是数组类型
                this.match(new Word("[","["));

                // 处理表达式
                q = expression();

                this.match(new Word("]","]"));

                // 数组类型
                target = AbstractSyntaxTree.newNode(NodeType.Array_ElemK);
                target.getChildren().set(0,p);
                target.getChildren().set(1,q);

            }else{
                // 不是数组声明
                target = p ;
            }
        }
        return target ;
    }


    // 处理常规表达式, 关系表达式
    // simple_expression->additive_expression { relop addivite_expression }
    // relop-><= | < | > | >= | == | !=
    private SyntaxNode simpleExpression(SyntaxNode parentNode) {

        SyntaxNode target = this.additiveExpression(parentNode);

        parentNode = null ;

        // 判断 运算符，关系符号
        String operator = this.token.getValue();
        if ("==".equals(operator) || ">".equals(operator) || ">=".equals(operator) ||
                "<".equals(operator) || "<=".equals(operator) || "!=".equals(operator)){
            SyntaxNode q = AbstractSyntaxTree.newNode(NodeType.OpK);
            q.setAttribute(this.token.getValue());
            q.getChildren().set(0,target);

            target = q ;

            // 匹配
            this.match(this.token);

            // 处理后续关系表达式
            SyntaxNode nextRelNode = this.additiveExpression(parentNode);

            // 设置关系表达式后续节点
            target.getChildren().set(1,nextRelNode);
            return target;
        }

        return target ;
    }



    // 算术表达式
    private SyntaxNode additiveExpression(SyntaxNode parentNode) {

        // 处理第一个 项目
        SyntaxNode target = this.term(parentNode);

        parentNode = null ;

        while ("+".equals(this.token.getValue()) || "-".equals(this.token.getValue())){

            // 处理算数表达式
            SyntaxNode q = AbstractSyntaxTree.newNode(NodeType.OpK);
            q.setAttribute(this.token.getValue());

            q.getChildren().set(0,target);

            // 匹配
            this.match(this.token);

            // 处理后续项目
            SyntaxNode nextNode = this.term(parentNode);
            q.getChildren().set(1,nextNode);

            target = q ;
        }

        return target;
    }


    // 处理算数表达式的一项
    // term->
    private SyntaxNode term(SyntaxNode parentNode) {

        SyntaxNode target = this.factor(parentNode);

        parentNode = null ;

        while("*".equals(this.token.getValue()) || "/".equals(this.token.getValue())){

            SyntaxNode opNode = AbstractSyntaxTree.newNode(NodeType.OpK);
            opNode.setAttribute(this.token.getValue());

            opNode.getChildren().set(0,target);
            target = opNode ;

            // 匹配
            this.match(this.token);
            // 处理后续因子
            SyntaxNode nextNode = this.factor(parentNode);
            opNode.getChildren().set(1, nextNode);

        }
        return target ;
    }



    // 处理因子
    private SyntaxNode factor(SyntaxNode parentNode) {

        SyntaxNode target = null ;

        // parentNode 为上层建筑 传递下来的 已经解析出来的以 ID 开头的 var 可能为 call 或 var
        if (parentNode != null) {

            if ("(".equals(this.token.getValue()) && parentNode.getKind().equals(NodeType.Array_ElemK)){
                // 函数调用 call
                target = this.call(parentNode);
            }else {
                target = parentNode ;
            }
        }else {
            // 上层建筑没有传递下来的 var
            //this.token

        }


        return target ;
    }



    // 函数调用
    private SyntaxNode call(SyntaxNode parentNode) {



        return null ;
    }


}
