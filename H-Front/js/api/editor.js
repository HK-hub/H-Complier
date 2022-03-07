// 设置代码编辑器区域值
function setEditorValue(params, code) {
    //alert("q");
    //console.log("setEditorValue", params);
    params.setValue(code);

}


// 设置Debug 区域内容
function setDebugValue(params, debugInfo) {
    //console.log('setDebugValue', params)
    params.setValue(debugInfo);
}


// 设置resultTerminal区域值
function setResultTerminalValue(params, result) {
    //console.log('setResultTerminalValue', params);
    params.setValue(result);

}

// 获取区域内容
function getTextAreaValue(params) {
    return params.getValue();
}




// 复制编辑器内容
function copyText(text, callback) {

    // 实现复制方法一：
    if (navigator.clipboard) {
        navigator.clipboard.writeText(text);
        callback && callback(true);
        return;
    }

}

//