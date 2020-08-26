package com.crt.messageconverter.convert;

import com.crt.messageconverter.pojo.Person;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Properties;

/**
 * @author Reed
 * @date 2020/8/26 10:42 上午
 */
public class MyMessageConverter extends AbstractHttpMessageConverter<Person>{
    public MyMessageConverter() {
        super(Charset.forName("UTF-8"), new MediaType("application", "properties"));
    }

    @Override
    protected boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(Person.class);
    }

    @Override
    protected Person readInternal(Class<? extends Person> aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        Properties properties = new Properties();
        properties.load(new InputStreamReader(httpInputMessage.getBody()));

        Person person = new Person();
        person.setId(Integer.valueOf(properties.getProperty("person.id")));
        person.setName(properties.getProperty("person.name"));
        return person;
    }

    @Override
    protected void writeInternal(Person person, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {
        Properties properties = new Properties();
        properties.setProperty("person.id", String.valueOf(person.getId()));
        properties.setProperty("person.name", person.getName());

        properties.store(new OutputStreamWriter(httpOutputMessage.getBody()), "Written by server");
    }
}
