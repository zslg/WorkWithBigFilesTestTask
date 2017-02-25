package com.company;

import java.util.List;
import java.util.Objects;


public class StatisticDto {

    private String id;
    private String productId;
    private String userId;
    private String profileName;
    private String text;

    private List<String> textWords;
    private String textTranslated;

    public StatisticDto(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getTextWords() {
        return textWords;
    }

    public void setTextWords(List<String> textWords) {
        this.textWords = textWords;
    }

    public String getTextTranslated() {
        return textTranslated;
    }

    public void setTextTranslated(String textTranslated) {
        this.textTranslated = textTranslated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatisticDto that = (StatisticDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(productId, that.productId) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(profileName, that.profileName) &&
                Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productId, userId, profileName, text);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("StatisticDto{");
        sb.append("id='").append(id).append('\'');
        sb.append(", productId='").append(productId).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", profileName='").append(profileName).append('\'');
        sb.append(", text='").append(text).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
