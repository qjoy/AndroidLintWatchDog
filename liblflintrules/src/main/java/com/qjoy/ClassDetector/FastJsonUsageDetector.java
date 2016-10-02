/*
package com.qjoy.ClassDetector;

import com.android.annotations.NonNull;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.ClassContext;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.Arrays;
import java.util.List;

public class FastJsonUsageDetector extends Detector implements Detector.ClassScanner {
	public static final Issue ISSUE = Issue.create("QjoyClassFastJsonCheck",
			"FastJson",
			"Check the class of use fastjson has implemented Serializable or Parcelable interface",
			Category.MESSAGES,
			9,
			Severity.ERROR,
			new Implementation(FastJsonUsageDetector.class,
					Scope.CLASS_FILE_SCOPE));

	@Override
	public List<String> getApplicableCallNames() {
		return Arrays.asList("deserialize", "deserializeAny", "deserializeList");
	}

	@Override
	public List<String> getApplicableMethodNames() {
		return Arrays.asList("deserialize", "deserializeAny", "deserializeList");
	}

	@Override
	public void checkCall(@NonNull ClassContext context,
	                      @NonNull ClassNode classNode,
	                      @NonNull MethodNode method,
	                      @NonNull MethodInsnNode call) {
		String owner = call.owner;
		if (owner.startsWith("com/qjoy/testsupport/json/FastJsonTools")) {

			System.out.print(context.getClassNode() + "\n");

			context.report(ISSUE,
					method,
					call,
					context.getLocation(call),
					"fastjson需要实现 Serializable或Parcelable接口,否则混淆后执行会崩溃");
		}
	}
}
*/
