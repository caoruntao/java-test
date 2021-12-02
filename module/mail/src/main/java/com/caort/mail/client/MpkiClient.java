package com.caort.mail.client;

import com.caort.mail.conf.MPKIProperties;
import com.caort.mail.pojo.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Caort
 * @date 2021/11/26 上午10:58
 */
@Component
public class MpkiClient {
    private static final Logger log = LoggerFactory.getLogger(MpkiClient.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String URL_LOGIN = "/api/v2/admin/users/account/api/login";
    private static final String URL_ORDER_LIST = "/api/v2/admin/order/query";
    private static final String URL_ORDER_GET = "/api/v2/admin/order/get";

    @Autowired
    private MPKIProperties mpkiProperties;

    private HttpClient client;
    private CookieManager cookieManager;


    @PostConstruct
    public void init() {
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        client = HttpClient.newBuilder()
                .cookieHandler(cookieManager)
                .connectTimeout(Duration.ofMillis(30000))
                .build();
    }

    public LoginResponse login(LoginRequest loginRequest) throws Exception {
        String urlStr = String.format("%s%s", mpkiProperties.getUrl(), URL_LOGIN);
        return post(URI.create(urlStr), loginRequest, LoginResponse.class);
    }

    public List<OrderListResponse> getOrderList(OrderListQueryRequest queryRequest) throws Exception {
        List<OrderListResponse> resultList = new ArrayList<>();
        StringBuilder urlSb = new StringBuilder()
                .append(mpkiProperties.getUrl() + URL_ORDER_LIST);
        urlSb.append("?pageSize=" + queryRequest.getPageSize());
        if (StringUtils.hasText(queryRequest.getOrderType())) {
            urlSb.append("&orderType=" + queryRequest.getOrderType());
        }
        if (StringUtils.hasText(queryRequest.getOrderState())) {
            urlSb.append("&orderState=" + queryRequest.getOrderState());
        }
        if (StringUtils.hasText(queryRequest.getUserName())) {
            urlSb.append("&userName=" + queryRequest.getUserName());
        }
        if (StringUtils.hasText(queryRequest.getConfirmor())) {
            urlSb.append("&confirmor=" + queryRequest.getConfirmor());
        }
        int page = queryRequest.getPage();
        PageInfo pageInfo;
        do {
            OrderListResponse orderListResponse = doGetOrderList(urlSb.toString(), page);
            pageInfo = orderListResponse.getData().getPageInfo();
            // 没有相关订单信息
            if(pageInfo == null){
                break;
            }
            resultList.add(orderListResponse);
        } while (pageInfo.getPage() * pageInfo.getPageSize() < pageInfo.getTotal());
        return resultList;
    }

    public OrderListResponse doGetOrderList(String originalUrl, int page) throws Exception {
        String urlStr = String.format("%s&page=%s", originalUrl, page);
        return get(URI.create(urlStr), OrderListResponse.class);
    }

    public OrderInfoResponse getOrderInfo(String id) throws Exception {
        String urlStr = String.format("%s%s?id=%s", mpkiProperties.getUrl(), URL_ORDER_GET, id);
        return get(URI.create(urlStr), OrderInfoResponse.class);
    }

    /**
     * @param uri
     * @param clazz 返回值类型,如Object.class
     * @param <T>
     * @return
     * @throws Exception
     */
    private <T> T get(URI uri, Class<T> clazz) throws Exception {
        HttpRequest request = HttpRequest.newBuilder(uri)
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (log.isDebugEnabled()) {
            log.info("request successful, response value [{}]", response.body());
        }
        return MAPPER.readValue(response.body(), clazz);
    }

    /**
     * @param uri
     * @param requestDTO 请求体
     * @param clazz      返回值类型,如Object.class
     * @param <T>
     * @return
     * @throws Exception
     */
    private <T> T post(URI uri, Object requestDTO, Class<T> clazz) throws Exception {
        byte[] dataBytes = MAPPER.writeValueAsBytes(requestDTO);
        HttpRequest request = HttpRequest.newBuilder(uri)
                .POST(HttpRequest.BodyPublishers.ofByteArray(dataBytes))
                .header("Content-Type", "application/json; charset=utf-8")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (log.isDebugEnabled()) {
            log.info("request successful, response value [{}]", response.body());
        }
        return MAPPER.readValue(response.body(), clazz);
    }
}
