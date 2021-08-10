package com.learning.javajson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
//        String jsonString = "{\"name\":\"Naveed\"}";
//        JsonNode node = Json.parseJson(jsonString);
//        System.out.println(node.get("name").asText());
        ArrayList<Projects> projects = new ArrayList<>();
        projects.add(new Projects("dfj5-2d56-8715-7A59", "A new project", true));
        projects.add(new Projects("dfj6-2d56-8715-7A59", "A Test project", false));


        ObjectMapper objectMapper = new ObjectMapper();
        Employees employees = new Employees("Naveed",
                "naveed.sultan@loblaw.ca",
                true,
                projects);

                objectMapper.writer().writeValue(new FileWriter("testFile.json"), employees);
    }

}
