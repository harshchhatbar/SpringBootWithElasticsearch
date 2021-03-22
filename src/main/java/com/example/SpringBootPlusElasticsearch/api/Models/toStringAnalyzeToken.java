package com.example.SpringBootPlusElasticsearch.api.Models;

import org.elasticsearch.client.indices.AnalyzeResponse;
import org.springframework.data.annotation.Persistent;

import java.io.Serializable;
import java.util.Map;

// e.g Method in Java is --> "<returnType> get<Name>"
// Output will be,
// <Name>: <Value> (which is return from getter method)
public class toStringAnalyzeToken implements Serializable {
    private AnalyzeResponse.AnalyzeToken TokenObj;
    public toStringAnalyzeToken(AnalyzeResponse.AnalyzeToken TokenObj) {
        this.TokenObj = TokenObj;
    }

    public String getTerm() {
        return TokenObj.getTerm();
    }

    public String getType() {
        return TokenObj.getType();
    }

    public int getPosition() {
        return TokenObj.getPosition();
    }

    public int getStartOffset() {
        return TokenObj.getStartOffset();
    }

    public int getEndOffset() {
        return TokenObj.getEndOffset();
    }

    public Map<String, Object> getAttributes() {
        return TokenObj.getAttributes();
    }

    @Override
    public String toString() {
        String output = "";
        output = "Token: " + "\n"
        + "Term: " +TokenObj.getTerm() + ",\n"
        + "Type: " +TokenObj.getType() + ",\n"
        + "Position: " +TokenObj.getPosition() + ",\n"
        + "Start Offset: " + TokenObj.getStartOffset() + ",\n"
        + "End Offset: " + TokenObj.getEndOffset() + ",\n"
        + "Attributes: " +TokenObj.getAttributes() + "\n";
        return output;
    }
}
