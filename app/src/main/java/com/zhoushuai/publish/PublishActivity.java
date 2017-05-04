package com.zhoushuai.publish;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhoushuai.listening.MainActivity;
import com.zhoushuai.listening.R;
import com.zhoushuai.net.Config;

/**
 * Created by zhoushuai on 27/04/2017.
 */

public class PublishActivity extends Activity implements View.OnClickListener {
    private ImageView returnImg;
    private EditText inputMsgEdit;
    private String phone;
    private String token;
    private ProgressDialog pd;
    private TextView sendText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publish_message_layout);
        init();
    }

    public void init() {
        sendText = (TextView) findViewById(R.id.publish_msg);
        returnImg = (ImageView) findViewById(R.id.publish_return_img);
        inputMsgEdit = (EditText) findViewById(R.id.publish_send_msg_edit);
        phone = getIntent().getStringExtra(Config.KEY_PHONE_NUM);
        token = getIntent().getStringExtra(Config.KEY_TOKEN);
        sendText.setOnClickListener(this);
        returnImg.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.publish_msg:
                sendMsg();
                break;
            case R.id.publish_return_img:
                Intent intent = new Intent(PublishActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    public void sendMsg() {
        if (TextUtils.isEmpty(inputMsgEdit.getText())) {
            Toast.makeText(this, R.string.msg_can_not_be_empty, Toast.LENGTH_SHORT).show();
            return;
        }

        pd = ProgressDialog.show(this, null, "正在发布");
        new PublishNet(phone, token, inputMsgEdit.getText().toString(),
                new PublisSuccess(this, pd, phone, token), new PublishFail(this, pd));
    }
}
