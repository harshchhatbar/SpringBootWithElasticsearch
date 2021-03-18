package com.example.SpringBootPlusElasticsearch.api.Repository;

import com.example.SpringBootPlusElasticsearch.api.Models.Movies;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends ElasticsearchRepository<Movies,String> {
    // will implement some methods
}
