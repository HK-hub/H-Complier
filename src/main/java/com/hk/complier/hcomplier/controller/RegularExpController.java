package com.hk.complier.hcomplier.controller;



import com.hk.complier.hcomplier.service.RegularExpService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : HK意境
 * @ClassName : RegularExpController
 * @date : 2022/4/3 10:54
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@CrossOrigin
@Controller
@RequestMapping("/reconvert")
public class RegularExpController {

    @Autowired
    private RegularExpService regularExpService ;
    
    // 接受正则表达式进行转换
    @ResponseBody
    @RequestMapping("/doConvert")
    public JSONObject convertRegularExpressions(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("rec request");
        String act = request.getParameter("act");
        String value = request.getParameter("value");

        JSONObject jsonObject =  regularExpService.convertRegularExpressionsToDFA(act, value);



        return jsonObject;

    }


}
