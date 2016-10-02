package com.qjoy.testsupport.log;

import android.annotation.SuppressLint;
import android.util.Log;

/**
 * @author AleXQ
 * @Date 16/9/22
 * @Description: 测试log
 */

@SuppressLint("All")
public class MyLog {
	public static void d(String Tag, String content){
		Log.d(Tag, content);
	}
}
