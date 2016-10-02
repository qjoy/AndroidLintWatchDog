package com.qjoy.BinaryResource;

import com.android.resources.ResourceFolderType;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.Location;
import com.android.tools.lint.detector.api.ResourceContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;

import java.util.EnumSet;

@SuppressWarnings("ALL")
public class ImageFileSizeDetector extends Detector implements Detector.BinaryResourceScanner {

	private static final Class<? extends Detector> DETECTOR_CLASS = ImageFileSizeDetector.class;
	private static final EnumSet<Scope> DETECTOR_SCOPE = Scope.BINARY_RESOURCE_FILE_SCOPE;
	private static final Implementation IMPLEMENTATION = new Implementation(
			DETECTOR_CLASS,
			DETECTOR_SCOPE
	);

	private static final String ISSUE_ID = "QJoyImageFileSizeInvalid";
	private static final String ISSUE_DESCRIPTION = "QJoyImageFileSizeInvalid";
	private static final String ISSUE_EXPLANATION = "Image File too large, Compress your one or more image files,  Final Solution: https://github.com/qjoy/TinyPNGNodeJSBatcher";
	private static final Category ISSUE_CATEGORY = Category.CORRECTNESS;
	private static final int ISSUE_PRIORITY = 5;
	private static final Severity ISSUE_SEVERITY = Severity.ERROR;

	public static final Issue ISSUE = Issue.create(
			ISSUE_ID,
			ISSUE_DESCRIPTION,
			ISSUE_EXPLANATION,
			ISSUE_CATEGORY,
			ISSUE_PRIORITY,
			ISSUE_SEVERITY,
			IMPLEMENTATION
	).addMoreInfo("https://github.com/qjoy/TinyPNGNodeJSBatcher");


	@SuppressWarnings({"WeakerAccess", "CanBeFinal"})
	String reportStr = "图片文件过大: %d" + "KB,超过了项目限制的:" + CHECK_IMAGE_KB_SIZE + "KB,请进行压缩或找设计重新出图.";

	private static final String CHECK_IMAGE_PNG = ".png";
	private static final String CHECK_IMAGE_JPEG = ".jpeg";
	private static final String CHECK_IMAGE_JPG = ".jpg";
	private static final long CHECK_IMAGE_KB_SIZE = 250;

	@Override
	public boolean appliesTo(ResourceFolderType var1) {
		return var1.getName().equalsIgnoreCase(String.valueOf(ResourceFolderType.MIPMAP)) || var1.getName().equalsIgnoreCase(String.valueOf(ResourceFolderType.DRAWABLE));
	}

	@Override
	public void checkBinaryResource(ResourceContext context) {

		String filename = context.file.getName();

		if ( filename.contains(CHECK_IMAGE_PNG)
				|| filename.contains(CHECK_IMAGE_JPEG)
				|| filename.contains(CHECK_IMAGE_JPG)
				){
			long fileSize = context.file.length()/1024;
			if (fileSize > CHECK_IMAGE_KB_SIZE){

				String repS = String.format(reportStr, fileSize);

				Location fileLocation = Location.create(context.file);
				context.report(ISSUE,
						fileLocation,
						repS);
			}
		}

	}
}
