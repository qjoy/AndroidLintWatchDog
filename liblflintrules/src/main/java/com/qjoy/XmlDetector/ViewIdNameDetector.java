package com.qjoy.XmlDetector;

import com.android.annotations.NonNull;
import com.android.resources.ResourceFolderType;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Context;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.ResourceXmlDetector;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.android.tools.lint.detector.api.XmlContext;

import org.w3c.dom.Attr;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;

import static com.android.SdkConstants.VALUE_ID;

/**
 * @author QJOY
 * @Date 16/9/22
 * @Description: 检查layout文件中的id是否符合《Android-Code-Style》中"布局文件中的id命名"这个部分的规定
 */
@SuppressWarnings("QjoyJavaChineseString")
public class ViewIdNameDetector extends ResourceXmlDetector
{

	private static final Class<? extends Detector> DETECTOR_CLASS = ViewIdNameDetector.class;
	private static final EnumSet<Scope> DETECTOR_SCOPE = Scope.RESOURCE_FILE_SCOPE;
	private static final Implementation IMPLEMENTATION = new Implementation(
			DETECTOR_CLASS,
			DETECTOR_SCOPE
	);

	private static final String ISSUE_ID = "QjoyResourceXmlViewIdName";
	private static final String ISSUE_DESCRIPTION = "QjoyResourceXmlViewIdName";
	private static final String ISSUE_EXPLANATION = "Check whether the name is conform to the specifications";
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

	String reportStrFormat = "Layout文件中id命名不符合规范: %s, 前缀必须是: %s";


	private static final String ANDROIDID = "android:id";
	/*
		id命名规范
	 */
	/*layout*/
	private static final String IDHEADER_LAYOUT_ABBREVIATION = "layout";
	private static final String ID_LAYOUT_RELATIVELAYOUT = "RelativeLayout";
	private static final String ID_LAYOUT_LINEARLAYOUT = "LinearLayout";
	private static final String ID_LAYOUT_FRAMELAYOUT = "FrameLayout";
	/*button*/
	private static final String IDHEADER_BUTTON_ABBREVIATION = "button";
	private static final String ID_BUTTON_BUTTON = "Button";
	private static final String ID_BUTTON_RADIOBUTTON = "RadioButton";
	private static final String ID_BUTTON_IMAGEBUTTON = "ImageButton";
	/*text*/
	private static final String IDHEADER_TEXT_ABBREVIATION = "text";
	private static final String ID_TEXT_TEXTVIEW = "TextView";
	private static final String ID_TEXT_EDITTEXT = "EditText";
	/*imageView*/
	private static final String IDHEADER_IMAGEVIEW_ABBREVIATION = "imageView";
	private static final String ID_IMAGEVIEW = "ImageView";
	/*listView*/
	private static final String IDHEADER_LISTVIEW_ABBREVIATION = "listView";
	private static final String ID_LISTVIEW = "ListView";
	/*webView*/
	private static final String IDHEADER_WEBVIEW_ABBREVIATION = "webView";
	private static final String ID_WEBVIEW = "WebView";
	/*listView*/
	private static final String IDHEADER_CHECKBOX_ABBREVIATION = "checkBox";
	private static final String ID_CHECKBOX = "CheckBox";
	/*listView*/
	private static final String IDHEADER_PROGRESSBAR_ABBREVIATION = "progressBar";
	private static final String ID_PROGRESSBAR = "ProgressBar";
	/*listView*/
	private static final String IDHEADER_SEEKBAR_ABBREVIATION = "seekBar";
	private static final String ID_SEEKBAR = "SeekBar";

	@Override
	public void beforeCheckProject(Context context) {
		super.beforeCheckProject(context);
	}

	@Override
	public boolean appliesTo(ResourceFolderType folderType) {
		return ResourceFolderType.LAYOUT == folderType;
	}

	@Override
	public Collection<String> getApplicableAttributes() {
		return Collections.singletonList(VALUE_ID);
	}

	@Override
	public void visitAttribute(@NonNull XmlContext context, @NonNull Attr attribute) {
		super.visitAttribute(context, attribute);

		String prnMain = context.getMainProject().getDir().getPath();
		String prnCur = context.getProject().getDir().getPath();

		//1.只关心id节点
		//2.只关心工程中的xml文件,build等等目录下的不关心
		if (attribute.getName().startsWith(ANDROIDID) && prnMain.equals(prnCur))
			checkNameRule(context, attribute);
	}

	private void checkNameRule(XmlContext context, Attr attribute) {

		String tagName = attribute.getOwnerElement().getTagName();//LineanLayout...
		//layout
		int startIndex = 0;
		String idName = attribute.getValue().substring(5);
		String attrRight = "";
		switch (tagName) {
			case ID_LAYOUT_LINEARLAYOUT:
			case ID_LAYOUT_RELATIVELAYOUT:
			case ID_LAYOUT_FRAMELAYOUT:
				attrRight = IDHEADER_LAYOUT_ABBREVIATION;
				startIndex = idName.indexOf(attrRight);
				break;
			case ID_BUTTON_BUTTON:
			case ID_BUTTON_IMAGEBUTTON:
			case ID_BUTTON_RADIOBUTTON:
				attrRight = IDHEADER_BUTTON_ABBREVIATION;
				startIndex = idName.indexOf(attrRight);
				break;
			case ID_TEXT_TEXTVIEW:
			case ID_TEXT_EDITTEXT:
				attrRight = IDHEADER_TEXT_ABBREVIATION;
				startIndex = idName.indexOf(attrRight);
				break;
			case ID_IMAGEVIEW:
				attrRight = IDHEADER_IMAGEVIEW_ABBREVIATION;
				startIndex = idName.indexOf(attrRight);
				break;
			case ID_LISTVIEW:
				attrRight = IDHEADER_LISTVIEW_ABBREVIATION;
				startIndex = idName.indexOf(attrRight);
				break;
			case ID_WEBVIEW:
				attrRight = IDHEADER_WEBVIEW_ABBREVIATION;
				startIndex = idName.indexOf(attrRight);
				break;
			case ID_CHECKBOX:
				attrRight = IDHEADER_CHECKBOX_ABBREVIATION;
				startIndex = idName.indexOf(attrRight);
				break;
			case ID_PROGRESSBAR:
				attrRight = IDHEADER_PROGRESSBAR_ABBREVIATION;
				startIndex = idName.indexOf(attrRight);
				break;
			case ID_SEEKBAR:
				attrRight = IDHEADER_SEEKBAR_ABBREVIATION;
				startIndex = idName.indexOf(attrRight);
				break;
		}

		if (startIndex != 0) {

			String reportStr = String.format(reportStrFormat, idName, attrRight);

			context.report(ISSUE,
					attribute,
					context.getLocation(attribute),
					reportStr);
		}

	}

}
