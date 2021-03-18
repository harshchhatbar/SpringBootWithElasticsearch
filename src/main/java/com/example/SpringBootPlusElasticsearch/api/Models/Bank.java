package com.example.SpringBootPlusElasticsearch.api.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

@Document(indexName = "bank")
public class Bank {
    @Id
    private String id;
    @Field(name = "account_number")
    private Integer acc_num;
    private String city;
    private Integer age;
    private Integer balance;
    private String address;
    private String email;
    private String employer;
    private String firstname;
    private String lastname;
    private String gender;
    private String state;

    @Override
    public String toString() {
        return "Bank{" +
                "id='" + id + '\'' +
                ", acc_num=" + acc_num +
                ", city='" + city + '\'' +
                ", age=" + age +
                ", balance=" + balance +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", employer='" + employer + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", gender='" + gender + '\'' +
                ", state='" + state + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getAcc_num() {
        return acc_num;
    }

    public void setAcc_num(Integer acc_num) {
        this.acc_num = acc_num;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
