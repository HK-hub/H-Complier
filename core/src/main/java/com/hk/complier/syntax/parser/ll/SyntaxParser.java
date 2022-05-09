package com.hk.complier.syntax.parser.ll;

import com.hk.complier.lexer.constant.Word;
import com.hk.complier.lexer.constant.WordMappingPool;
import com.hk.complier.lexer.token.TokenPool;
import com.hk.complier.syntax.parser.ll.ast.AbstractSyntaxTree;
import com.hk.complier.syntax.parser.ll.excption.SyntaxException;
import lombok.Data;

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

    // 记录语法树
    public AbstractSyntaxTree ast ;

    // 记录语法解析过程中语法推到选择的策略
    public Map<String,List<String>> parseTrance = new HashMap<String,List<String>>();

    // 记录语法分析过程中的错误
    private List<SyntaxException> syntaxErrorList = new ArrayList<>();
    
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
    public void parse(){
        Word startWord = null ;

        // 获取第一个 token
        if(this.tokenPool.hasNextToken()){
            // 获取开始 token
            startWord = this.tokenPool.getNextToken();

            // 进入程序入口
            this.program(startWord,this.tokenPool.getCursor());

        }else{
            // 没有 token 
           // this.syntaxErrorList.add(new SyntaxException(null,0,"no token input!"));
        }

    }
    

    // 非终结符: 程序解析
    public int program(Word preToken , int currentCursor){

        // 当前解析语句的解析过程 parseTrance
        List<String> trance = new ArrayList<>();

        // 记录当前 token 的坐标
        int thisCursor = this.tokenPool.getCursor() ;

        // 进入 声明语句 处理函数
        int nextPosition = this.declaration_statement(preToken, thisCursor);

        // 获取下一个位置的 token 
        Word intToken = this.tokenPool.getNextToken();

        // 处理 int 
        // 判断是否为 int
        if(!WordMappingPool.getTokenValue("int").equals(intToken.getToken())){
            // 出现错误
            //this.syntaxErrorList.add(new SyntaxException(intToken,nextPosition,
            //        "this main() function return type must be int , but current is not int!"));
        }
        
        // 判断 main 关键字
        Word mainToken = this.tokenPool.getNextToken();
        if(!"main".equals(mainToken.getValue())){
            // 关键字不为 main 出现错误
            //this.syntaxErrorList.add(new SyntaxException(mainToken,this.tokenPool.getCursor(),
                 //   "this main() function' name must be main, but current name is not main"));
        }
        
        // 接下来判断是否为 () 函数界
        Word parenthesisToken = this.tokenPool.getNextToken();
        if(!"()".equals(parenthesisToken.getValue())){
            // 下一个 token 不为()
           // this.syntaxErrorList.add(new SyntaxException(parenthesisToken, this.tokenPool.getCursor(),
               //     "this main() function must end of be () , but the next token is not ()"));
        }

        // 解析 复合语句
        int compoundStatementIndex = this.compound_statement(parenthesisToken, this.tokenPool.getCursor());

        // main 函数结束后，规定为其他函数的声明和定义
        int functionBlockStartIndex = this.tokenPool.getCursor();
        Word functionBlockStartToken = this.tokenPool.getTheIndexToken(functionBlockStartIndex);
        this.function_block(functionBlockStartToken,functionBlockStartIndex);


        // 全部通过之后，将这个过程加入语法解析的过程中
        trance.add("program->declaration_statement int main () compound_statement function_block");
        this.parseTrance.put("main() function parse",trance);

        return currentCursor ;
    }


    // 非终结符：函数块
    public void function_block(Word preToken , int currentCursor){

    }


    // 非终结符：函数定义
    public void function_define(){

    }


    // 非终结符: 函数定义形式参数列表
    public void function_define_format_parameter_list(){



    }

    // 非终结符: 函数定义形式参数项
    public void function_define_format_parameter_item(){



    }

    // 非终结符: 语句
    public void statement(){



    }

    // 非终结符: 声明语句->值声明|函数声明|$
    // 需要预测分析接下来的几个token 来判断选择那种策略
    public int declaration_statement(Word preToken , int currentCursor){
        // 这里需要用到 preToken 作为第一个token 进行判断

        // 值声明和函数声明：void
        if("void".equals(preToken.getValue())){
            // 这里为函数声明
            this.function_declaration(preToken, currentCursor);

        }else if ("final".equals(preToken.getValue())){
            // 常量声明
            this.const_declaration();

        }else {
            // 此时 preToken 是作为变量类型或函数返回值类型
            // 需要继续获取到下一个 token 进行判断
            Word identifier = this.tokenPool.getNextToken();

            // 标识符
            // 还需要获取下一个 token 进行判断: 判断是否为 ( , 进而得出是否为函数声明
            Word isParenthesisToken = this.tokenPool.getNextToken();

            // 回退指针
            this.tokenPool.decreaseCursorBySteps(2);

            if ("(".equals(isParenthesisToken)) {
                // 函数声明: 但是需要回退2个位置，因为我们向前预测了两个token的位置
                this.function_declaration(preToken, currentCursor);

            }else if("=".equals(isParenthesisToken)){
                // 赋值语句
                this.value_declaration(preToken,currentCursor);

            }else {
                // 出现错误
                //this.syntaxErrorList.add(new SyntaxException(isParenthesisToken, this.tokenPool.getCursor()+2,
                    //    "the assign statement:" + identifier+  " next expect is a '=', but is "+ isParenthesisToken));

            }

        }

        // 查看是否还有下一个 token



        // 这里的返回值得好好琢磨琢磨
        return 0 ;
    }

    // 非终结符: 值声明
    public void value_declaration(Word preToken , int currentCursor){



    }

    // 非终结符: 常量声明
    public void const_declaration(){



    }

    // 非终结符: 常量类型
    public void const_type(){



    }

    // 非终结符: 常量声明表
    public void const_declaration_table(){



    }

    // 非终结符: 变量声明
    public void variable_declaration(){



    }

    // 非终结符: 变量类型
    public void variable_type(){



    }

    // 非终结符: 变量声明表
    public void variable_declaration_table(){



    }

    // 非终结符: 单变量声明
    public void single_variable_declaration(){



    }

    // 非终结符: 函数声明
    public void function_declaration(Word preToken , int currentCursor){



    }

    // 非终结符: 函数类型(函数返回值类型)
    public void function_return_type(){



    }

    // 非终结符: 函数声明形参列表
    public void function_declaration_format_parameter_list(){



    }

    // 非终结符: 函数声明形参项
    public void function_declaration_format_parameter_item(){



    }

    // 非终结符: 执行语句
    public void execute_statement(){



    }

    // 非终结符: 出局处理语句
    public void data_process_statement(){



    }

    // 非终结符: 赋值语句
    public void assign_statement(){



    }

    // 非终结符: 赋值表达式
    public void assign_expression(){



    }

    // 非终结符: 函数调用语句
    public void function_call_statement(){



    }

    // 非终结符: 控制语句
    public void control_statement(){



    }

    // 非终结符: 函数定义
    public int compound_statement(Word preToken , int currentCursor){


        return 0;
    }

    // 非终结符: 语句表
    public void statement_table(){



    }

    // 非终结符: if 语句
    public void if_statement(){



    }

    // 非终结符: if_tail
    public void if_tail(){



    }

    // 非终结符: for 语句
    public void for_statement(){



    }

    // 非终结符: while 语句
    public void while_statement(){



    }

    // 非终结符: do while 语句
    public void do_while_statement(){



    }

    // 非终结符: 循环语句 语句
    public void loop_statement(){



    }

    // 非终结符: 循环复合语句
    public void loop_compound_statement(){



    }

    // 非终结符: 循环语句表
    public void loop_statement_table(){



    }

    // 非终结符: 循环执行语句
    public void loop_execute_statement(){



    }

    // 非终结符: 循环 if 语句
    public void loop_if_statement(){



    }

    // 非终结符: return 语句
    public void return_statement(){



    }

    // 非终结符: break 语句
    public void break_statement(){



    }

    // 非终结符: continue 语句
    public void continue_statement(){



    }


    // 非终结符: 表达式
    public void expression(){



    }


    // 非终结符: 算术表达式
    public void arithmetic_expression(){



    }


    // 非终结符: 项
    public void prime(){



    }


    // 非终结符: 因子
    public void factor(){



    }

    // 非终结符: 函数调用
    public void function_call(){



    }

    // 非终结符: 实际参数列表
    public void actual_parameter_list(){



    }

    // 非终结符: 实际参数
    public void actual_parameter_item(){



    }

    // 非终结符: 关系表达式
    public void relational_expression(){



    }


    // 非终结符: 关系运算符
    public void relational_operator(){



    }


    // 非终结符: 布尔表达式
    public void bool_expression(){



    }


    // 非终结符: 布尔项
    public void bool_prime(){



    }


    // 非终结符: 布尔因子
    public void bool_factor(){



    }


    // 非终结符: 标识符
    public void identifier(){



    }


    // 非终结符: 常量
    public void const_value(){



    }

    // 非终结符: 变量
    public void variable_value(){



    }




}
