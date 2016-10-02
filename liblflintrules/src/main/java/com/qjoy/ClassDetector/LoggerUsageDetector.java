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
import java.util.EnumSet;
import java.util.List;

@SuppressWarnings("QjoyJavaChineseString")
public class LoggerUsageDetector extends Detector implements Detector.ClassScanner {

	private static final Class<? extends Detector> DETECTOR_CLASS = LoggerUsageDetector.class;
	private static final EnumSet<Scope> DETECTOR_SCOPE = Scope.CLASS_FILE_SCOPE;
	private static final Implementation IMPLEMENTATION = new Implementation(
			DETECTOR_CLASS,
			DETECTOR_SCOPE
	);

	private static final String ISSUE_ID = "QJoyLogUtilsNotUsed";
	private static final String ISSUE_DESCRIPTION = "QJoyLogUtilsNotUsed";
	private static final String ISSUE_EXPLANATION = "Logging should be avoided in production for security and performance reasons. Therefore, we created a LogUtils that wraps all our calls to Logger and disable them for release flavor.";
	private static final Category ISSUE_CATEGORY = Category.CORRECTNESS;
	private static final int ISSUE_PRIORITY = 9;
	private static final Severity ISSUE_SEVERITY = Severity.ERROR;

	public static final Issue ISSUE = Issue.create(
			ISSUE_ID,
			ISSUE_DESCRIPTION,
			ISSUE_EXPLANATION,
			ISSUE_CATEGORY,
			ISSUE_PRIORITY,
			ISSUE_SEVERITY,
			IMPLEMENTATION
	);

	@Override
	public List<String> getApplicableCallNames() {
		return Arrays.asList("v", "d", "i", "w", "e", "wtf");
	}

	@Override
	public List<String> getApplicableMethodNames() {
		return Arrays.asList("v", "d", "i", "w", "e", "wtf");
	}

	@Override
	public void checkCall(@NonNull ClassContext context,
	                      @NonNull ClassNode classNode,
	                      @NonNull MethodNode method,
	                      @NonNull MethodInsnNode call) {
		String owner = call.owner;
		if (owner.startsWith("android/util/Log")) {
			context.report(ISSUE,
					method,
					call,
					context.getLocation(call),
					"使用MyLog类日志, 避免使用Log");
		}
	}
}
