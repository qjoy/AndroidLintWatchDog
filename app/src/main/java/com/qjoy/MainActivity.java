package com.qjoy;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import com.qjoy.testsupport.activity.LintCheckRightAppAcompatActivity;
import com.qjoy.testsupport.json.FastJsonTools;
import com.qjoy.testsupport.json.PeopleInfo;
import com.qjoy.testsupport.log.MyLog;

import java.util.ArrayList;

import qjoy.com.myapplication.R;


public class MainActivity extends LintCheckRightAppAcompatActivity {

	@SuppressWarnings("unused")
	private static final String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

/*
//		QjoyClassLogUtilsNotUsed测试
		LogTest();

//		QjoyJavaFastJson测试
		JsonTest();

//		QjoyJavaFastJson测试
		JsonArrayTest();
*/
	}

	private void LogTest(){
		Log.d(TAG, "test");
	}

//	@SuppressLint("QjoyJavaChineseString")
//	private void JsonTest(){
//		PeopleInfo info = FastJsonTools.deserialize("{\"name\":\"Alex\",\"school\":\"Beira\",\"birthday\":\"1985年3月xx日\"}"
//				, PeopleInfo.class);
//		MyLog.d(TAG, info.name);
//	}

//	private void JsonTestOrg(){
//		PeopleInfo info = JSON.parseObject("{\"name\":\"Alex\",\"school\":\"Beira\",\"birthday\":\"1985年3月xx日\"}"
//				, PeopleInfo.class);
//		MyLog.d(TAG, info.name);
//	}

	@SuppressLint("QjoyJavaChineseString")
	private void JsonArrayTest(){
		ArrayList<PeopleInfo> infos = (ArrayList<PeopleInfo>)FastJsonTools.deserializeList("[{\"name\":\"Alex\",\"school\":\"Beida\",\"birthday\":\"1985年3月xx日\"},{\"name\":\"Brain\",\"school\":\"Qinghua\",\"birthday\":\"1988年5月xx日\"}]"
				, PeopleInfo.class);
		MyLog.d(TAG, "Array length:"+infos.size());
	}

}
