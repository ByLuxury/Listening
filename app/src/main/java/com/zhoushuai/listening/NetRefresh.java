package com.zhoushuai.listening;

import android.os.AsyncTask;

import com.zhoushuai.net.Config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by zhoushuai on 02/05/2017.
 */

public class NetRefresh {
    public NetRefresh(final String url, final String phone, final IReFreshSuccessCallback freshSuccessCallback, final IRefreshFailCallback refreshFailCallback) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
                    conn.setRequestMethod("POST");
                    //把电话号码传入服务端
                    OutputStream out = conn.getOutputStream();
                    OutputStreamWriter os=new OutputStreamWriter(out, Config.CHARSET);
                    BufferedWriter bw=new BufferedWriter(os);
                    bw.write("phone="+phone);
                    bw.flush();
                    //读取服务端消息
                    InputStream is = conn.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);
                    StringBuffer data = new StringBuffer();
                    String line;
                    while ((line = br.readLine()) != null) {
                        data.append(line);
                    }
                    return data.toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s != null) {
                    if (freshSuccessCallback != null) {
                        freshSuccessCallback.onSuccess(s);
                    }
                } else {
                    if (refreshFailCallback != null) {
                        refreshFailCallback.onFail();
                    }
                }

            }
        }.execute();

    }

    /**
     * 回调接口
     */
    public interface IReFreshSuccessCallback {
        void onSuccess(String data);
    }

    public interface IRefreshFailCallback {
        void onFail();
    }
}
