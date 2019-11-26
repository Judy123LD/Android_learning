package com.example.webviewtest;
/**
 * 使用本机搭建Apache服务器，真机调试，
 * 127.0.0.1/get_data.json报错：  java.net.SocketTimeoutException: failed to connect to /10.0.2.2 (port 8080) after 10000ms
 * 将127.0.0.1（localhost）改为本机局域网ip：(以太网 WLAN ipv4)192.168.xx.xx
 * 继续报以上错
 * 将电脑防火墙关闭，手机电脑要连接相同局域网，重新install app
 */

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView=(TextView)findViewById(R.id.response_text);
        Button sendRequest=(Button)findViewById(R.id.send_request);
        sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String url="http://192.168.200.171/get_data.json";
                String url="http://www.baidu.com";


                //HttpURLRequest回调
                HttpUtil.sendHttpRequest(url, new HttpCallbackListener() {
                    //调用sendHttpRequest时将HttpCallbackListener实例传入
                    @Override
                    public void onFinish(String response) {
                        //根据返回信息执行具体的逻辑
//                            parseJSONWithGSON(response);
                        Log.i(TAG, "onFinish: "+response);
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.d(TAG, "onError: "+e.getMessage());
                    }
                });

                //okHttp回调
//                HttpUtil.sendOkHttpRequest(url,new okhttp3.Callback(){
//                    @Override
//                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                        //对异常情况进行处理
//                        Log.d(TAG, "onFailure: "+e.getMessage());
//                    }
//
//                    @Override
//                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                        //得到服务器返回的具体内容
//                        String responseData=response.body().string();
//                        Log.i(TAG, "onResponse: "+responseData);
//                    }
//                });
            }
        });
    }

    private void sendRequestWithHttpUrlConnection(){
        //开启线程发起网络请求（什么时候开启子线程，耗时操作？）
        new Thread(new Runnable() {
            @Override
            public void run() {
                //处理逻辑
                HttpURLConnection connection=null;
                BufferedReader reader=null;

                try {
                    //调用openConnection
                    URL url=new URL("http://127.0.0.1/get_data.json");
                    connection=(HttpURLConnection) url.openConnection();
                    //对connection进行一些设置
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(8000);//连接超时的秒数
                    connection.setReadTimeout(8000);//读取超时的秒数
//                    DataOutputStream out=new DataOutputStream(connection.getOutputStream());
//                    out.writeBytes("username=admin&password=123456");
                    Log.i(TAG, "connection... ");
                    //获取服务器返回的输入流
                    InputStream in=connection.getInputStream();
                    Log.i(TAG, "获取输入流");
                    //读取获取到的输入流
                    reader=new BufferedReader(new InputStreamReader(in));
                    StringBuilder response=new StringBuilder();
                    String line;
                    while((line=reader.readLine())!=null){
                        response.append(line);
                    }
                    showResponse(response.toString());
                } catch (MalformedURLException e) {
                    Log.i(TAG, "连接失败");
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    //最后一定要关闭流，断开连接
                    if(reader!=null){
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if(connection!=null){
                            connection.disconnect();
                        }
                    }
                }
            }
        }).start();

    }

    private void showResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //在这里进行UI操作，将结果显示在界面上
                textView.setText(response);
            }
        });
    }

    private void sendRequestWithOkHttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //创建一个OkHttpClient实例
                    OkHttpClient client=new OkHttpClient();
                    //创建一个request对象
                    Request request=new Request.Builder()
                            .url("http://192.168.200.171/get_data.json")
//                            .url("http://www.baidu.com")
                            .build();
                    //调用newCall方法创建一个Call对象
                    //调用Call对象的execute()方法发送请求并获取服务器返回的数据
                    Response response=client.newCall(request).execute();
                    //response对象就是服务器返回的数据，如下来得到返回的具体内容
                    String responseData=response.body().string();
                    Log.i(TAG, "response"+response);
                    Log.i(TAG, "responseData:"+responseData);
//                    showResponse(responseData);
//                    parseJSONWithJSONOBJECT(responseData);
                    parseJSONWithGSON(responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSONWithJSONOBJECT(String jsonData){
        try {
            JSONArray jsonArray=new JSONArray(jsonData);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                String id=jsonObject.getString("id");
                String version=jsonObject.getString("version");
                String name=jsonObject.getString("name");
                showResponse(id+version+name);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void parseJSONWithGSON(String jsonData){
        Gson gson=new Gson();
        List<App> appList=gson.fromJson(jsonData,new TypeToken<List<App>>(){}.getType());
        for(App app :appList){
            Log.i(TAG, "id:"+app.getId());
            Log.i(TAG, "version:"+app.getVersion());
            Log.i(TAG, "name:"+app.getName());
        }
    }

}
