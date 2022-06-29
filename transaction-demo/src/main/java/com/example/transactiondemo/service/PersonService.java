package com.example.transactiondemo.service;

import com.example.transactiondemo.mapper.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonService {
    private final PersonMapper personMapper;

    @Autowired
    PersonService personService;

    @Autowired
    public PersonService(PersonMapper personMapper) {
        this.personMapper = personMapper;
    }

    @Transactional
    public void save(String name){
        personMapper.save(name);
        try {
            personService.saveWithNoTransaction(name);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveWithNoTransaction(String name){
        personMapper.save(name);
        int i = 1 / 0;
    }
}
