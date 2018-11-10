
package com.example.azatsepin.theguardianreader.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Result {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("sectionId")
    @Expose
    private String sectionId;
    @SerializedName("sectionName")
    @Expose
    private String sectionName;
    @SerializedName("webPublicationDate")
    @Expose
    private String webPublicationDate;
    @SerializedName("webTitle")
    @Expose
    private String webTitle;
    @SerializedName("webUrl")
    @Expose
    private String webUrl;
    @SerializedName("apiUrl")
    @Expose
    private String apiUrl;

    public String getId() {
        return id;
    }

    public String getSectionId() {
        return sectionId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getWebPublicationDate() {
        return webPublicationDate;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result result = (Result) o;
        return Objects.equals(id, result.id) &&
                Objects.equals(sectionId, result.sectionId) &&
                Objects.equals(sectionName, result.sectionName) &&
                Objects.equals(webPublicationDate, result.webPublicationDate) &&
                Objects.equals(webTitle, result.webTitle) &&
                Objects.equals(webUrl, result.webUrl) &&
                Objects.equals(apiUrl, result.apiUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sectionId, sectionName, webPublicationDate, webTitle, webUrl, apiUrl);
    }
}
