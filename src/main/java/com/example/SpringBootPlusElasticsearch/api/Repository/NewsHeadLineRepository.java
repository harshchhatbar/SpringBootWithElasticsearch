package com.example.SpringBootPlusElasticsearch.api.Repository;

import com.example.SpringBootPlusElasticsearch.api.Models.NewsHeadLine;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsHeadLineRepository extends ElasticsearchRepository<NewsHeadLine, String> {
    // will implement some methods..
}
