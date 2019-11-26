package com.example.webviewtest;
/**
 * 网络请求放在主线程可能会造成线程堵塞，所以在sendRequest方法中新开子线程，
 * 所有耗时逻辑都是在子线程中进行的，sendRequest还没来的及响应就执行结束了，所以增加一个回调机制HttpCallbackListener
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpUtil {
    public static void sendHttpRequest(final String address, final HttpCallbackListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection=null;
                BufferedReader reader=null;
                try {
                    URL url = new URL(address);
                    connection=(HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    //获取返回数据 读取数据
                    InputStream in=connection.getInputStream();
                    reader=new BufferedReader(new InputStreamReader(in));
                    StringBuilder response=new StringBuilder();
                    String line;
                    while((line=reader.readLine())!=null){
                        response.append(line);
                    }
                    if(listener!=null){
                        //回调OnFinish方法
                        listener.onFinish(response.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if(listener!=null){
                        listener.onError(e);
                    }
                }finally {
//                    if(reader!=null){
//                        try {
//                            reader.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
                    if(connection!=null){
                        connection.disconnect();
                    }
                }
            }
        }).start();

    }

    public static void sendOkHttpRequest(String address,okhttp3.Callback callback){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                .url(address)
                .build();
        //OkHttp在enqueue方法内部已经开好子线程了，然后会在子线程中执行Http请求，将最终的结果回调到OkHttp.Callback中
        client.newCall(request).enqueue(callback);
    }
}

