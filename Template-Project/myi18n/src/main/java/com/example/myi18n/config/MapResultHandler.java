package com.example.myi18n.config;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

import java.util.HashMap;
import java.util.Map;

public class MapResultHandler implements ResultHandler {
    private final Map mappedResults = new HashMap();

    @Override
    public void handleResult(ResultContext context) {
        Map map = (Map) context.getResultObject();
        // xml配置中通过resultMap配置的property
        mappedResults.put(map.get("key"), map.get("value"));
    }

    public Map getMappedResults() {
        return mappedResults;
    }
}
