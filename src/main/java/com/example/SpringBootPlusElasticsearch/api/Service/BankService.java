package com.example.SpringBootPlusElasticsearch.api.Service;

import com.example.SpringBootPlusElasticsearch.api.Daos.BankDao;
import com.example.SpringBootPlusElasticsearch.api.Models.Bank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BankService {

    private BankDao bankDao;

    // There is no need of autowire here as there is exactly one contructor is there.
    // For better understanding we should add @Autowire.
    @Autowired
    public BankService(BankDao bankDao)
    {
        this.bankDao = bankDao;
    }

    public List<Bank> getAccByFirstName(String firstName)
    {
        List<Bank> accList = new ArrayList<>();

        return accList;
    }

    public List<Bank> getAllBankAcc() {
        return bankDao.getAllBankAcc();
    }

    public SearchHits<Bank> getAllByFirstName(String name)
    {
        return bankDao.getAllByFirstName(name);
    }

    public SearchHits<Bank> getAllByCity(String name) {
        return bankDao.getAllByCity(name);
    }

    public SearchHits<Bank> getAllByState(String name) {
        return bankDao.getAllByState(name);
    }
}
