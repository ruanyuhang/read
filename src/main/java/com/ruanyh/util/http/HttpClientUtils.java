package com.ruanyh.util.http;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class HttpClientUtils {
    private static PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
    private static String EMPTY_STR = "";
    private static String UTF8 = "UTF-8";

    static {
        connectionManager.setMaxTotal(100);                 // 整个连接池最大连接数
        connectionManager.setDefaultMaxPerRoute(20);        // 每路由最大连接数，默认值是2
    }

    /**
     * 私有的构造方法,不允许实例化
     */
    private HttpClientUtils() {}


    /**
     * Get方法(无参数)
     * @param url
     * @return
     */
    public static String get(String url) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        return response(httpGet);
    }

    /**
     * Get方法(有参数)
     * @param url
     * @param params 请求参数
     * @return
     * @throws URISyntaxException
     */
    public static String get(String url, Map<String, Object> params) throws URISyntaxException, IOException {
        List<NameValuePair> pairs = getNVPair(params);
        URI uri = new URIBuilder().setPath(url).setParameters(pairs).build();
        HttpGet httpGet = new HttpGet(uri);
        return response(httpGet);
    }

    /**
     * Get方法
     * @param url
     * @param headers Http请求头
     * @param params 请求参数
     * @return
     * @throws URISyntaxException
     */
    public static String get(String url, Map<String, Object> headers, Map<String, Object> params)
            throws URISyntaxException, IOException {
        List<NameValuePair> pairs = getNVPair(params);
        URI uri = new URIBuilder().setPath(url).setParameters(pairs).build();
        HttpGet httpGet = new HttpGet(uri);
        for (Map.Entry<String, Object> param : headers.entrySet()) {
            httpGet.addHeader(param.getKey(), String.valueOf(param.getValue()));
        }
        return response(httpGet);
    }

    /**
     * Post方法
     * @param url
     * @return
     */
    public static String post(String url) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        return response(httpPost);
    }

    /**
     * Post方法
     * @param url
     * @param params 请求参数
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String post(String url, Map<String, Object> params) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> pairs = getNVPair(params);
        httpPost.setEntity(new UrlEncodedFormEntity(pairs, UTF8));
        return response(httpPost);
    }

    /**
     * Post方法
     * @param url
     * @param headers Http请求头
     * @param params 请求参数
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String post(String url, Map<String, Object> headers, Map<String, Object> params)
            throws IOException {
        HttpPost httpPost = new HttpPost(url);

        for (Map.Entry<String, Object> param : headers.entrySet()) {
            httpPost.addHeader(param.getKey(), String.valueOf(param.getValue()));
        }

        List<NameValuePair> pairs = getNVPair(params);
        httpPost.setEntity(new UrlEncodedFormEntity(pairs, UTF8));

        return response(httpPost);
    }

    /**
     * Map参数转换为NameValuePair列表
     * @param params
     * @return
     */
    private static List<NameValuePair> getNVPair(Map<String, Object> params) {
        List<NameValuePair> pairs = new ArrayList<>();
        for (Map.Entry<String, Object> param : params.entrySet()) {
            pairs.add(new BasicNameValuePair(param.getKey(), String.valueOf(param.getValue())));
        }
        return pairs;
    }

    /**
     * 响应
     * @param request
     * @return
     */
    private static String response(HttpRequestBase request) throws IOException {
        CloseableHttpClient httpClient = getHttpClient();
        CloseableHttpResponse response = httpClient.execute(request);
        try {
            response = httpClient.execute(request);
            // response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                // long len = entity.getContentLength();// -1 表示长度未知
                String result = EntityUtils.toString(entity);

                // httpClient.close();
                return result;
            }
        } finally {
            response.close();
        }
        return EMPTY_STR;
    }

    /**
     * 通过连接池获取HttpClient
     * @return
     */
    private static CloseableHttpClient getHttpClient() {
        return HttpClients.custom().setConnectionManager(connectionManager).build();
    }

}
