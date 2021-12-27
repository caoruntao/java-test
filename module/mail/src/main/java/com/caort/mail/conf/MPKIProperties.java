package com.caort.mail.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Caort
 * @date 2021/11/26 下午4:28
 */
@EnableConfigurationProperties
@Configuration
@ConfigurationProperties("mpki")
public class MPKIProperties {
    private String url;
    /**
     * 订单一般30天内没处理就取消了，因此默认设置记录存放40天
     */
    private int validPeriod;
    private Mail mail;
    private Login login;
    private QueryInfo query;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getValidPeriod() {
        return validPeriod;
    }

    public void setValidPeriod(int validPeriod) {
        this.validPeriod = validPeriod;
    }

    public Mail getMail() {
        return mail;
    }

    public void setMail(Mail mail) {
        this.mail = mail;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public QueryInfo getQuery() {
        return query;
    }

    public void setQuery(QueryInfo query) {
        this.query = query;
    }

    public static class Mail {
        private String[] cc;

        public String[] getCc() {
            return cc;
        }

        public void setCc(String[] cc) {
            this.cc = cc;
        }
    }

    public static class Login {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static class QueryInfo {
        private String orderType;
        private String userName;
        private String confirmor;
        private String orderState;
        private int page = 1;
        private int pageSize = 100;

        public String getOrderType() {
            return orderType;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getConfirmor() {
            return confirmor;
        }

        public void setConfirmor(String confirmor) {
            this.confirmor = confirmor;
        }

        public String getOrderState() {
            return orderState;
        }

        public void setOrderState(String orderState) {
            this.orderState = orderState;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }
    }
}
