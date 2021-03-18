package com.example.SpringBootPlusElasticsearch.api.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Document(indexName = "news_headlines")
public class NewsHeadLine {

    @Id
    private String id;

    @Field(type = FieldType.Date, format = DateFormat.date)
    private Date date;
    @Field(value = "@timestamp", type = FieldType.Date, format = DateFormat.date_time)
    private Date timestamp;
    @Field(type = FieldType.Text)
    private String authors;
    @Field(type = FieldType.Keyword)
    private String category;
    @Field(type = FieldType.Text)
    private String headline;
    @Field(type = FieldType.Keyword)
    private String link;
    @Field(value = "short_description", type = FieldType.Text)
    private String short_des;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getShort_des() {
        return short_des;
    }

    public void setShort_des(String short_des) {
        this.short_des = short_des;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}

