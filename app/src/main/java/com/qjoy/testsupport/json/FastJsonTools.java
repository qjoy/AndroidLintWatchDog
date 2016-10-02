package com.qjoy.testsupport.json;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * Created by guxiuzhong on 2015/5/12.
 * 开源项目FastJson json解析和生成的工具类
 */
public class FastJsonTools {

    /**
     * 将java类型的对象转换为JSON格式的字符串
     *
     * @param object java类型的对象
     * @return JSON格式的字符串
     */
    public static <T> String serialize(T object) {
        return JSON.toJSONString(object);
    }

    /**
     * 将JSON格式的字符串转换为java类型的对象或者java数组类型的对象，不包括java集合类型
     *
     * @param json JSON格式的字符串
     * @param clz  java类型或者java数组类型，不包括java集合类型
     * @return java类型的对象或者java数组类型的对象，不包括java集合类型的对象
     */
    public static <T> T deserialize(String json, Class<T> clz) {

	    return JSON.parseObject(json, clz);
    }


    /**
     * 将JSON格式的字符串转换为List<T>类型的对象
     *
     * @param json JSON格式的字符串
     * @param clz  指定泛型集合里面的T类型
     * @return List<T>类型的对象
     */
    public static <T> List<T> deserializeList(String json, Class<T> clz) {

        if(json == null || !json.startsWith("[")) {
            return null;
        }
	    return JSON.parseArray(json, clz);
    }

}
