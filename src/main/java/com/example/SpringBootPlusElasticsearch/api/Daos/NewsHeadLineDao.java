package com.example.SpringBootPlusElasticsearch.api.Daos;

import com.example.SpringBootPlusElasticsearch.api.Models.NewsHeadLine;
import com.example.SpringBootPlusElasticsearch.api.Repository.NewsHeadLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 All the query operations, related to "news_headline" Index, are performed in this class and
 All the related model classes, that are used in this class, can be found inside Model Package.

 Path: "com.example.SpringBootPlusElasticsearch.Models"
 **/

@Component
public class NewsHeadLineDao {

    private ElasticsearchOperations elasticsearchOperations;
    private NewsHeadLineRepository newsHeadLineRepository;

    @Autowired
    public NewsHeadLineDao(ElasticsearchOperations elasticsearchOperations,
                           NewsHeadLineRepository newsHeadLineRepository){
        this.elasticsearchOperations = elasticsearchOperations;
        this.newsHeadLineRepository = newsHeadLineRepository;
    }

    public List<NewsHeadLine> getAllDoc() {
        List<NewsHeadLine> newsDocs =  new ArrayList<>();
        Pageable pageable = Pageable.unpaged();
        newsHeadLineRepository.findAll(pageable).forEach(newsDocs::add);
        return newsDocs;
    }


}
