package com.example.SpringBootPlusElasticsearch.api.Controller;

import com.example.SpringBootPlusElasticsearch.api.Models.NewsHeadLine;
import com.example.SpringBootPlusElasticsearch.api.Service.NewsHeadLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/*
    Methods from this class will accept http request prefix by "/bank".
    And redirect it to appropriate controller class.
*/
@RestController
@RequestMapping("/news")
public class NewsHeadLineController {
    private NewsHeadLineService newsHeadLineService;

    @Autowired
    public NewsHeadLineController(NewsHeadLineService newsHeadLineService)
    {
        this.newsHeadLineService = newsHeadLineService;
    }

    @RequestMapping("/getAll")
    public List<NewsHeadLine> getAllDoc()
    {
        List<NewsHeadLine> newsList = new ArrayList<>();
        newsList = newsHeadLineService.getAllDoc();
        return newsList;
    }
}
