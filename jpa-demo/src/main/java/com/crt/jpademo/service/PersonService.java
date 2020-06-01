package com.crt.jpademo.service;

import com.crt.jpademo.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Service
@Transactional
public class PersonService {

   // @Autowired
    @PersistenceContext
    private EntityManager entityManager;

    public void save(Person person){
        entityManager.persist(person);
    }
}
