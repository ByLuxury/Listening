package com.zhoushuai.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhoushuai.listening.R;
/**
 * Created by zhoushuai on 23/04/2017.
 */
/**
 * ImageView创建工厂
 */
public class ActivityViewFactory {

	/**
	 * 取ImageView视图的同时加载显示url
	 * @param context
	 * @param url
	 * @return
	 */
	public static ImageView getImageView(Context context, String url) {
		ImageView imageView = (ImageView)LayoutInflater.from(context).inflate(
				R.layout.activity_banner, null);
		ImageLoader.getInstance().displayImage(url, imageView);
		return imageView;
	}
}
