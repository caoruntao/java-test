package com.crt.jpademo.controller;

import com.crt.jpademo.entity.Person;
import com.crt.jpademo.repository.PersonRepository;
import com.crt.jpademo.service.PersonService;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
public class PersonController {

    private final PersonService personService;
    private final PersonRepository personRepository;

    public PersonController(PersonService personService, PersonRepository personRepository) {
        this.personService = personService;
        this.personRepository = personRepository;
    }

    /*@PostMapping("/person/save")
    public void savePerson(@RequestBody Person person){
        personService.save(person);
    }*/

    /*@GetMapping("/person/findAll")
    public Page<Person> findAll(Pageable pageable){
        return personRepository.findAll(pageable);
    }*/

    @RequestMapping("/*")
    public Page<Person> findAll(Pageable pageable){
        return personRepository.findAll(pageable);
    }
}
