package com.zhoushuai.listening;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhoushuai.net.Config;
import com.zhoushuai.publish.PublishActivity;

/**
 * Created by zhoushuai on 17/04/2017.
 */
public class MeFragment extends Fragment {
   private TextView tvNickname;
  private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         view=inflater.inflate(R.layout.me_layout,container,false);
        initData();
        return  view;
    }
    public void initData(){
        tvNickname= (TextView) view.findViewById(R.id.me_name);
        Bundle bundle=getArguments();
        String nickname = null;
        if(bundle!=null){
            nickname=bundle.getString(Config.KEY_NICKNAME);
            
        }
        tvNickname.setText(nickname);

    }
}
