package com.crt.messageconverter.controller;

import com.crt.messageconverter.pojo.Person;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Reed
 * @date 2020/8/26 10:55 上午
 */
@RestController
public class ConvertController {

    @GetMapping(value = "/convert")
    public Person convert(@RequestBody Person person){
        return person;
    }

    @GetMapping(value = "/json/to/properties",
            consumes = "application/json", produces = "application/properties")
    public Person json2Properties(@RequestBody Person person){
        return person;
    }

    @GetMapping(value = "/properties/to/json",
            consumes = "application/properties", produces = "application/json")
    public Person properties2Json(@RequestBody Person person){
        return person;
    }
}
