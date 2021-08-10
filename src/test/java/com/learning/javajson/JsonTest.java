package com.learning.javajson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.learning.javajson.pojo.SimpleTestCaseJsonPojo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsonTest {

    String jsonString = "{\"name\":\"Naveed\"}";

    @Test
    void parseJson() throws JsonProcessingException {

        JsonNode node = Json.parseJson(jsonString);
        assertEquals(node.get("name").asText(), "Naveed");

    }

    @Test
    void fromJson() throws JsonProcessingException {
        JsonNode node = Json.parseJson(jsonString);
        SimpleTestCaseJsonPojo pojo = Json.fromJson(node, SimpleTestCaseJsonPojo.class);

        assertEquals(pojo.name, "Naveed");

    }



}