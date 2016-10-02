package com.qjoy.testsupport.activity;

import android.app.Activity;
import android.os.Bundle;

import qjoy.com.myapplication.R;

public class LFBaseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lfbase);
	}
}
