package com.mediamath.bidder.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Site {
    private String id;
    private String domain;
    private List<String> cat;
    private String page;
    public Site() {

    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public List<String> getCat() {
        return cat;
    }

    public void setCat(List<String> cat) {
        this.cat = cat;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "Site{" +
                "id='" + id + '\'' +
                ", domain='" + domain + '\'' +
                ", cat=" + cat +
                ", page='" + page + '\'' +
                '}';
    }
}
