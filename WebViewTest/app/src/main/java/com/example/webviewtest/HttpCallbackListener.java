package com.example.webviewtest;

public interface HttpCallbackListener {
    //当服务器响应请求时调用 返回服务器返回的数据
    void onFinish(String response);
    //当进行网络操作时出现错误的时候调用 返回错误信息
    void onError(Exception e);
}
