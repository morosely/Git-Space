package com.example.myi18n.utils;

import com.example.myi18n.common.contants.I18nContants;
import com.example.myi18n.common.contants.StrinfContants;
import com.example.myi18n.entity.I18nAllocate;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.example.myi18n.common.contants.I18nContants.DEFAULT_LANGUAGE;

public class I18nUtils {
    /**
     * 获取列表中数据类型
     * @param obj
     * @return
     */
    public static Class getArrayListClass(Object obj) {
        // 判断空，判断类型是否为列表，判断是否有数据（无数据无法获取类型）
        if (obj != null && ArrayList.class.equals(obj.getClass()) && ((List) obj).size() > 0) {
            return ((List) obj).get(0).getClass();
        }
        return null;
    }
    /**
     * 获取数据类型
     * @param obj
     * @return
     */
    public static Class getClass(Object obj) {
        // 判断空，判断类型是否为列表，判断是否有数据（无数据无法获取类型）
        if (obj != null && !ArrayList.class.equals(obj.getClass())) {
            return obj.getClass();
        }
        return null;
    }

    /**
     * 获取字符串中所有的变量参数
     * @param str 字符串/对象Json
     * @return
     */
    public static List<String> findParams(String str) {
        if (null == str){
            return null;
        }
        List<String> params = new ArrayList<>();
        // 转化为二进制
        char[] chars = str.toCharArray();
        // 找到标志的索引
        int findIndex = -1;
        for (int i = 0; i < chars.length; i ++) {
            // 判断 ${ 组合
            // i <= chars.length - 3 防越界，${A，假如以此结尾，$ 在 length - 3 位置
            if (i <= chars.length - 3 && chars[i] == '$' && chars[i + 1] == '{') {
                // 获取首个变量的下标索引
                findIndex = i + 2;
            }
            // 判断 } 且，已经存在索引下标，防止前面单独出现 } 的情况
            if (chars[i] == '}' && findIndex != -1) {
                // 添加变量
                params.add(new String(Arrays.copyOfRange(chars, findIndex, i)));
                // 重置标识
                findIndex = -1;
            }
        }
        return params;
    }

    /**
     * 数据处理
     * @param lang 语言环境
     * @param data 返回数据
     * @param languages 语言包
     * @param params 需要替换的参数列表
     * @return
     */
    public static StringBuilder dataProcess(String lang, StringBuilder data, List<I18nAllocate> languages, List<String> params) {
        if (null ==params){
            return data;
        }
        // 循环数据
        for (I18nAllocate language : languages) {
            // 有配置语言，非空对象，为后端使用的标签
            if (StringUtils.isNotBlank(language.getLangs().toString()) && !"{}".equals(language.getLangs())) {
                for (String param : params) {
                    // 如果标签组合匹配
                    if (language.equalsCombination(param)) {
                        // 假如当前环境非默认语种，判断当前语种是否已经配置，如果没配置或为空，使用默认语种数据
                        if (!I18nContants.DEFAULT_LANGUAGE.equals(lang) && JsonUtils.toMap(language.getLangs()).containsKey(lang) && StringUtils.isNotBlank((String) JsonUtils.toMap(language.getLangs()).get(lang))) {
                            data.replace(0, data.length(), replaceRegex(data.toString(), param, StrUtil.nullIsEmpty((String) JsonUtils.toMap(language.getLangs()).get(lang))));
                        } else {
                            data.replace(0, data.length(), replaceRegex(data.toString(), param, StrUtil.nullIsEmpty((String) JsonUtils.toMap(language.getLangs()).get(DEFAULT_LANGUAGE))));
                        }
                    }
                }
            }
        }
        return data;
    }

    public static String getLanguageValue(String lang,String languages){
        // 假如当前环境非默认语种，判断当前语种是否已经配置，如果没配置或为空，使用默认语种数据
        if (!I18nContants.DEFAULT_LANGUAGE.equals(lang) && JsonUtils.toMap(languages).containsKey(lang) && StringUtils.isNotBlank((String) JsonUtils.toMap(languages).get(lang))) {
            return  StrUtil.nullIsEmpty((String) JsonUtils.toMap(languages).get(lang));
        } else {
            return  StrUtil.nullIsEmpty((String) JsonUtils.toMap(languages).get(I18nContants.DEFAULT_LANGUAGE));
        }
    }

    /**
     * 正则内容替换
     * @param source 数据
     * @param key 国际化标签
     * @param value 国际化对应值
     * @return
     */
    private static String replaceRegex(String source, String key, String value) {
        String regex = "\\$\\{"+key+"\\}";
        return source.replaceAll(regex, value);
    }


}
