package com.qjoy;

import com.android.tools.lint.detector.api.Issue;
import com.qjoy.BinaryResource.ImageFileSizeDetector;
import com.qjoy.ClassDetector.LoggerUsageDetector;
import com.qjoy.JavaDetector.BaseActivityDetector;
import com.qjoy.JavaDetector.ChineseStringDetector;
import com.qjoy.JavaDetector.FastJsonDetector;
import com.qjoy.XmlDetector.ViewIdNameDetector;

import java.util.Arrays;
import java.util.List;

/**
 * @author AleXQ
 * @Date 16/9/19
 * @Description: 用来注册我们自己定义了哪些issue，这样lint在检查代码时才知道要针对哪些issue进行检查
 */

public class LFIssueRegistry extends com.android.tools.lint.client.api.IssueRegistry {
	@Override
	public List<Issue> getIssues() {
		System.out.println("###### LFIssueRegistry lint rules works ######");
		return Arrays.asList(
				FastJsonDetector.ISSUE
				, ViewIdNameDetector.ISSUE
				, ChineseStringDetector.ISSUE
				, LoggerUsageDetector.ISSUE
				, BaseActivityDetector.ISSUE
				, ImageFileSizeDetector.ISSUE
		);
	}
}
