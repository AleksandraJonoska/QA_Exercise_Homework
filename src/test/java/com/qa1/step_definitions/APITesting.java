package com.qa1.step_definitions;

import com.qa1.utilities.Environment;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapper;
import io.restassured.mapper.ObjectMapperDeserializationContext;
import io.restassured.mapper.ObjectMapperSerializationContext;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Before;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;


import java.io.File;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class APITesting {

    static String id; // time based universal unique identifier


     @DisplayName("POST Template without ID") //Successfully created with status code 200.
    // Content type( media type ) is not application json( Not sure if this is a bug)
    @Test
    @Order(1)
    public void test10(){

        File jsonData = new File("src/test/resources/test-data/POSTtemplate.json");

          id = given()
                .contentType(ContentType.JSON)
                .body(jsonData)
                .when().post(Environment.BASE_URL + "/templates").prettyPeek()
                .then().statusCode(200)
                //.contentType(ContentType.JSON)
                .extract().response().asString();

    }

    // @DisplayName("PUT Template with ID") Successfully created with id and got status code 200
    @Test
    @Order(2)
    public void test11(){



        File jsonData = new File("src/test/resources/test-data/PUTtemplate.json");


        String  result = given()
                .contentType(ContentType.JSON)
                .pathParam("id", id)
                .body(jsonData)
                .when().put(Environment.BASE_URL + "/templates/:{id}").prettyPeek()
                .then().statusCode(200)
                //.contentType(ContentType.JSON)
                .extract().response().asString();


       Assert.assertTrue(result.endsWith(id));


    }


    // @DisplayName("GET Template by ID") 200 status code
    @Test
    @Order(3)
    public void test12(){
        System.out.println("id = " + id);


        ValidatableResponse response = given()
                .accept(ContentType.JSON)
                .and()
                .pathParam("id", id)
                .when().get(Environment.BASE_URL + "/templates/:{id}").prettyPeek()
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
        .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("test-data/SchemaValidaton.json"));



    }


    //@DisplayName("DELETE Template")
    @Test
    @Order(4)
    public void test13(){

        //we can delete one id only one time, so it will give 204/200 only for the first execution
        // it is a bug it is giving 500 internal server error and response body is "Unexpected end of JSON input"


        given()
                .pathParam("id",id)
                .when().delete(Environment.BASE_URL+"/templates/:{id}")
                .then().statusCode(204); // it is giving server error, and it should delete or give some kind of authorization authentication issues

        //after deleted when we send get request to id that we deleted, it needs to give 404  not found
        given()
                .pathParam("id",id)
                .when().get(Environment.BASE_URL+"/templates/:{id}")
                .then().statusCode(404);

    }


    // @DisplayName("GET Template with non - existing ID") 404 not found status code
    @Test
    @Order(5)
    public void test14(){

        given()
                .accept(ContentType.JSON)
                .and()
                .pathParam("id", id)
                .when().get(Environment.BASE_URL+"/templates/:{id}")
                .then()
                .statusCode(404);
    }


    //@DisplayName("POST Process a template") 200 Successful
    @Test
    @Order(6)
    public void test15(){

        Map<String,Object> requestBodyMap = new LinkedHashMap<>();
        requestBodyMap.put("name","Kristina");
        requestBodyMap.put("age",21);

        given()
                .contentType(ContentType.JSON)
                .pathParam("id",id)
                .body(requestBodyMap)
                .when().post(Environment.BASE_URL+"/templates/:{id}/process").prettyPeek()
                .then().statusCode(200);
                //.contentType(ContentType.JSON);

    }

    //@DisplayName("POST Process a template") 404 Not Found wrong id
    @Test
    @Order(7)
    public void test16(){

        Map<String,Object> requestBodyMap = new LinkedHashMap<>();
        requestBodyMap.put("name","Kristina");
        requestBodyMap.put("age",21);

        given()
                .contentType(ContentType.JSON)
                .pathParam("id","959fa787-fe22-4610-b9ab")
                .body(requestBodyMap)
                .when().post(Environment.BASE_URL+"/templates/:{id}/process").prettyPeek()
                .then().statusCode(404);
        //.contentType(ContentType.JSON);

    }


    //@DisplayName("POST Process a template") Missing field 400 Bad Request, content type text
    @Test
    @Order(8)
    public void test17(){

        Map<String,Object> requestBodyMap = new LinkedHashMap<>();
        requestBodyMap.put("name","Kristina");
        //requestBodyMap.put("age",21);

        given()
                .contentType(ContentType.JSON)
                .pathParam("id",id)
                .body(requestBodyMap)
                .when().post(Environment.BASE_URL+"/templates/:{id}/process").prettyPeek()
                .then().statusCode(400);
        //.contentType(ContentType.JSON);

    }

    //@DisplayName(" GET Process a template") 200
    @Test
    @Order(9)
    public void test18(){

        given()
                .contentType(ContentType.JSON)
                .pathParam("id", id)
                .queryParams("name", "Kristina")
                .queryParams("age", 21)
                .when().get(Environment.BASE_URL+"/templates/:{id}/process").prettyPeek()
                .then().statusCode(200);
        //.contentType(ContentType.JSON); It is text/html



    }


    //@DisplayName("GET Process a template") 400 Bad request, Missing field
    @Test
    @Order(91)
    public void test19(){

        given()
                .contentType(ContentType.JSON)
                .pathParam("id", id)
                .queryParams("name", "Kristina")
                .when().get(Environment.BASE_URL+"/templates/:{id}/process").prettyPeek()
                .then().statusCode(400);
        //.contentType(ContentType.JSON);


    }


    //@DisplayName("GET Process a template") 404 Not Found, Wrong ID
    @Test
    @Order(92)
    public void test20(){

        given()
                .contentType(ContentType.JSON)
                .pathParam("id", "959fa787-fe22-4610-b9ab")
                .queryParams("name", "Kristina")
                .queryParams("age", 21)
                .when().get(Environment.BASE_URL + "/templates/:{id}/process").prettyPeek()
                .then().statusCode(404);
        //.contentType(ContentType.JSON);

    }




}
