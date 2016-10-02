
package com.qjoy.JavaDetector;

import com.android.annotations.NonNull;
import com.android.tools.lint.client.api.JavaParser;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;

import java.util.ArrayList;
import java.util.EnumSet;

import lombok.ast.AstVisitor;
import lombok.ast.ForwardingAstVisitor;
import lombok.ast.MethodInvocation;
import lombok.ast.Node;

@SuppressWarnings("All")

/**
 * @author QJOY
 * @Date 16/9/22
 * @Description: 检查fastJson格式化类对象是否实现了Serializable或者Parcelable
 */
public class FastJsonDetector extends Detector implements Detector.JavaScanner {

	private static final Class<? extends Detector> DETECTOR_CLASS = FastJsonDetector.class;
	private static final EnumSet<Scope> DETECTOR_SCOPE = Scope.JAVA_FILE_SCOPE;
	private static final Implementation IMPLEMENTATION = new Implementation(
			DETECTOR_CLASS,
			DETECTOR_SCOPE
	);

	private static final String ISSUE_ID = "QjoyJavaFastJson";
	private static final String ISSUE_DESCRIPTION = "QjoyJavaFastJson";
	private static final String ISSUE_EXPLANATION = "Check the class of use fastjson has implemented Serializable or Parcelable interface";
	private static final Category ISSUE_CATEGORY = Category.LINT;
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

	private static final String FASTJSONTOOLS_NAME = "FastJsonTools";

	private static final String INTERFACE_JAVAOBJECT = "java.lang.Object";
	/**
	 * 需要实现的接口名称
	 */
	private static final String INTERFACE_SERIALIZABLE = "Serializable";
	private static final String INTERFACE_PARCELABLE = "Parcelable";
	/**
	 * 报错信息
	 */
	private static final String REPORTWORD = "被用到FastJson的JSON.parseXXX方法的类,必须实现Serializable或Parcelable接口,否则混淆后执行会崩溃";
	/**
	 * 检测的接口名称
	 */
	//public static <T> String serialize(T object)
	private static final String FASTJSONMETHOD_SERIALIZE = "serialize";
	//public static <T> T deserialize(String json, Class<T> clz)
	private static final String FASTJSONMETHOD_DESERIALIZE = "deserialize";
//	//public static <T> List<T> deserializeList(String json, Class<T> clz)
//	private static final String FASTJSONMETHOD_DESERIALIZELIST = "deserializeList";



	@Override
	public AstVisitor createJavaVisitor(final @NonNull JavaContext context) {

		mContext = context;

		return new ForwardingAstVisitor() {
			@Override
			public boolean visitMethodInvocation(MethodInvocation node) {

				JavaParser.ResolvedNode resolve = context.resolve(node);
				if (resolve instanceof JavaParser.ResolvedMethod) {
					JavaParser.ResolvedMethod method = (JavaParser.ResolvedMethod) resolve;
					// 方法所在的类校验
					JavaParser.ResolvedClass containingClass = method.getContainingClass();
					if (containingClass.getName().contains(FASTJSONTOOLS_NAME)) {

						if (method.getName().equals(FASTJSONMETHOD_DESERIALIZE) ) {

							JavaParser.TypeDescriptor classType = method.getReturnType();
							JavaParser.ResolvedClass resolvedClass = classType.getTypeClass();

							recursiveSupperClass(resolvedClass, node);

							return true;
						}
						else if (method.getName().equals(FASTJSONMETHOD_SERIALIZE) ){

							JavaParser.TypeDescriptor classType = method.getArgumentType(0);
							JavaParser.ResolvedClass resolvedClass = classType.getTypeClass();

							recursiveSupperClass(resolvedClass, node);

							return true;
						}
					}
				}
				return super.visitMethodInvocation(node);
			}

			boolean found = false;

			JavaParser.ResolvedClass recursiveSupperClass(JavaParser.ResolvedClass curClass, Node node){

				if (found)
					return null;
				//是否已经找到跟节点上了
				if (curClass.getName().equals(INTERFACE_JAVAOBJECT)){
					context.report(
							ISSUE,
							node,
							context.getLocation(node),
							REPORTWORD
					);
					found = true;
					return curClass;
				}

				//在当前节点查找是否
				if (checkFastJsonRules(curClass, node)) {
					return curClass;
				}
				else{
					return recursiveSupperClass(curClass.getSuperClass(), node);
				}
			}
			
			boolean checkFastJsonRules(JavaParser.ResolvedClass curClass, Node node){
				boolean res = false;
				try {
					ArrayList<JavaParser.ResolvedClass> interfaces = (ArrayList) curClass.getInterfaces();

					boolean valid = false;
					for (int i = 0; i < interfaces.size(); i++) {
						JavaParser.ResolvedClass tmpInterface = interfaces.get(i);
						if (tmpInterface.getName().contains(INTERFACE_SERIALIZABLE) ||
								tmpInterface.getName().contains(INTERFACE_PARCELABLE)) {
							valid = true;
							found = true;
						}
					}

					if (!valid) {
						context.report(
								ISSUE,
								node, context.getLocation(node),
								REPORTWORD
						);
						res = true;
						found = true;
					}
				}
				catch (Exception e){
					res = false;
				}

//							System.out.print("\n method:" + des.getName() + "\n");
				return res;
				
			}
		};


	}




}
