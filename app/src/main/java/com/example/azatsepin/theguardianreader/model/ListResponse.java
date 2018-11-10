
package com.example.azatsepin.theguardianreader.model;

import java.util.List;
import java.util.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("userTier")
    @Expose
    private String userTier;
    @SerializedName("total")
    @Expose
    private int total;
    @SerializedName("startIndex")
    @Expose
    private int startIndex;
    @SerializedName("pageSize")
    @Expose
    private int pageSize;
    @SerializedName("currentPage")
    @Expose
    private int currentPage;
    @SerializedName("pages")
    @Expose
    private int pages;
    @SerializedName("orderBy")
    @Expose
    private String orderBy;
    @SerializedName("results")
    @Expose
    private List<Result> results = null;

    public String getStatus() {
        return status;
    }

    public String getUserTier() {
        return userTier;
    }

    public int getTotal() {
        return total;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPages() {
        return pages;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public List<Result> getResults() {
        return results;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListResponse response = (ListResponse) o;
        return Objects.equals(status, response.status) &&
                Objects.equals(userTier, response.userTier) &&
                Objects.equals(total, response.total) &&
                Objects.equals(startIndex, response.startIndex) &&
                Objects.equals(pageSize, response.pageSize) &&
                Objects.equals(currentPage, response.currentPage) &&
                Objects.equals(pages, response.pages) &&
                Objects.equals(orderBy, response.orderBy) &&
                Objects.equals(results, response.results);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, userTier, total, startIndex, pageSize, currentPage, pages, orderBy, results);
    }
}
