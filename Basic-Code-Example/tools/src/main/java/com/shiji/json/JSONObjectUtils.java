package com.shiji.json;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

import java.util.*;

//add by yihaitao 2023-06-17 JSON替换工具类
public class JSONObjectUtils {

    public static void main(String[] args) {
        String json = "{'goodsName':'小米','enSanme':'XIAOMI','del':'ABC'}";
        String jsonArr = "[{ \"goodsCode\":{ \"goods\":\"goodsName\"}},{\"shopCode\":{ \"shop\":\"shopName\"}}]";
        Map replaceKeyMap = new HashMap();
        replaceKeyMap.put("goodsName","del");
        replaceKeyMap.put("enSanme","goodsName");

        Map replaceValueMap = new HashMap();
//        Map valueMap = new HashMap();
//        valueMap.put("10086","Y");
//        valueMap.put("10010","N");
//        replaceValueMap.put("code",valueMap);

        Map addNewMap = new HashMap();
//        Map newMap = new HashMap();
//        newMap.put("status","1");
//        addNewMap.put("brandCode",newMap);


        System.out.println(replaceJSON(jsonArr,replaceKeyMap,replaceValueMap,addNewMap));
    }

    /**
     * 功能JSObject新增一个JSON串 add by yihaitao 2023-06-17
     * @param jsonData 原始JSON串
     * @param addNewMap 原JSON串的加入新值 {"leafFlag":{"status":"1"}}，在JSON串中的leafFlag字段所在的层级增加{"status":"1"}
     * @return  {"shop":{"leafFlag":false,"shopName":"螃蟹岬店"}} -> {"shop":{"leafFlag":false,"shopName":"螃蟹岬店","status":"1"}}
     */
    public static JSONObject addNewJSON(String jsonData,Map<String, Map> addNewMap){
        return replaceJSON(jsonData,null,null,addNewMap);
    }

    /**
     * 功能JSObject替換 Value add by yihaitao 2023-06-17
     * @param jsonData 原始JSON串
     * @param replaceValueMap 替換JSON的Value {"leafFlag":{"false":"N","true":"Y"}}
     * @return  {"leafFlag":"false"} -> {"leafFlag":"N"}
     */
    public static JSONObject replaceJSONValue(String jsonData,Map<String, Map> replaceValueMap){
        return replaceJSON(jsonData,null,replaceValueMap,null);
    }

    /**
     * 功能JSObject替換Key add by yihaitao 2023-06-17
     * @param jsonData 原始JSON串
     * @param replaceKeyMap 替換JSON中的Key {"shopCode":"mkt"}，删除JSON某个Key {"goodsCode":"del"}
     * @return 替换 {"shopCode":"002"} -> {"mkt":"002"}，
     *         删除 {"goodsName":"小米","goodsCode":"del"} -> {"goodsName":"小米"}
     */
    public static JSONObject replaceJSONKey(String jsonData,Map<String, String> replaceKeyMap){
        return replaceJSON(jsonData,replaceKeyMap,null,null);
    }

    /**
     * 功能JSObject替換Key，替換Value,增加新值
     * add by yihaitao 2023-06-17
     * @param jsonData:JSON字符串
     * @param replaceKeyMap:替換JSON中的Key {"shopCode":"mkt"}
     * 例:{"shopCode":"002"}转换成{"mkt":"002"}
     * @param replaceValueMap：替換JSON的Value {"leafFlag":{"false":"N","true":"Y"}}
     * 例:{"leafFlag":false}转换成{"leafFlag":"N"}
     * @param addNewMap：原JSON串的加入新值 {"leafFlag":{"status":"1"}}，在JSON串中的leafFlag字段所在的层级增加{"status":"1"}
     * 例:{"shop":{"leafFlag":false,"shopName":"螃蟹岬店"}} -> {"shop":{"leafFlag":false,"shopName":"螃蟹岬店","status":"1"}}
     * @return 返回新的JSON
     */
    public static JSONObject replaceJSON(String jsonData, Map<String, String> replaceKeyMap, Map<String,Map> replaceValueMap, Map<String,Map> addNewMap) {
        replaceKeyMap = replaceKeyMap == null ? new HashMap<String,String>() : replaceKeyMap;
        replaceValueMap = replaceValueMap == null ? new HashMap<>() : replaceValueMap;
        addNewMap = addNewMap == null ? new HashMap<>() : addNewMap;

        SortedMap<String, Object> map = new TreeMap<>();
        JSONObject jsonObject = JSON.parseObject(jsonData);

        //获取所有的Key,得到key对应的value
        for (String key : jsonObject.keySet()) {
            //在此判断==》如果没有要替换的值，resKey就为原值，否则为要替换的值
            String resKey = replaceKeyMap.get(key) == null ? key : replaceKeyMap.get(key);
            Object value = jsonObject.get(key);
            //实现删除功能
            if (resKey.equals("del") && !"del".equals(key)){
                //map.remove(key);
                continue;
            }else{//实现替换key的功能
                //A.如果value为JSONArray类型，则解析
                if (value instanceof JSONArray) {
                    JSONArray jsonArray = new JSONArray(new LinkedList<>());
                    JSONArray array = jsonObject.getJSONArray(key);
                    if(((JSONArray) value).isEmpty()){
                        map.put(resKey,value);
                    }else{
                        for (int i = 0; i < array.size(); i++) {
                            Object object = array.get(i);
                            //如果value里面是List: ["aaa","bbb","ccc"]; 或者是value里面是JSONObject[{},{},{}]
                            if (object instanceof String){
                                map.put(resKey,array);
                                //jsonArray.add(array);
                            }else {
                                JSONObject sortJson = replaceJSON(String.valueOf(object),replaceKeyMap,replaceValueMap,addNewMap);
                                jsonArray.add(sortJson);
                                map.put(resKey,jsonArray);
                            }
                        }
                    }
                    //B.如果value为JSONObject类型，则解析
                } else if (value instanceof JSONObject) {
                    JSONObject sortJson = replaceJSON(String.valueOf(value),replaceKeyMap,replaceValueMap,addNewMap);
                    map.put(resKey, sortJson);
                    //C.如果value为JSON类型，则装入map
                } else {
                    //替換值
                    if(replaceValueMap.containsKey(resKey)){
                        Map valueMap =  replaceValueMap.get(resKey);
                        map.put(resKey,valueMap.get(value) != null ? valueMap.get(value) : value);
                    }else{
                        map.put(resKey,value);
                    }
                    //加新增
                    if(addNewMap.containsKey(resKey)){
                        map.putAll(addNewMap.get(resKey));
                    }

                }
            }
        }
        return new JSONObject(map);
    }
}
