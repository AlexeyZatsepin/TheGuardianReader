
package com.example.azatsepin.theguardianreader.domain;

import android.arch.persistence.room.Embedded;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Article implements Parcelable {

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
    @SerializedName("isHosted")
    @Expose
    private Boolean isHosted;
    @SerializedName("pillarId")
    @Expose
    private String pillarId;
    @SerializedName("pillarName")
    @Expose
    private String pillarName;

    public Article(){}

    protected Article(Parcel in) {
        url = in.readString();
        type = in.readString();
        sectionId = in.readString();
        sectionName = in.readString();
        webPublicationDate = in.readString();
        webTitle = in.readString();
        webUrl = in.readString();
        apiUrl = in.readString();
        fields = in.readParcelable(Fields.class.getClassLoader());
        byte tmpIsHosted = in.readByte();
        isHosted = tmpIsHosted == 0 ? null : tmpIsHosted == 1;
        pillarId = in.readString();
        pillarName = in.readString();
    }

    public static Article fromEntity(ArticleEntity article) {
        Article entity = new Article();
        entity.webTitle = article.getTitle();
        entity.pillarName = article.getPillarName();
        entity.sectionName = article.getSectionName();
        entity.webPublicationDate = article.getDate();
        entity.fields = new Fields();
        entity.fields.setBody(article.getBody());
        entity.fields.setThumbnail(article.getThumbnail());
        entity.webUrl = article.getLink();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(type);
        dest.writeString(sectionId);
        dest.writeString(sectionName);
        dest.writeString(webPublicationDate);
        dest.writeString(webTitle);
        dest.writeString(webUrl);
        dest.writeString(apiUrl);
        dest.writeParcelable(fields,flags);
        dest.writeByte((byte) (isHosted == null ? 0 : isHosted ? 1 : 2));
        dest.writeString(pillarId);
        dest.writeString(pillarName);
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };
}
