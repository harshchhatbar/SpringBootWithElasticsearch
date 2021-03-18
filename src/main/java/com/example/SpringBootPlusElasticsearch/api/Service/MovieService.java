package com.example.SpringBootPlusElasticsearch.api.Service;

import com.example.SpringBootPlusElasticsearch.api.Daos.MovieDao;
import com.example.SpringBootPlusElasticsearch.api.Models.Movies;
import org.elasticsearch.client.indices.AnalyzeResponse;
import org.elasticsearch.search.aggregations.Aggregations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private MovieDao movieDao;

    @Autowired
    public MovieService(MovieDao movieDao){
        this.movieDao = movieDao;
    }

    public List<Movies> getAllMovies() {
        return movieDao.getAllMovies();
    }

    public Movies insertMovie(Movies moviesObj) {
        return movieDao.insertMovie(moviesObj);
    }

    public String deleteData(String docId) {
        return movieDao.deleteData(docId);
    }

    public SearchHits<Movies> getMoviesByCastMust(String... cast) {
        return movieDao.getMoviesByCastMust(cast);
    }

    public SearchHits<Movies> getMoviesByTitle(String movieTitle) {
        return movieDao.getMoviesByTitle(movieTitle);
    }

    public SearchHits<Movies> getMoviesByCastShould(int minMatch, String[] cast) {
        if(minMatch > cast.length)
            return null;
        return movieDao.getMoviesByCastShould(minMatch,cast);
    }

    public SearchHits<Movies> getMoviesOfDirectors(String directorName){
        return movieDao.getMoviesOfDirectors(directorName);
    }

    public SearchHits<Movies> getMoviesByFullPlot(String plotData) {
        return movieDao.getMoviesByFullPlot(plotData);
    }

    public List<AnalyzeResponse.AnalyzeToken> trialAnalyzer(String analyzerType, String... textToAnalyze)
    {
        return movieDao.TrialAnalyzer(analyzerType, textToAnalyze);
    }

    public SearchHits<Movies> getMoviesByTitleUpdated(String movie)
    {
        return movieDao.getMoviesByTitleUpdated(movie);
    }

    public SearchHits<Movies> getMoviesByCastExact(String castName) {
        return movieDao.getMoviesByCastExact(castName);
    }

    public org.elasticsearch.search.SearchHits getMoviesByCastExact(String castName, String... ListOfFields)
    {
        return movieDao.getMoviesByCastExact(castName,ListOfFields);
    }

    public SearchHits<Movies> getMoviesByPlot(String movieDescription) {
        return movieDao.getMoviesByPlot(movieDescription);
    }
    public SearchHits<Movies> getMoviesByPlot_2(String movieDescription) {
        return movieDao.getMoviesByPlot_2(movieDescription);
    }

    public SearchHits<Movies> getMoviesByPlot_3(String PlotData)
    {
        return movieDao.getMoviesByPlot_3(PlotData);
    }

    public Aggregations getDirectorsMovieMetric(String... ListOfFields) {
        return movieDao.getDirectorsMovieMetric(ListOfFields);
    }

    public Aggregations getLanguageBasedMetric_1()
    {
        return movieDao.getLanguageBasedMetric_1();
    }
}
