
package com.example.azatsepin.theguardianreader.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SingleResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("userTier")
    @Expose
    private String userTier;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("content")
    @Expose
    private Article content;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserTier() {
        return userTier;
    }

    public void setUserTier(String userTier) {
        this.userTier = userTier;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Article getContent() {
        return content;
    }

    public void setContent(Article content) {
        this.content = content;
    }

}
