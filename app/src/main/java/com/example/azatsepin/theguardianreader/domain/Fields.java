
package com.example.azatsepin.theguardianreader.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Fields implements Parcelable {

    @SerializedName("headline")
    @Expose
    private String headline;
    @SerializedName("standfirst")
    @Expose
    private String standfirst;
    @SerializedName("trailText")
    @Expose
    private String trailText;
    @SerializedName("byline")
    @Expose
    private String byline;
    @SerializedName("main")
    @Expose
    private String main;
    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("newspaperPageNumber")
    @Expose
    private String newspaperPageNumber;
    @SerializedName("wordcount")
    @Expose
    private String wordcount;
    @SerializedName("firstPublicationDate")
    @Expose
    private String firstPublicationDate;
    @SerializedName("isInappropriateForSponsorship")
    @Expose
    private String isInappropriateForSponsorship;
    @SerializedName("isPremoderated")
    @Expose
    private String isPremoderated;
    @SerializedName("lastModified")
    @Expose
    private String lastModified;
    @SerializedName("newspaperEditionDate")
    @Expose
    private String newspaperEditionDate;
    @SerializedName("productionOffice")
    @Expose
    private String productionOffice;
    @SerializedName("publication")
    @Expose
    private String publication;
    @SerializedName("shortUrl")
    @Expose
    private String shortUrl;
    @SerializedName("shouldHideAdverts")
    @Expose
    private String shouldHideAdverts;
    @SerializedName("showInRelatedContent")
    @Expose
    private String showInRelatedContent;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("legallySensitive")
    @Expose
    private String legallySensitive;
    @SerializedName("sensitive")
    @Expose
    private String sensitive;
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("bodyText")
    @Expose
    private String bodyText;
    @SerializedName("charCount")
    @Expose
    private String charCount;
    @SerializedName("shouldHideReaderRevenue")
    @Expose
    private String shouldHideReaderRevenue;
    @SerializedName("showAffiliateLinks")
    @Expose
    private String showAffiliateLinks;

    public Fields(){}

    protected Fields(Parcel in) {
        headline = in.readString();
        standfirst = in.readString();
        trailText = in.readString();
        byline = in.readString();
        main = in.readString();
        body = in.readString();
        newspaperPageNumber = in.readString();
        wordcount = in.readString();
        firstPublicationDate = in.readString();
        isInappropriateForSponsorship = in.readString();
        isPremoderated = in.readString();
        lastModified = in.readString();
        newspaperEditionDate = in.readString();
        productionOffice = in.readString();
        publication = in.readString();
        shortUrl = in.readString();
        shouldHideAdverts = in.readString();
        showInRelatedContent = in.readString();
        thumbnail = in.readString();
        legallySensitive = in.readString();
        sensitive = in.readString();
        lang = in.readString();
        bodyText = in.readString();
        charCount = in.readString();
        shouldHideReaderRevenue = in.readString();
        showAffiliateLinks = in.readString();
    }

    public static final Creator<Fields> CREATOR = new Creator<Fields>() {
        @Override
        public Fields createFromParcel(Parcel in) {
            return new Fields(in);
        }

        @Override
        public Fields[] newArray(int size) {
            return new Fields[size];
        }
    };

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getStandfirst() {
        return standfirst;
    }

    public void setStandfirst(String standfirst) {
        this.standfirst = standfirst;
    }

    public String getTrailText() {
        return trailText;
    }

    public void setTrailText(String trailText) {
        this.trailText = trailText;
    }

    public String getByline() {
        return byline;
    }

    public void setByline(String byline) {
        this.byline = byline;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getNewspaperPageNumber() {
        return newspaperPageNumber;
    }

    public void setNewspaperPageNumber(String newspaperPageNumber) {
        this.newspaperPageNumber = newspaperPageNumber;
    }

    public String getWordcount() {
        return wordcount;
    }

    public void setWordcount(String wordcount) {
        this.wordcount = wordcount;
    }

    public String getFirstPublicationDate() {
        return firstPublicationDate;
    }

    public void setFirstPublicationDate(String firstPublicationDate) {
        this.firstPublicationDate = firstPublicationDate;
    }

    public String getIsInappropriateForSponsorship() {
        return isInappropriateForSponsorship;
    }

    public void setIsInappropriateForSponsorship(String isInappropriateForSponsorship) {
        this.isInappropriateForSponsorship = isInappropriateForSponsorship;
    }

    public String getIsPremoderated() {
        return isPremoderated;
    }

    public void setIsPremoderated(String isPremoderated) {
        this.isPremoderated = isPremoderated;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getNewspaperEditionDate() {
        return newspaperEditionDate;
    }

    public void setNewspaperEditionDate(String newspaperEditionDate) {
        this.newspaperEditionDate = newspaperEditionDate;
    }

    public String getProductionOffice() {
        return productionOffice;
    }

    public void setProductionOffice(String productionOffice) {
        this.productionOffice = productionOffice;
    }

    public String getPublication() {
        return publication;
    }

    public void setPublication(String publication) {
        this.publication = publication;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getShouldHideAdverts() {
        return shouldHideAdverts;
    }

    public void setShouldHideAdverts(String shouldHideAdverts) {
        this.shouldHideAdverts = shouldHideAdverts;
    }

    public String getShowInRelatedContent() {
        return showInRelatedContent;
    }

    public void setShowInRelatedContent(String showInRelatedContent) {
        this.showInRelatedContent = showInRelatedContent;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getLegallySensitive() {
        return legallySensitive;
    }

    public void setLegallySensitive(String legallySensitive) {
        this.legallySensitive = legallySensitive;
    }

    public String getSensitive() {
        return sensitive;
    }

    public void setSensitive(String sensitive) {
        this.sensitive = sensitive;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getBodyText() {
        return bodyText;
    }

    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
    }

    public String getCharCount() {
        return charCount;
    }

    public void setCharCount(String charCount) {
        this.charCount = charCount;
    }

    public String getShouldHideReaderRevenue() {
        return shouldHideReaderRevenue;
    }

    public void setShouldHideReaderRevenue(String shouldHideReaderRevenue) {
        this.shouldHideReaderRevenue = shouldHideReaderRevenue;
    }

    public String getShowAffiliateLinks() {
        return showAffiliateLinks;
    }

    public void setShowAffiliateLinks(String showAffiliateLinks) {
        this.showAffiliateLinks = showAffiliateLinks;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(headline);
        dest.writeString(standfirst);
        dest.writeString(trailText);
        dest.writeString(byline);
        dest.writeString(main);
        dest.writeString(body);
        dest.writeString(newspaperPageNumber);
        dest.writeString(wordcount);
        dest.writeString(firstPublicationDate);
        dest.writeString(isInappropriateForSponsorship);
        dest.writeString(isPremoderated);
        dest.writeString(lastModified);
        dest.writeString(newspaperEditionDate);
        dest.writeString(productionOffice);
        dest.writeString(publication);
        dest.writeString(shortUrl);
        dest.writeString(shouldHideAdverts);
        dest.writeString(showInRelatedContent);
        dest.writeString(thumbnail);
        dest.writeString(legallySensitive);
        dest.writeString(sensitive);
        dest.writeString(lang);
        dest.writeString(bodyText);
        dest.writeString(charCount);
        dest.writeString(shouldHideReaderRevenue);
        dest.writeString(showAffiliateLinks);
    }
}
