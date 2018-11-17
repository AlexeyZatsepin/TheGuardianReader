
package com.example.azatsepin.theguardianreader.domain;

import android.arch.persistence.room.Embedded;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Article {

    @SerializedName("id")
    @Expose
    private String url;
    @SerializedName("type")
    @Expose
    private String type;
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
    @SerializedName("fields")
    @Expose
    @Embedded
    private Fields fields;
    @SerializedName("tags")
    @Expose
    private List<Tag> tags = null;
    @SerializedName("isHosted")
    @Expose
    private Boolean isHosted;
    @SerializedName("pillarId")
    @Expose
    private String pillarId;
    @SerializedName("pillarName")
    @Expose
    private String pillarName;

    private boolean saved;

    public Article(){}

    public static Article fromEntity(ArticleEntity article) {
        Article entity = new Article();
        entity.webTitle = article.getTitle();
        entity.pillarName = article.getPillarName();
        entity.sectionName = article.getSectionName();
        entity.fields = new Fields();
        entity.fields.setLastModified(article.getDate());
        entity.fields.setBody(article.getBody());
        entity.fields.setThumbnail(article.getThumbnail());
        entity.webUrl = article.getLink();
        entity.saved = article.isSaved();
        entity.tags = new ArrayList<>();
        entity.tags.add(new Tag());
        return entity;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getWebPublicationDate() {
        return webPublicationDate;
    }

    public void setWebPublicationDate(String webPublicationDate) {
        this.webPublicationDate = webPublicationDate;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public void setWebTitle(String webTitle) {
        this.webTitle = webTitle;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public Fields getFields() {
        return fields;
    }

    public void setFields(Fields fields) {
        this.fields = fields;
    }

    public Boolean getIsHosted() {
        return isHosted;
    }

    public void setIsHosted(Boolean isHosted) {
        this.isHosted = isHosted;
    }

    public String getPillarId() {
        return pillarId;
    }

    public void setPillarId(String pillarId) {
        this.pillarId = pillarId;
    }

    public String getPillarName() {
        return pillarName;
    }

    public void setPillarName(String pillarName) {
        this.pillarName = pillarName;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(url, article.url) &&
                Objects.equals(type, article.type) &&
                Objects.equals(sectionId, article.sectionId) &&
                Objects.equals(sectionName, article.sectionName) &&
                Objects.equals(webPublicationDate, article.webPublicationDate) &&
                Objects.equals(webTitle, article.webTitle) &&
                Objects.equals(webUrl, article.webUrl) &&
                Objects.equals(apiUrl, article.apiUrl) &&
                Objects.equals(fields, article.fields) &&
                Objects.equals(isHosted, article.isHosted) &&
                Objects.equals(pillarId, article.pillarId) &&
                Objects.equals(pillarName, article.pillarName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, type, sectionId, sectionName, webPublicationDate, webTitle, webUrl, apiUrl, fields, isHosted, pillarId, pillarName);
    }
}
