package com.example.SpringBootPlusElasticsearch.api.Daos;

import com.example.SpringBootPlusElasticsearch.api.Models.Bank;
import com.example.SpringBootPlusElasticsearch.api.Repository.BankRepository;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * All the query operations, related to "bank" Index, are performed in this class and
 * All the related model classes, that are used in this class, can be found inside Model Package.
 * <p>
 * Path: "com.example.SpringBootPlusElasticsearch.Models"
 **/
@Component
public class BankDao {

    private ElasticsearchOperations elasticsearchRestTemplate;
    private BankRepository bankRepository;

    @Autowired
    public BankDao(ElasticsearchOperations elasticsearchRestTemplate, BankRepository bankRepository) {
        this.elasticsearchRestTemplate = elasticsearchRestTemplate;
        this.bankRepository = bankRepository;
    }

    /*
        Return all document from bank index.
     */
    public List<Bank> getAllBankAcc() {
        List<Bank> accList = new ArrayList<>();
        Iterable<Bank> ItrAcc = bankRepository.findAll();
        ItrAcc.forEach(accList::add);
        return accList;
    }

    /*
        Get bank accounts of all the person whose
        FirstName is "name"
    */
    public SearchHits<Bank> getAllByFirstName(String name) {
        Criteria criteria = new Criteria("firstname").is(name);
        Query query = new CriteriaQuery(criteria);
        return elasticsearchRestTemplate.search(query, Bank.class);
    }

    /*
        Get bank accounts of all the person who is
        living in given city.
    */
    public SearchHits<Bank> getAllByCity(String name) {
        Criteria criteria = new Criteria("city").is(name);
        Query query = new CriteriaQuery(criteria);
        return elasticsearchRestTemplate.search(query, Bank.class);
    }

    /*
         Get bank accounts of all the person,
            who is living in given state.
            SHOULD have "ha" prefix in FirstName
            SHOULD have age between 30 and 23.
    */
    public SearchHits<Bank> getAllByState(String name) {

        //for "String" type fields, Filter/Aggregation works on only keyword field (Not text field)
        BoolQueryBuilder TrialboolQueryBuilder = QueryBuilders.boolQuery()
                .should(QueryBuilders.rangeQuery("age").lt(30).gt(23))
                .should(QueryBuilders.prefixQuery("firstname", "ha"))
                .filter(QueryBuilders.termQuery("state.keyword", name));

        Query query2 = new NativeSearchQuery(TrialboolQueryBuilder);

        return elasticsearchRestTemplate.search(query2, Bank.class);
    }

}
