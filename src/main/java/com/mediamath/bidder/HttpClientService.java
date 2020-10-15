package com.mediamath.bidder;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;

public class HttpClientService implements Closeable {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientService.class);

    private final CloseableHttpClient httpClient;
    private String hostUrl;

    public enum PathOption{
        RELATIVE, ABSOLUTE
    }

    public HttpClientService(
            int httpSessionTimeoutInMillis,
            String hostUrlArg) {
        this.hostUrl = hostUrlArg;
        RequestConfig config = RequestConfig.custom()

                .setConnectTimeout(httpSessionTimeoutInMillis)
                .setConnectionRequestTimeout(httpSessionTimeoutInMillis)
                .setSocketTimeout(httpSessionTimeoutInMillis).build();
        httpClient = HttpClientBuilder.create()
                .setMaxConnTotal(100)
                .setConnectionManagerShared(true)
                .setKeepAliveStrategy(DefaultConnectionKeepAliveStrategy.INSTANCE)
                .setMaxConnPerRoute(100)
                .setDefaultRequestConfig(config).build();
    }

    public String sendPostRequestWithJsonBody(
            String url,
            String jsonBody,
            PathOption pathOption) throws IOException {
        if (pathOption.equals(PathOption.RELATIVE)) {
            url = hostUrl + url;
        }
        HttpPost httpPost = new HttpPost(url);

        StringEntity entity = new StringEntity(jsonBody);
        httpPost.setEntity(entity);

        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        CloseableHttpResponse response = httpClient.execute(httpPost);
        if (response.getEntity() == null) {
            return "";
        }
        return EntityUtils.toString(response.getEntity(), "UTF-8");
    }

    public CloseableHttpResponse sendPostRequest(
            String url,
            String body,
            String contentType,
            PathOption pathOption) throws IOException {
        if (pathOption.equals(PathOption.RELATIVE)) {
            url = hostUrl + url;
        }
        HttpPost httpPost = new HttpPost(url);

        StringEntity entity = new StringEntity(body);
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", contentType);
        httpPost.setHeader("Content-type", contentType);

        //    assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
        LOGGER.debug("httpPost : {}", httpPost);
        return httpClient.execute(httpPost);
    }

    public String sendGetRequest(String url,
                                 PathOption pathOption) {
        if (pathOption.equals(PathOption.RELATIVE)) {
            url = hostUrl + url;
        }

        try {
            HttpGet httpget = new HttpGet(url);
            // Create a custom response handler
            ResponseHandler<String> responseHandler = response -> {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            };
            String responseBody = httpClient.execute(httpget, responseHandler);
            LOGGER.debug("responseBody : {}", responseBody);
            return responseBody;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getHostUrl() {
        return hostUrl;
    }

    @Override
    public void close() throws IOException {
        LOGGER.info("closing httpClient");
        httpClient.close();
    }
}