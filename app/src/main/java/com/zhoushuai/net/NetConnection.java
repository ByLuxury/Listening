package com.zhoushuai.net;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

/**
 * Created by zhoushuai on 29/04/2017.
 */

public class NetConnection {

    /**
     * @param url
     * @param method
     * @param successCallback
     * @param failCallback
     * @param keyValues
     */
//    ISuccessCallback successCallback;
//    IFailCallback failCallback;
    public NetConnection(final String url, final HttpMethod method, final ISuccessCallback successCallback, final IFailCallback failCallback, final String... keyValues) {

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                StringBuffer paraStr = new StringBuffer();//创建参数对
                for (int i = 0; i < keyValues.length; i += 2) {
                    paraStr.append(keyValues[i]).append("=").append(keyValues[i + 1]).append("&");

                }
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    URLConnection connection = new URL(url).openConnection();
                    connection.setReadTimeout(5000);
                    switch (method) {
                        case POST:
                            connection.setDoOutput(true);//往服务器端输出信息
                            BufferedWriter bw = new BufferedWriter(
                                    new OutputStreamWriter(connection.getOutputStream(),
                                            Config.CHARSET));
                            bw.write(paraStr.toString());//写出字符串，上传参数
                            bw.flush();
                            break;
                        //其他方式为get方式
                        default:
                            connection = new URL(url + "?" + paraStr.toString()).openConnection();
                            break;
                    }
                    Log.i("RESULT", "result:" + paraStr);
                    System.out.println("Request url:" + connection.getURL());
                    System.out.println("Request data:" + paraStr);
                    //读取数据
                    InputStream is = connection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);
                    String data = null;
                    StringBuffer result = new StringBuffer();
                    while ((data = br.readLine()) != null) {
                        result.append(data);
                    }
                    Log.i("RESULT", "result:" + result);
                    return result.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if (result != null) {
                    if (successCallback != null) {//判断监听是否可用
                        successCallback.onSuccess(result);
                    }
                } else {
                    if (failCallback != null) {
                        failCallback.onFail(result);
                    }
                }

            }
        }.execute();


    }



}
