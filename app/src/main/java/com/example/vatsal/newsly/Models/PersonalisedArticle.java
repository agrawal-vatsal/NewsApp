package com.example.vatsal.newsly.Models;

public class PersonalisedArticle extends ArticleInterface {

    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String category;

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getUrlToImage() {
        return urlToImage;
    }

    public String getCategory() {
        return category;
    }

    public PersonalisedArticle(Article article, String category) {
        this.title = article.getTitle();
        this.description = article.getDescription();
        this.url = article.getUrl();
        this.urlToImage = article.getUrlToImage();
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
