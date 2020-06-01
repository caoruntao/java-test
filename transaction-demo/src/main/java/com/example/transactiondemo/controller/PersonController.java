package com.example.transactiondemo.controller;

import com.example.transactiondemo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {
    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/person/save/{name}")
    public void save(@PathVariable String name){
        personService.saveWithNoTransaction(name);
    }

    @GetMapping("/person/save2/{name}")
    public void save2(@PathVariable String name){
        personService.save(name);
    }
}
