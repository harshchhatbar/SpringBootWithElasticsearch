package com.example.SpringBootPlusElasticsearch.api.Controller;

import com.example.SpringBootPlusElasticsearch.api.Models.Bank;
import com.example.SpringBootPlusElasticsearch.api.Service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bank")
public class BankController {

    private BankService bankService;

    @Autowired
    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @RequestMapping("/")
    public String checking() {
        return "Checking";
    }

    @RequestMapping("/getAll")
    public List<Bank> getAllBankAcc() {
        return bankService.getAllBankAcc();
    }

    @RequestMapping("/FName/{FirstName}")
    public SearchHits<Bank> getAllByFirstName(@PathVariable("FirstName") String name)
    {
        return bankService.getAllByFirstName(name);
    }

    @RequestMapping("/city/{CityName}")
    public SearchHits<Bank> getAllByCity(@PathVariable("CityName") String name)
    {
        return bankService.getAllByCity(name);
    }

    @RequestMapping("/state/{StateName}")
    public SearchHits<Bank> getAllByState(@PathVariable("StateName") String name)
    {
        return bankService.getAllByState(name);
    }

}
