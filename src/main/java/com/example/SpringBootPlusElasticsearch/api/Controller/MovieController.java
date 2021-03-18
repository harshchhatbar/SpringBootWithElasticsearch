package com.example.SpringBootPlusElasticsearch.api.Controller;

import com.example.SpringBootPlusElasticsearch.api.Models.Movies;
import com.example.SpringBootPlusElasticsearch.api.Service.MovieService;
import org.elasticsearch.client.indices.AnalyzeResponse;
import org.elasticsearch.search.aggregations.Aggregations;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private MovieService movieService;
    @Autowired
    public MovieController(MovieService movieService){
        this.movieService = movieService;
    }

    @GetMapping("/getAll")
    public List<Movies> getAllMovies()
    {
        return movieService.getAllMovies();
    }

    @GetMapping("/title/{name}")
    public SearchHits<Movies> getMoviesByTitle(@PathVariable("name") String movieTitle)
    {
        return movieService.getMoviesByTitle(movieTitle);
    }

    @PostMapping("/addData")
    public Movies insertData(@RequestBody Movies moviesObj)
    {
        return movieService.insertMovie(moviesObj);
    }

    @DeleteMapping("/{DocId}")
    public String deleteData(@PathVariable String DocId)
    {
        String returnId = movieService.deleteData(DocId);
        Assert.assertEquals("ID of deleted Document Should be same as Given ID", DocId, returnId);
        return movieService.deleteData(DocId);
    }

    @GetMapping("/castMust")
    public SearchHits<Movies> getMoviesByCastMust(@RequestBody  String... cast)
    {
        return movieService.getMoviesByCastMust(cast);
    }

    @GetMapping("/castShould/{minShouldMatch}")
    public SearchHits<Movies> getMoviesByCastShould(@PathVariable("minShouldMatch") int minMatch,
                                                    @RequestBody String... cast)
    {
        return movieService.getMoviesByCastShould(minMatch,cast);
    }

    @GetMapping("/FullPlotSearch")
    public SearchHits<Movies> getMoviesByFullPlot(@RequestBody String plotData)
    {
        return movieService.getMoviesByFullPlot(plotData);
    }

    @GetMapping("/Analyzer/{type}")
    public List<AnalyzeResponse.AnalyzeToken> trialAnalyzer(@PathVariable("type") String analyzerType,
                                                            @RequestBody String... textToAnalyze)
    {
        System.out.println(textToAnalyze);
        return movieService.trialAnalyzer(analyzerType, textToAnalyze);
    }

    @GetMapping("/titleUp")
    public SearchHits<Movies> getMoviesByTitleUpdated(@RequestBody String movie)
    {
        return movieService.getMoviesByTitleUpdated(movie);
    }

    @GetMapping("/director/{DirectorName}")
    public SearchHits<Movies> getMoviesOfDirectors(@PathVariable("DirectorName") String directorName)
    {
        return movieService.getMoviesOfDirectors(directorName);
    }

    @GetMapping("/castExact/{name}")
    public SearchHits<Movies> getMoviesByCastExact(@PathVariable("name") String castName)
    {
        return movieService.getMoviesByCastExact(castName);
    }

    @GetMapping("/castExactCustomFields/{name}")
    public org.elasticsearch.search.SearchHits getMoviesByCastExact(@PathVariable("name") String castName,@RequestBody String... ListOfFields)
    {
        return movieService.getMoviesByCastExact(castName, ListOfFields);
    }

    @GetMapping("/PlotSearch1")
    public SearchHits<Movies> getMoviesByPlot(@RequestBody String movieDescription)
    {
        return movieService.getMoviesByPlot(movieDescription);
    }

    @GetMapping("/PlotSearch2")
    public SearchHits<Movies> getMoviesByPlot_2(@RequestBody String movieDescription)
    {
        return movieService.getMoviesByPlot_2(movieDescription);
    }

    @GetMapping("/PlotSearch3")
    public SearchHits<Movies> getMoviesByPlot_3(@RequestBody String PlotData)
    {
        return movieService.getMoviesByPlot_3(PlotData);
    }

    @GetMapping("/AggDirector")
    public Aggregations getDirectorsMovieMetric(@RequestBody String... ListOfFields)
    {
        return movieService.getDirectorsMovieMetric(ListOfFields);
    }

    @GetMapping("/AggDirLang")
    public Aggregations getLanguageBasedMetric_1()
    {
        return movieService.getLanguageBasedMetric_1();
    }
}
