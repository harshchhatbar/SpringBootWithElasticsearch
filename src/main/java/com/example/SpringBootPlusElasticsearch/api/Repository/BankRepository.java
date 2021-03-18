package com.example.SpringBootPlusElasticsearch.api.Repository;

import com.example.SpringBootPlusElasticsearch.api.Models.Bank;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends ElasticsearchRepository<Bank,String> {
    // will implement some repositories...
}
