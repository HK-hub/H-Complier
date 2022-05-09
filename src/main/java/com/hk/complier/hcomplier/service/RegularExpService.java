package com.hk.complier.hcomplier.service;


import com.hk.complier.dfa.graph.ALGraph;
import com.hk.complier.dfa.model.BinTree;
import com.hk.complier.dfa.model.RegExpress;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author : HK意境
 * @ClassName : RegularExpService
 * @date : 2022/4/3 10:57
 * @description : 负责转换正则表达式
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Service
public class RegularExpService {


    public JSONObject convertRegularExpressionsToDFA(String act, String value) throws IOException {

        if ("inputRE".equals(act)) {
            RegExpress re = new RegExpress(value);//(a|b)*a(a|b)  a*|s*d  (a|b)a(a|b|c)*
            BinTree root = null;
            try {
                root = re.makeTree();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            root.print();

            // 创建 NFA 图
            ALGraph g = root.makeNFAGraph("NFA of \"" + value + "\"");
            ALGraph dfa = g.toDFAGraph();
            dfa.setTitle("DFA of \"" + value + "\"");
            ALGraph mfa = dfa.toMFAGraph();
            mfa.setTitle("MFA of \"" + value + "\"");
            JSONObject o = new JSONObject();

            o.put("nfa", g.toString());
            o.put("dfa", dfa.toString());
            o.put("mfa", mfa.toString());

            return o ;
        }

        return null ;
    }

}