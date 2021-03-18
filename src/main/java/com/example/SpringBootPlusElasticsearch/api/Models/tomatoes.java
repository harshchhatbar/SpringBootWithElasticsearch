package com.example.SpringBootPlusElasticsearch.api.Models;

public class tomatoes {
    private String website;
    private String boxOffice;
    private String production;
    private Critic viewer;

    private Critic critic;

    private Integer rotten;
    private Integer fresh;

    @Override
    public String toString() {
        return "tomatoes{" +
                "website='" + website + '\'' +
                ", boxOffice='" + boxOffice + '\'' +
                ", production='" + production + '\'' +
                ", viewer=" + viewer +
                ", critic=" + critic +
                ", rotten=" + rotten +
                ", fresh=" + fresh +
                '}';
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getBoxOffice() {
        return boxOffice;
    }

    public void setBoxOffice(String boxOffice) {
        this.boxOffice = boxOffice;
    }

    public String getProduction() {
        return production;
    }

    public void setProduction(String production) {
        this.production = production;
    }

    public Critic getViewer() {
        return viewer;
    }

    public void setViewer(Critic viewer) {
        this.viewer = viewer;
    }

    public Critic getCritic() {
        return critic;
    }

    public void setCritic(Critic critic) {
        this.critic = critic;
    }

    public Integer getRotten() {
        return rotten;
    }

    public void setRotten(Integer rotten) {
        this.rotten = rotten;
    }

    public Integer getFresh() {
        return fresh;
    }

    public void setFresh(Integer fresh) {
        this.fresh = fresh;
    }
}
