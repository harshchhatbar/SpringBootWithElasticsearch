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
        // Pageable.unpaged() made possible to show all the document.
        newsHeadLineRepository.findAll(pageable).forEach(newsDocs::add);
        return newsDocs;
    }


}
