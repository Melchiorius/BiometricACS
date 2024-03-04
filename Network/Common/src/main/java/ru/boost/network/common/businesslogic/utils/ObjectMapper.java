package ru.boost.network.common.businesslogic.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import ru.boost.network.common.businesslogic.entity.Person;

import java.io.IOException;
import java.util.List;

public class ObjectMapper {

    public static String Persons2Json(List<Person> persons) {
        try {
            // Преобразуем список persons в JSON
            com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
            String jsonPersons = objectMapper.writeValueAsString(persons);
            return jsonPersons;
        }
        catch (JsonProcessingException e){
            System.out.println(e);
        }
        return null;
    }

    public static List<Person> Json2Persons(String json) {
        try {
            // Преобразуем JSON в список persons
            com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
            List<Person> persons = objectMapper.readValue(json, new TypeReference<List<Person>>() {});
            return persons;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] Persons2Bytes(List<Person> persons) {
        try {
            com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
            byte[] bytes = objectMapper.writeValueAsBytes(persons);
            return bytes;
        }
        catch (JsonProcessingException e){
            System.out.println(e);
        }
        return null;
    }

    public static List<Person> Bytes2Persons(byte[] bytes) {
        try {
            com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
            List<Person> persons = objectMapper.readValue(bytes, new TypeReference<List<Person>>() {});
            return persons;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
