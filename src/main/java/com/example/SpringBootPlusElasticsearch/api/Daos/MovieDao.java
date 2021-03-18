package com.example.SpringBootPlusElasticsearch.api.Daos;

import com.example.SpringBootPlusElasticsearch.api.Models.EdgeNgramTokenizer;
import com.example.SpringBootPlusElasticsearch.api.Models.Movies;
import com.example.SpringBootPlusElasticsearch.api.Repository.MovieRepository;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.AnalyzeRequest;
import org.elasticsearch.client.indices.AnalyzeResponse;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.AvgAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.SumAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.*;
import static org.springframework.data.domain.Sort.Order.desc;

/**
    All the query and aggregation operations, related to "Movie" Index, are performed in this class and
    All the related model classes, that are used in this class, can be found inside Model Package.

    Path: "com.example.SpringBootPlusElasticsearch.Models"
**/
@Component
public class MovieDao {
    private final ElasticsearchOperations elasticsearchOperations;
    private final MovieRepository movieRepository;
    private final RestHighLevelClient restHighLevelClient;

        public MovieDao(ElasticsearchOperations elasticsearchOperations,
                    MovieRepository movieRepository,
                        RestHighLevelClient restHighLevelClient){
        this.elasticsearchOperations = elasticsearchOperations;
        this.movieRepository = movieRepository;
        this.restHighLevelClient = restHighLevelClient;
    }

    /*
        Get all the documents of movies.
    */
    public List<Movies> getAllMovies() {
        List<Movies> allMovieList = new ArrayList<>();
        movieRepository.findAll(Pageable.unpaged()).forEach(allMovieList::add);
        return allMovieList;
    }

    /*
         Insert movie into "movie" index.
    */
    public Movies insertMovie(Movies moviesObj) {
        return movieRepository.save(moviesObj);
    }

    /*
        Delete movie from index.
    */
    public String deleteData(String docId) {
        Movies moviesObj = new Movies();
        moviesObj.setId(docId);

        String findMovie = elasticsearchOperations.delete(moviesObj);
        //movieRepository.deleteById(docId);
        return findMovie;
    }

    /*
        All of Cast member given in cast array
        must be in movies...
     */
    public SearchHits<Movies> getMoviesByCastMust(String... cast) {
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        for(String itr:cast){
            boolQueryBuilder.must(new MatchQueryBuilder("cast",itr));
        }
        //MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("cast",cast);
        Query query = new NativeSearchQuery(boolQueryBuilder);
        return elasticsearchOperations.search(query, Movies.class);
    }

    public SearchHits<Movies> getMoviesByTitle(String movieTitle) {
        Criteria criteria = new Criteria("title").is(movieTitle);
        Query query = new CriteriaQuery(criteria);
        return elasticsearchOperations.search(query, Movies.class);
    }

    /*
        Applying bool compound query on cast field.
        And get all the movies which matches at least
        "minMatch" number of cast-members.
    */
    public SearchHits<Movies> getMoviesByCastShould(int minMatch, String[] cast) {
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

        /* Equivalent elasticsearch query:
         *     "query":{
         *           "bool":{
         *              "should":[
         *              {
         *                 "match":{
         *                      "title":{
         *                          "query": movie,
         *                          "analyzer": "keyword"
         *                      }
         *                 }
         *              },
         *              {
         *                  .....
         *              }
         *              ,
         *              .... all the match queries in should clause
         *              ],
         *              "minimum_should_match": size(cast)/2
         *          }
         *      }
         *
         * */

        /*
            "match" is liberal form of "match_phrase".
            "match" will divide given term according to analyzer
            BUT "match_phrase" will take given query as it is
            and then it will search.
        //------------------------------------------------------------------------------------------------------//
            Some basics of boolean compound query.
            "must": clause (query) must appear in matching documents
            "filter": The clause (query) must appear in matching documents
            "should": The clause (query) should appear in the matching document (Help to reorder document
            , also help to boost relevant result)
         */

        for(String itr:cast)
            boolQueryBuilder.should(new MatchQueryBuilder("cast",itr));

        // By default if there are more than one should clauses then it will set minimum_should_match = 1.
        // minimum number of should clauses must be satisfied by document in order to be included into result set
        // We can explicitly set it with any value less than number of should clauses.
        boolQueryBuilder.minimumShouldMatch(minMatch);
        Query query = new NativeSearchQuery(boolQueryBuilder);
        return elasticsearchOperations.search(query, Movies.class);
    }

    /*
        Full-text-search query using match on "fullplot" field
    */
    public SearchHits<Movies> getMoviesByFullPlot(String plotData) {

        /* Equivalent elasticsearch query:
         *     "query":{
         *           "match":{
         *               "fullplot":{
         *                   "query": plotData,
         *                   "analyzer": "standard"
         *               }
         *           }
         *      }
         *
         * */

        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("fullplot",plotData)
                .analyzer("standard");

        Query query = new NativeSearchQuery(matchQueryBuilder);
        return elasticsearchOperations.search(query, Movies.class);
    }

    private List<AnalyzeResponse.AnalyzeToken> buildTokens(AnalyzeRequest requestAnalyzer)
    {
        try{
            AnalyzeResponse response = restHighLevelClient.indices().analyze(requestAnalyzer, RequestOptions.DEFAULT);
            List<AnalyzeResponse.AnalyzeToken> tokenList = response.getTokens();
            for(AnalyzeResponse.AnalyzeToken itr: tokenList)
                System.out.println(itr.getTerm());
//             System.out.println(tokenList.size());
//             toStringAnalyzeToken is now serializable and can be used in place of AnalyeToken.
//             List<toStringAnalyzeToken> finalTokenList = new ArrayList<>();
//             for(AnalyzeResponse.AnalyzeToken itr:tokenList){
//                finalTokenList.add(new toStringAnalyzeToken(itr));
//                System.out.println(finalTokenList);
//             }
            return tokenList;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
            Below code is equivalent to what we can we with this
            endpoint "GET movies/_analyze".
    */
    public List<AnalyzeResponse.AnalyzeToken> TrialAnalyzer(String AnalyzerType, String... textToAnalyze)
    {
        // All the Token filters (and Char_filers also) defined here anyone(any analyzer) can use it.
        Map<String, Object> StandardTokenizer = new LinkedHashMap<>();
        StandardTokenizer.put("type", "standard");

        // Using builder class, it helps to write cleaner code compare to writing all the properties
        // manually everytime.
        EdgeNgramTokenizer edgeNgramTokenizer =
                new EdgeNgramTokenizer.Builder()
                        .setMinGram(3)
                        .setMaxGram(6)
                        .setTokenChars(new String[]{"letter","digit"})
                        .build();

        Map<String, Object> EdgeNgramTokenizer = new LinkedHashMap<>();
        EdgeNgramTokenizer.put("type","edge_ngram");
        EdgeNgramTokenizer.put("min_gram",3);
        EdgeNgramTokenizer.put("max_gram",6);

        /*
            This particularly will break the token whenever it encounters a character which
            doesn't belong to list of token_chars --> array. Helps to create(break) a big text
            into meaningful token(as well as its edge_ngram tokens).
        */
        EdgeNgramTokenizer.put("token_chars",new String[]{"letter","digit"});

        Map<String, Object> StopwordTokenFilter = new LinkedHashMap<>();
        StopwordTokenFilter.put("type", "stop");
        StopwordTokenFilter.put("stopwords", "_english_");

        switch (AnalyzerType)
        {
            case "standard":
            {
                AnalyzeRequest analyzeRequestOnStandard = AnalyzeRequest.buildCustomAnalyzer(StandardTokenizer)
                        .addCharFilter("html_strip")
                        .addTokenFilter("lowercase")
                        .addTokenFilter(StopwordTokenFilter)
                        .build(textToAnalyze);
                return buildTokens(analyzeRequestOnStandard);
            }
            case "edge_ngram":
            {
                AnalyzeRequest analyzeRequestOnEdgeNgram =
                        AnalyzeRequest.buildCustomAnalyzer(edgeNgramTokenizer.getTokenizer())
                        .addTokenFilter("lowercase")
                        .addCharFilter("html_strip")
                        .build(textToAnalyze);
                return buildTokens(analyzeRequestOnEdgeNgram);
            }
            default:
            {
                System.out.println("Enter valid Analyzer name....\n");
                return null;
            }
        }
    }

    public SearchHits<Movies> getMoviesByTitleUpdated(String movie)
    {
        /* Equivalent elasticsearch query:
        *     "query":{
        *           "match":{
        *               "title":{
        *                   "query": movie,
        *                   "analyzer": "standard",
        *                   "operator": "and",
        *                   "fuzziness": "AUTO"
        *               }
        *           }
        *      }
        *
        * */

        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("title", movie)
                .analyzer("standard")
                .fuzziness(Fuzziness.AUTO)
                .operator(Operator.AND);

        Query query = new NativeSearchQuery(matchQueryBuilder)
                .addSort(Sort.by(desc("awards.wins")));

        return elasticsearchOperations.search(query, Movies.class);
    }

    /*
        Find all the movies of director (some variation of given directorName
         will be considered for query because of Fuzziness).
         Then sort document result according to their IMDB rating.
    */

    public SearchHits<Movies> getMoviesOfDirectors(String directorName)
    {
        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("directors", directorName)
                .fuzziness(Fuzziness.AUTO);

        Query query = new NativeSearchQuery(matchQueryBuilder)
                .addSort(Sort.by(desc("imdb.rating")));

        return elasticsearchOperations.search(query, Movies.class);
    }

    /*
           Find all Movies in which given castName exists
           as part of "cast" Array field.
    */
    public SearchHits<Movies> getMoviesByCastExact(String castName) {

            /* Equivalent elasticsearch query:
            * {
            *      "query": {
            *           "match":{
            *               "cast": castName
            *           }
            *       }
            * }
            * */

            Criteria criteria = new Criteria("cast").matches(castName);
            /* Equivalent elasticsearch query:
             * {
             *      "query": {
             *           "match":{
             *               "cast": {
             *                  "query": castName,
             *                  "operator": "and"
             *              }
             *           }
             *       }
             * } */

            Criteria criteria2 = new Criteria("cast").in(castName);
            Query query = new CriteriaQuery(criteria);

        return elasticsearchOperations.search(query, Movies.class);
    }

    /*
            Overloaded the previous method.
            Main goal of writing this function is to get an idea about how to project specific
            fields in elasticsearch.
    */

    public org.elasticsearch.search.SearchHits getMoviesByCastExact(String castName, String... ListOfFields)
    {
        // How one can project specific fields in query.
        String[] includeFields = ListOfFields;
        String[] excludeFields = new String[] {};

        SearchRequest searchRequest = new SearchRequest("movies");
        SearchSourceBuilder searchSourceBuilder =  new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchPhraseQuery("cast",castName));
        searchSourceBuilder.fetchSource(includeFields,excludeFields);
        searchRequest.source(searchSourceBuilder);

        try{
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            org.elasticsearch.search.SearchHits hits = searchResponse.getHits();
            System.out.println(hits.getTotalHits());
            return hits;
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /*
           Find all relevant Movie list using "MatchBoolPrefix" on Plot field
           (can be applied to FullPlot field)
    */
    public SearchHits<Movies> getMoviesByPlot(String movieDescription) {

            /*  query for "Avenger save Uni"
                In MatchPhrasePrefixQuery, Order of terms in query will remain same as given
                and last word will be queried as prefix: "Uni".
                However, "slop" attribute allow some liberalness.

                SLOP: (Optional, integer) Maximum number of positions allowed between matching tokens.
                Defaults to 0. Transposed terms have a slop of 2.
            */
            /*
                In contras to MatchBoolPrefixQuery, Order of terms are not maintained
                and last word will be queried as prefix: "Uni".
            */

            /*  Elasticsearch equivalent query
            *   "query": {
            *       "bool":{
            *           "should":[
            *               {"term": {"fieldName": "first_token"}},
            *               {"term": {"fieldName": "second_token"}},
            *               {"term": {"fieldName": "third_token"}},
            *               .....
            *               .....
            *               .....
            *               {"prefix": {"fieldName": "Last_Token"}}
            *           ]
            *       }
            *   }
            * */

            // String[] words = movieDescription.split("\\s+");

            MatchBoolPrefixQueryBuilder matchingBuilder =
                    new MatchBoolPrefixQueryBuilder("plot",movieDescription)
                    .analyzer("standard")
                    .minimumShouldMatch("75%");
            // Fraction of words in given string that should be matched in order to be able to put
            // document in result set.
            Query query = new NativeSearchQuery(matchingBuilder);
            return elasticsearchOperations.search(query, Movies.class);
    }

    /*
           Find all relevant Movies using "MatchPhrasePrefix" on Plot field
           using given movieDescription.
           (can be applied to FullPlot field)
    */
    public SearchHits<Movies> getMoviesByPlot_2(String movieDescription) {

            /*  query for "Avenger Saves Uni"
                In MatchPhrasePrefixQuery, Order of terms in query will remain same as given
                and last word will be queried as prefix: "Uni"
                However, "slop" attribute allow some liberalness.

                SLOP: (Optional, integer) Maximum number of positions allowed between matching tokens.
                Defaults to 0. Transposed terms have a slop of 2.
            */
            /*
                In contras to MatchBoolPrefixQuery, Order of terms are not maintained
                and last word will be queried as prefix: "Uni".
            */

        /*  Elasticsearch equivalent query (Not quite sure but seems it should be this)
         *   "query": {
         *       "bool":{
         *           "should":[
         *               {"term": {"fieldName": "Whole Text except Last_Token" } },
         *               {"prefix": {"fieldName": "Last_Token"}}
         *           ]
         *       }
         *   }
         * */

        MatchPhrasePrefixQueryBuilder matchingBuilder =
                new MatchPhrasePrefixQueryBuilder("plot",movieDescription);

        Query query = new NativeSearchQuery(matchingBuilder);
        return elasticsearchOperations.search(query, Movies.class);
    }

    /*
            Find Relevant movies using Multi-Match Query
            on Plot and FullPlot fields using different types.
    */
    public SearchHits<Movies> getMoviesByPlot_3(String PlotData)
    {
        // To get all the words in string which are space separated.
        //String[] words = PlotData.split("\\s+");
        /*
                "BEST FIELDS"
        */
        /* ElasticSearch Equivalent query:
        *   "query":{
        *       "dis_max":{
        *           "queries":[
        *               {
        *                   "match": {
        *                       "plot":{
        *                           "query": PlotData
        *                           "minimum_should_match": "50%"
        *                       }
        *                   }
        *               },
        *               {
        *                   "match": {
        *                       "fullplot":{
        *                           "query": PlotData
        *                           "minimum_should_match": "50%"
        *                       }
        *                   }
        *               },
        *           ],
        *           "tie_breaker": 0.5
        *       }
        *   }
        * NOTE:
        * In BEST_MATCH, minimum_should_match(same goes for OPERATOR) applies at field level as shown above.
        * Operator: this will apply at field level, and may put very strict constrain.
        * Scoring: uses the _score from the best field.
        * */

        MultiMatchQueryBuilder multiMatchQueryBuilder =
                new MultiMatchQueryBuilder(PlotData, "plot","fullplot")
                .type(MultiMatchQueryBuilder.Type.BEST_FIELDS)
                .tieBreaker(0.5F)
                .minimumShouldMatch("50%");

        Query BestFieldQuery = new NativeSearchQuery(multiMatchQueryBuilder);

        /*
                "MOST FIELDS"
        */
        /* ElasticSearch Equivalent query:
         *   "query":{
         *       "bool":{
         *           "should":[
         *               {
         *                   "match": {
         *                       "plot":{
         *                           "query": PlotData
         *                           "minimum_should_match": "50%"
         *                       }
         *                   }
         *               },
         *               {
         *                   "match": {
         *                       "fullplot":{
         *                           "query": PlotData
         *                           "minimum_should_match": "50%"
         *                       }
         *                   }
         *               },
         *           ]
         *       }
         *   }
         *  Scoring: combines the _score from each field.
         * */

        /*
             BEST_FIELD and MOST_FIELD will return same result set. Just score assign to each document is
             different.
        */

        Map<String, Float> ScoreMap = new HashMap<>();
        /*
            ScoreMap(Assigning score OR (some kind of importance) to each field)
            This will reorder documents of result set.
        */

        //ScoreMap.put("fullplot",3F);
        MultiMatchQueryBuilder multiMatchQueryBuilder1 =
                new MultiMatchQueryBuilder(PlotData, "plot","fullplot")
                .type(MultiMatchQueryBuilder.Type.MOST_FIELDS)
                .minimumShouldMatch("50%")
                .fields(ScoreMap);

        Query MostFieldQuery = new NativeSearchQuery(multiMatchQueryBuilder1);
        /*
                "CROSS FIELDS"
        */

        /* - All the fields which has same analyzer will group together and behave as one big field.
         * - Then it will try to find given query content into these big grouped field.
         * - we can set analyzer = "" attribute and force all the fields to group together and find
         *   query content into it.
         *  Scoring: DON'T KNOW
         *
         * IMPORTANT NOTE:
         *     HERE, Unlike BEST_FIELDS and MOST_FIELDS, operator and Minimum_should_match attributes works
         *     at term level. If operator is "AND", All the given terms should be in at least one of fields.
         * */

        Map<String, Float> ScoreMapMultiMatch = new HashMap<>();
        ScoreMapMultiMatch.put("fullplot",3F);

        // Should create builder instead of writing all this things everytime
        // High chances for mis-spelling attribute names.

        MultiMatchQueryBuilder multiMatchQueryBuilder2 =
                new MultiMatchQueryBuilder(PlotData, "plot","fullplot")
                .type(MultiMatchQueryBuilder.Type.CROSS_FIELDS)
                .fields(ScoreMapMultiMatch)
                .operator(Operator.AND);

        Query CrossFieldQuery = new NativeSearchQuery(multiMatchQueryBuilder2);
        /*
            There are other types also like BoolPrefix, Phrase, PrefixPhrase.
            They are exactly same as BestType just difference is that they apply
            match_bool_prefix, match_phrase, match_prefix_phrase clauses respectively
            instead of match.
        */
        return elasticsearchOperations.search(CrossFieldQuery, Movies.class);
    }

    // Some other full-text search queries are remaining.

    /*
        Top-30 Directors on basis of Total number of awards.
     */
    public Aggregations getDirectorsMovieMetric(String... ListOfFields)
    {
        SumAggregationBuilder MaxSubAgg = new SumAggregationBuilder("max-awards-agg")
                .field("awards.wins");

        TermsAggregationBuilder aggregationBuilder = new TermsAggregationBuilder("my-term-agg")
                .field("directors.keyword")
                .subAggregation(MaxSubAgg)
                .order(BucketOrder.aggregation("max-awards-agg",false))
                .size(30);

        SearchRequest searchRequest = new SearchRequest("movies");

        SearchSourceBuilder searchSourceBuilder =  new SearchSourceBuilder()
                    .aggregation(aggregationBuilder);

        searchRequest.source(searchSourceBuilder);
        try{
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            return searchResponse.getAggregations();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /*
           Creating bucket by directors then
                for each one director creating bucket by movie language then
                    for each movie language finding one Metric-Avg Imdb Rating.
    */
    public Aggregations getLanguageBasedMetric_1()
    {
        AvgAggregationBuilder AvgSubAgg = new AvgAggregationBuilder("sub-max-rating-agg")
                .field("imdb.rating");

        TermsAggregationBuilder TermSubAgg = new TermsAggregationBuilder("sub-term-lang-agg")
                .field("languages")
                .subAggregation(AvgSubAgg);

        TermsAggregationBuilder aggregationBuilder = new TermsAggregationBuilder("my-lang-term-agg")
                .field("directors.keyword")
                .size(20)
                .subAggregation(TermSubAgg);

        SearchRequest searchRequest = new SearchRequest("movies");
        /*
              Unlike query filter, PostFilter will not affect aggregation.

              1) So, If we apply PostFilter, aggregation will be applied to all data.
              2) And, If we apply Filter(in "query" clause), aggregation will be applied to
              the data which is returned by Filter.
        */

        SearchSourceBuilder searchSourceBuilder =  new SearchSourceBuilder()
                .aggregation(aggregationBuilder);

        searchRequest.source(searchSourceBuilder);
        try{
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            return searchResponse.getAggregations();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}

