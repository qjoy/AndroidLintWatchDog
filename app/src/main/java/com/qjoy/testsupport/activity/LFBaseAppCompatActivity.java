package com.qjoy.testsupport.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import qjoy.com.myapplication.R;

public class LFBaseAppCompatActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lfbase);
	}
}
