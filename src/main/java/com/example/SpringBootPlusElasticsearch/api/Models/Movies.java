package com.example.SpringBootPlusElasticsearch.api.Models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Document(indexName = "movies")
public class Movies {
    @Id
    private String id;

    private String title;
    private String plot;
    private List<String> genres;
    private Integer runtime;
    private List<String> cast;

    private Integer num_mflix_comments;
    private String fullplot;
    private List<String> countries;

    private List<String> directors;
    private String rated;
    private Integer year;
    private String type;
    private String poster;
    private Integer metacritic;
    private List<String> languages;

    private IMDB imdb;
    private tomatoes tomatoes;
    private Awards awards;

    public IMDB getImdb() {
        return imdb;
    }

    public void setImdb(IMDB imdb) {
        this.imdb = imdb;
    }

    public com.example.SpringBootPlusElasticsearch.api.Models.tomatoes getTomatoes() {
        return tomatoes;
    }

    public void setTomatoes(com.example.SpringBootPlusElasticsearch.api.Models.tomatoes tomatoes) {
        this.tomatoes = tomatoes;
    }

    public Awards getAwards() {
        return awards;
    }

    public void setAwards(Awards awards) {
        this.awards = awards;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public List<String> getCast() {
        return cast;
    }

    public void setCast(List<String> cast) {
        this.cast = cast;
    }

    public Integer getNum_mflix_comments() {
        return num_mflix_comments;
    }

    public void setNum_mflix_comments(Integer num_mflix_comments) {
        this.num_mflix_comments = num_mflix_comments;
    }

    public String getFullplot() {
        return fullplot;
    }

    public void setFullplot(String fullplot) {
        this.fullplot = fullplot;
    }

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    public List<String> getDirectors() {
        return directors;
    }

    public void setDirectors(List<String> directors) {
        this.directors = directors;
    }

    public String getRated() {
        return rated;
    }

    public void setRated(String rated) {
        this.rated = rated;
    }

//    public Awards getAwards() {
//        return awards;
//    }
//
//    public void setAwards(Awards awards) {
//        this.awards = awards;
//    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

//    public IMDB getImdb() {
//        return imdb;
//    }
//
//    public void setImdb(IMDB imdb) {
//        this.imdb = imdb;
//    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Integer getMetacritic() {
        return metacritic;
    }

    public void setMetacritic(Integer metacritic) {
        this.metacritic = metacritic;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }
//
//    public com.example.SpringBootPlusElasticsearch.api.Models.tomatoes getTomatoes() {
//        return tomatoes;
//    }
//
//    public void setTomatoes(com.example.SpringBootPlusElasticsearch.api.Models.tomatoes tomatoes) {
//        this.tomatoes = tomatoes;
//    }
}
