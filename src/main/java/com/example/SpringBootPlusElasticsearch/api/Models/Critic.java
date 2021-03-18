package com.example.SpringBootPlusElasticsearch.api.Models;

public class Critic {
    private Double rating;
    private Integer numReviews;
    private Integer meter;

    @Override
    public String toString() {
        return "Critic{" +
                "rating=" + rating +
                ", numReviews=" + numReviews +
                ", meter=" + meter +
                '}';
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getNumReviews() {
        return numReviews;
    }

    public void setNumReviews(Integer numReviews) {
        this.numReviews = numReviews;
    }

    public Integer getMeter() {
        return meter;
    }

    public void setMeter(Integer meter) {
        this.meter = meter;
    }
}
