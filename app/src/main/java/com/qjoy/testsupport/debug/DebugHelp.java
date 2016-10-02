package com.qjoy.testsupport.debug;

/**
 * Copyright (c) 2015 by laifeng
 *
 * @author xueqing
 * @Date 15/9/1
 * @Description: $gradle打包libraray造成BuildConfig.Debug判断有问题
 */

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import qjoy.com.myapplication.BuildConfig;

/**
 * User: xueqing(xueqing@youku.com) 
 * Date: 2015-09-01 
 * Time: 16:14 
 * FIXME 
 */
public class DebugHelp {

    public static Boolean sDebug = null;

    public static boolean isDebugBuild() {
        if (sDebug == null) {
            try {
                final Class<?> activityThread = Class.forName("android.app.ActivityThread");
                final Method currentPackage = activityThread.getMethod("currentPackageName");
                final String packageName = (String) currentPackage.invoke(null, (Object[]) null);
                final Class<?> buildConfig = Class.forName(packageName + ".BuildConfig");
                final Field DEBUG = buildConfig.getField("DEBUG");
                DEBUG.setAccessible(true);
                sDebug = DEBUG.getBoolean(null);
            } catch (final Throwable t) {
                final String message = t.getMessage();
                if (message != null && message.contains("BuildConfig")) {
                    // Proguard obfuscated build. Most likely a production build.
                    sDebug = false;
                } else {
                    sDebug = BuildConfig.DEBUG;
                }
            }
        }
        return sDebug;
    }

}
