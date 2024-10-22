package com.deliveroo;

import cn.hutool.core.codec.Base64;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import java.net.HttpCookie;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class DeliverooHttp {

    public static void main(String[] args) {
        //1.获取token
        String TOKEN_URL = "https://auth.developers.deliveroo.com/oauth2/token";
        JSONObject json = new JSONObject(){{put("grant_type","client_credentials");}};
        HttpResponse response = HttpRequest.post(TOKEN_URL)
                .header(Header.ACCEPT,"application/json")
                .basicAuth("4lu6faphtgo638164283qqihjg","dt284vi2i7akttpiao18sb8gnkc33h2460bbtmctvcqeu7hh4q6")
                .header(Header.CONTENT_TYPE,"application/x-www-form-urlencoded")
                .form(json)
                .timeout(10000)//超时，毫秒
                .execute();
        JSONObject tokenJSON = JSONObject.parse(response.body());
        String access_token = tokenJSON.getString("access_token");
        System.out.println("1. access_token ===>>> "+ access_token);
        System.out.println("----------------------------------------------------------------------------------------------------");

        //2.获取发送的请求
        String Master_Catalogue_API_URL = "https://api.developers.deliveroo.com/brands/aeon-hk/catalogue/uploads";
        // 设置Authorization头
        response = HttpRequest.post(Master_Catalogue_API_URL)
                .setHttpProxy("127.0.0.1", 10809)
                .bearerAuth(access_token)
                .header("Content-Type", "application/json;charset=UTF-8")
                .setSSLSocketFactory(SSLUtils.getSSLSocketFactory())
                .timeout(10000)//超时，毫秒
                .execute(); // 发送请求并执行
        // 获取响应内容
        String responseBody = response.body();
        System.out.println("2. responseBody ===>>> "+response.getStatus() + "\n" + responseBody);
        System.out.println("----------------------------------------------------------------------------------------------------");

        //3.发送请求
        JSONObject urlJSON = JSONObject.parse(responseBody);
        String upload_url = urlJSON.getString("upload_url");
        String data = "{\n" +
                "     \"version\": \"catalogue-upload-v1\",\n" +
                "     \"catalogue\": {\n" +
                "          \"id\": \"1\",\n" +
                "          \"items\": [\n" +
                "               {\n" +
                "                    \"id\": \"789\",\n" +
                "                    \"plu\": \"789-plu\",\n" +
                "                    \"barcodes\": [\n" +
                "                         \"5000128861083\",\n" +
                "                         \"5000128861106\",\n" +
                "                         \"5000128861069\"\n" +
                "                    ],\n" +
                "                    \"name\": {\n" +
                "                         \"en\": \"Deliveroo Bananas 5 per Pack\"\n" +
                "                    },\n" +
                "                    \"description\": {\n" +
                "                         \"en\": \"Deliveroo Bananas 5 per Pack\"\n" +
                "                    },\n" +
                "                    \"media\": [\n" +
                "                         {\n" +
                "                              \"media_type\": \"main_image\",\n" +
                "                              \"media_url\": \"https://xxx.com/image.jpeg\"\n" +
                "                         }\n" +
                "                    ],\n" +
                "                    \"price_info\": {\n" +
                "                         \"price\": 150\n" +
                "                    },\n" +
                "                    \"tax_rate\": \"1.0\",\n" +
                "                    \"is_eligible_as_replacement\": true,\n" +
                "                    \"is_eligible_for_substitution\": true,\n" +
                "                    \"is_returnable\": false,\n" +
                "                    \"classifications\": [],\n" +
                "                    \"allergies\": [\n" +
                "                         \"no_allergens\"\n" +
                "                    ],\n" +
                "                    \"diets\": [],\n" +
                "                    \"nutritional_info\": {\n" +
                "                         \"energy_kcal\": {\n" +
                "                              \"low\": 0,\n" +
                "                              \"high\": 0\n" +
                "                         }\n" +
                "                    },\n" +
                "                    \"modifier_ids\": [],\n" +
                "                    \"max_quantity\": null,\n" +
                "                    \"operational_name\": \"Deliveroo Bananas 5 per Pack\"\n" +
                "               }\n" +
                "          ],\n" +
                "          \"hero_image\": {\n" +
                "               \"url\": \"https://xxx.com/image.jpeg\"\n" +
                "          },\n" +
                "          \"experience\": \"categories\",\n" +
                "          \"categories\": {\n" +
                "               \"item_categories\": [\n" +
                "                    {\n" +
                "                         \"id\": \"fruit\",\n" +
                "                         \"name\": {\n" +
                "                              \"en\": \"Fruit\"\n" +
                "                         },\n" +
                "                         \"description\": {\n" +
                "                              \"en\": \"Fruit\"\n" +
                "                         },\n" +
                "                         \"item_ids\": [\n" +
                "                              \"789\"\n" +
                "                         ]\n" +
                "                    }\n" +
                "               ]\n" +
                "          },\n" +
                "          \"modifiers\": []\n" +
                "     }\n" +
                "}";
        response = HttpRequest.put(upload_url)
                .setHttpProxy("127.0.0.1", 10809)
                .header("Content-Type", "application/json;charset=UTF-8")
                .setSSLSocketFactory(SSLUtils.getSSLSocketFactory())
                .body(data)
                .timeout(10000)//超时，毫秒
                .execute(); // 发送请求并执行
        // 获取响应内容
        responseBody = response.body();
        System.out.println("3. responseBody ===>>> "+response.getStatus() + "\n" + responseBody);
        System.out.println("----------------------------------------------------------------------------------------------------");
    }

}
