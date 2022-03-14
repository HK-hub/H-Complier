package com.hk.complier.hcomplier.controller;

import com.hk.complier.core.lexicalnalyzer.LexicalAnalyzer;
import com.hk.complier.hcomplier.common.res.ResponseResult;
import com.hk.complier.hcomplier.service.SourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : HK意境
 * @ClassName : LexicalAnalysisController
 * @date : 2022/3/14 14:59
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@RestController
@RequestMapping("/lex")
public class LexicalAnalysisController {

    @Autowired
    private SourceService sourceService ;

    /**
     * @methodName : lexicalAnalysis
     * @author : HK意境
     * @date : 2022/3/14 15:03
     * @description : 进行词法分析
     * @Todo :
     * @params :
         * @param : null
     * @return : ResponseEntity<LexicalAnalyzer> 词法分析的结果
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0
     */
    public ResponseResult lexicalAnalysis(@RequestBody String sourceString){

        System.out.println(sourceString);
        LexicalAnalyzer analyzer = sourceService.lexicalAnalyzerService(sourceString);

        return new ResponseResult<LexicalAnalyzer>().setData(analyzer);
    }

}
