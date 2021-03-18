package com.example.SpringBootPlusElasticsearch.api.Models;

public class IMDB {
    private Double rating;
    private Integer votes;
    private Integer id;

    @Override
    public String toString() {
        return "IMDB{" +
                "rating=" + rating +
                ", votes=" + votes +
                ", id=" + id +
                '}';
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
