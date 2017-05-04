package com.zhoushuai.listening;

import org.apache.hc.core5.http.impl.io.DefaultBHttpClientConnection;
import org.apache.hc.core5.http.io.HttpClientConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by zhoushuai on 24/04/2017.
 */

public class HttpClientThread extends Thread {

    private String url;
    private String name;
    private String password;
    private String id;
    private String phone;
    private String nickname;



    public HttpClientThread(String url, String phone, String nickname, String password) {
        this.url = url;
        this.phone = phone;
        this.nickname= nickname;
        this.password = password;
    }

    public HttpClientThread(String url, String phone, String id) {
        this.url = url;
        this.phone = phone;
        this.id = id;
    }

    public void doHttpPost() {
        try {
            URL httpURL = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) httpURL.openConnection();
            conn.setRequestMethod("POST");
            OutputStream os = conn.getOutputStream();//输出流
            conn.setReadTimeout(5000);//超时时间
            String info = "phone=" + phone + "&nickname=" + nickname+"&password="+password;
            os.write(info.getBytes());
            //读数据
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String str;
            while ((str = reader.readLine()) != null) {
                sb.append(str);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        doHttpPost();
    }
}
