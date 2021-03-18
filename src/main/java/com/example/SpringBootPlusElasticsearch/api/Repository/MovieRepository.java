package com.example.SpringBootPlusElasticsearch.api.Repository;

import com.example.SpringBootPlusElasticsearch.api.Models.Movies;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends ElasticsearchRepository<Movies,String> {
}
