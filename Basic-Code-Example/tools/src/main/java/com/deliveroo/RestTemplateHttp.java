package com.deliveroo;

import cn.hutool.core.codec.Base64;
import com.alibaba.fastjson2.JSONObject;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

public class RestTemplateHttp {

    public static void main(String[] args) {

        //1.Base64编码
        String API_KEY = "4lu6faphtgo638164283qqihjg";
        String API_SECRET = "dt284vi2i7akttpiao18sb8gnkc33h2460bbtmctvcqeu7hh4q6";
        String encode = Base64.encode(API_KEY+":"+API_SECRET);
        System.out.println("1. encode ===>>> "+ encode);
        System.out.println("----------------------------------------------------------------------------------------------------");

        //2.获取token
        String TOKEN_URL = "https://auth.developers.deliveroo.com/oauth2/token";
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type","client_credentials");

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization","Basic " + encode);
        headers.add("Content-Type","application/x-www-form-urlencoded");
        headers.add("Accept","application/json");
        HttpEntity<MultiValueMap<String,String>> requestEntity = new HttpEntity<>(body,headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> responseEntity = restTemplate.exchange(TOKEN_URL, HttpMethod.POST, requestEntity, String.class);
        String token = responseEntity.getBody();
        System.out.println("2. token ===>>> "+ token);
        JSONObject tokenJSON = JSONObject.parse(token);
        String access_token = tokenJSON.getString("access_token");
        System.out.println("2. access_token ===>>> "+ access_token);
        System.out.println("----------------------------------------------------------------------------------------------------");

        //3.获取发送的请求
        String Master_Catalogue_API_URL = "https://api.developers.deliveroo.com/brands/aeon-hk/catalogue/uploads";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization","Bearer " + access_token);
        httpHeaders.add("Content-Type","application/json;charset=UTF-8");
        HttpEntity<String> formEntity = new HttpEntity<String>("{}",httpHeaders);

        RestTemplate unSSLRestTemplate = restTemplateHttps();
        String response = unSSLRestTemplate.postForObject(Master_Catalogue_API_URL, formEntity, String.class);
        System.out.println("3. response ===>>> " + response);
    }

    /**
     * 用于https请求，忽略认证
     * @return	unSSLRestTemplate
     */
    public static RestTemplate restTemplateHttps()  {
        RestTemplate restTemplate = null;
        try {
            TrustStrategy acceptingTrustStrategy = (chain, authType) -> true;
            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);

            HttpClientBuilder clientBuilder = HttpClients.custom();

            CloseableHttpClient httpClient = clientBuilder.setSSLSocketFactory(sslsf).build();

            HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
            httpRequestFactory.setConnectionRequestTimeout(10000);
            httpRequestFactory.setConnectTimeout(10000);
            httpRequestFactory.setReadTimeout(10000);

            httpRequestFactory.setHttpClient(httpClient);

            restTemplate = new RestTemplate(httpRequestFactory);
            //解决乱码
            List<HttpMessageConverter<?>> httpMessageConverters = restTemplate.getMessageConverters();
            httpMessageConverters.stream().forEach(httpMessageConverter ->{
                if(httpMessageConverter instanceof StringHttpMessageConverter){
                    StringHttpMessageConverter messageConverter = (StringHttpMessageConverter)httpMessageConverter;
                    messageConverter.setDefaultCharset(Charset.forName("UTF-8"));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return restTemplate;
    }
}
