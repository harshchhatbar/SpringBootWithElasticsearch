package com.example.SpringBootPlusElasticsearch.api.Models;

import java.util.LinkedHashMap;
import java.util.Map;

public class EdgeNgramTokenizer {
    private Map<String, Object> tokenizer;

    public EdgeNgramTokenizer(Map<String, Object> tokenizer){
        this.tokenizer = tokenizer;
    }

    public Map<String, Object> getTokenizer() {
        return tokenizer;
    }

    public void setTokenizer(Map<String, Object> tokenizer) {
        this.tokenizer = tokenizer;
    }

    public static class Builder{
        Map<String, Object> tokenizer;

        public Builder(){
            this.tokenizer = new LinkedHashMap<>();
            this.tokenizer.put("type","edge_ngram");
        }

        public Builder setMinGram(int minGram){
            this.tokenizer.put("min_gram", minGram);
            return this;
        }

        public Builder setMaxGram(int maxGram){
            this.tokenizer.put("max_gram", maxGram);
            return this;
        }

        public Builder setTokenChars(String[] ListChars){
            this.tokenizer.put("token_chars", ListChars);
            return this;
        }

        public EdgeNgramTokenizer build(){
            EdgeNgramTokenizer ObjCreated = new EdgeNgramTokenizer(this.tokenizer);
            return ObjCreated;
        }
    }

}
