package com.example.azatsepin.theguardianreader.domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

@Entity(tableName = "Article", indices={@Index(value="title", unique=true)})
public class ArticleEntity implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String title;
    private String pillarName;
    private String sectionName;
    private String date;
    private String body;
    private String link;
    private String thumbnail;
    private boolean pinned;
    private boolean saved;

    public ArticleEntity() {
    }

    protected ArticleEntity(Parcel in) {
        title = in.readString();
        pillarName = in.readString();
        sectionName = in.readString();
        date = in.readString();
        body = in.readString();
        thumbnail = in.readString();
        link = in.readString();
        pinned = in.readByte() != 0;
        saved = in.readByte() != 0;
    }

    public static ArticleEntity fromArticle(Article article) {
        ArticleEntity entity = new ArticleEntity();
        entity.title = article.getWebTitle();
        entity.pillarName = article.getPillarName();
        entity.sectionName = article.getSectionName();
        entity.date = article.getFields().getLastModified();
        entity.body = article.getFields().getBody();
        entity.thumbnail = article.getFields().getThumbnail();
        entity.link = article.getWebUrl();
        entity.pinned = false;
        entity.saved = false;
        return entity;
    }

    public static final Creator<ArticleEntity> CREATOR = new Creator<ArticleEntity>() {
        @Override
        public ArticleEntity createFromParcel(Parcel in) {
            return new ArticleEntity(in);
        }

        @Override
        public ArticleEntity[] newArray(int size) {
            return new ArticleEntity[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPillarName() {
        return pillarName;
    }

    public void setPillarName(String pillarName) {
        this.pillarName = pillarName;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(pillarName);
        dest.writeString(sectionName);
        dest.writeString(date);
        dest.writeString(body);
        dest.writeString(thumbnail);
        dest.writeString(link);
        dest.writeByte((byte) (pinned ? 1 : 0));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleEntity entity = (ArticleEntity) o;
        return id == entity.id &&
                pinned == entity.pinned &&
                Objects.equals(title, entity.title) &&
                Objects.equals(pillarName, entity.pillarName) &&
                Objects.equals(sectionName, entity.sectionName) &&
                Objects.equals(date, entity.date) &&
                Objects.equals(body, entity.body) &&
                Objects.equals(link, entity.link) &&
                Objects.equals(thumbnail, entity.thumbnail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, pillarName, sectionName, date, body, link, thumbnail, pinned);
    }
}
