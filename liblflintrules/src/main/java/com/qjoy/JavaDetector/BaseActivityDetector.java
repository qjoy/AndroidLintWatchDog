package com.qjoy.JavaDetector;

import com.android.tools.lint.client.api.JavaParser;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;

import java.util.EnumSet;

import lombok.ast.AstVisitor;
import lombok.ast.ClassDeclaration;
import lombok.ast.ForwardingAstVisitor;
import lombok.ast.Node;

/**
 * @author QJOY
 * @Date 16/9/22
 * @Description: 检查工程所有Activity是否继承自LFBaseActivity或者LFBaseAppCompatActivity
 */
@SuppressWarnings({"QjoyJavaChineseString", "deprecation"})
public class BaseActivityDetector extends Detector implements Detector.JavaScanner {

	private static final Class<? extends Detector> DETECTOR_CLASS = BaseActivityDetector.class;
	private static final EnumSet<Scope> DETECTOR_SCOPE = Scope.JAVA_FILE_SCOPE;
	private static final Implementation IMPLEMENTATION = new Implementation(
			DETECTOR_CLASS,
			DETECTOR_SCOPE
	);

	private static final String ISSUE_ID = "QJoyJavaBaseActivity";
	private static final String ISSUE_DESCRIPTION = "QJoyJavaBaseActivity";
	private static final String ISSUE_EXPLANATION = "Check whether your Activity inherited from LFBaseActivity";
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

	private JavaContext mContext;

	private static final String INTERFACE_JAVAOBJECT = "java.lang.Object";
	/**
	 * 不可以直接继承的Activity类全信息
	 */
	private static final String INVALIDE_ACTVITY_PARENTNAME_0 = "android.app.Activity";
	private static final String INVALIDE_ACTVITY_PARENTNAME_1 = "android.support.v7.app.AppCompatActivity";

	private static final String VALIDE_ACTVITY_PARENTNAME_0 = "LFBaseActivity";
	private static final String VALIDE_ACTVITY_PARENTNAME_1 = "LFBaseAppCompatActivity";
	/**
	 * 报错信息
	 */
	private static final String REPORTWORD = "需要继承自LFBaseActivity/LFBaseAppCompatActivity,不允许直接继承自Activity/AppCompatActivity";

	@SuppressWarnings("deprecation")
	@Override
	public AstVisitor createJavaVisitor(JavaContext context) {

		mContext = context;

		return new ForwardingAstVisitor() {
			@Override
			public boolean visitClassDeclaration(ClassDeclaration node) {

				@SuppressWarnings("deprecation") JavaParser.ResolvedNode resolve = mContext.resolve(node);

				if (resolve instanceof JavaParser.ResolvedClass) {

					if (resolve.getName().contains(VALIDE_ACTVITY_PARENTNAME_0) ||
							resolve.getName().contains(VALIDE_ACTVITY_PARENTNAME_1)
							){
						return super.visitClassDeclaration(node);
					}

					JavaParser.ResolvedClass superclass = ((JavaParser.ResolvedClass) resolve).getSuperClass();

					recursiveSupperClass(superclass, node);
				}


				return super.visitClassDeclaration(node);
			}

			JavaParser.ResolvedClass recursiveSupperClass(JavaParser.ResolvedClass curClass, Node node){

				//是否已经找到跟节点Object上了
				if (curClass.getName().equals(INTERFACE_JAVAOBJECT)){
					return curClass;
				}

				//在当前节点查找是否
				if (checkActivityRules(curClass, node)) {
					return curClass;
				}
				else{
					return recursiveSupperClass(curClass.getSuperClass(), node);
				}
			}

			boolean checkActivityRules(JavaParser.ResolvedClass curClass, Node node){
				boolean res = false;

				if (curClass.getName().contains(VALIDE_ACTVITY_PARENTNAME_0) ||
						curClass.getName().contains(VALIDE_ACTVITY_PARENTNAME_1)){
					res = true;
				}

				if (curClass.matches(INVALIDE_ACTVITY_PARENTNAME_0) ||
						curClass.matches(INVALIDE_ACTVITY_PARENTNAME_1)){
					mContext.report(
							ISSUE,
							node, mContext.getLocation(node),
							REPORTWORD
					);

					res = true;
				}

				return res;

			}

		};
	}
}
