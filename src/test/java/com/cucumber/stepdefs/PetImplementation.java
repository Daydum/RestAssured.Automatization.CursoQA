package com.cucumber.stepdefs;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import javax.naming.Name;
import java.io.File;
import java.io.Serializable;
import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PetImplementation implements Serializable {

    private Response petById = null;
    private Response petByStatus = null;
    private Response postPet = null;
    private Response putPet = null;
    private Response deletePet = null;



    @Before
    public void before() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2/pet/";
    }

    //Get Pet By Id. Validation
    @Given("obtain pet by {int}")
    public Response getPetById(int id) {
        postRequestThatAddANewPet();
        petById = given().get(" " + id);
        return petById;
    }

    @And("the response is {int} for pet by id")
    public void theResponseIsForPetById(int status) {
        assertEquals("The response is " + status, status, petById.statusCode());
    }

    //Get Pet By Status. Validation
    @Given("obtain pet by status")
    public Response getPetByStatus() {
        petByStatus = given().get("/findByStatus?status=available&status=pending&status=sold");
        return petByStatus;
    }

    @And("the response is {int} for pet by status")
    public void validateGetPetByStatusResponse(int status) {
        assertEquals("The response is " + status, status, petByStatus.statusCode());
    }

    //Create a new pet. Validation
    @Given("post request that add a new pet")
    public Response postRequestThatAddANewPet() {
        File jsonFile = new File("src/test/java/data/Pet.json");
        postPet = given().contentType(ContentType.JSON).body(jsonFile).post();
        return postPet;
    }

    @And("the response is {int} for create new pet")
    public void theResponseIsForCreateNewPet(int status) {
        assertEquals("The response is " + status, status, postPet.statusCode());
    }

    @Then("the body contain the {string}, the {string} and the {string} of the created pet")
    public void theBodyContainTheTheAndTheOfTheCreatedPet(String name, String id, String status) {
        JsonPath jsonPathPet = new JsonPath(postPet.body().asString());
        String jsonPetName = jsonPathPet.getString("name");
        String jsonPetId = jsonPathPet.getString("id");
        String jsonPetStatus = jsonPathPet.getString("status");
        assertEquals("The value of the Name field is not what is expected", name, jsonPetName);
        assertEquals("The value of the ID field is not what is expected", id, jsonPetId);
        assertEquals("The value of the Status field is not what is expected", status, jsonPetStatus);
    }

    @Given("the following put request that update the created pet")
    public void theFollowingPutRequestThatUpdateTheCreatedPet() {
        postRequestThatAddANewPet();
        HashMap<String, String> bodyRequestMapPut = new HashMap<>();
        bodyRequestMapPut.put("name", "Jack Sparrow");
        putPet = given().contentType(ContentType.JSON).body(bodyRequestMapPut).put();
    }

    @And("the response is {int} for the put")
    public void theResponseIsForThePut(int status) {
        assertEquals("The response is " + status, status, putPet.statusCode());
    }

    @Then("the body response contains update {string}")
    public void theBodyResponseContainsUpdate(String updated_name) {
        JsonPath jsonPathPet = new JsonPath(putPet.body().asString());
        String jsonNewPetName = jsonPathPet.getString("name");
        assertEquals("The value of the Updated Name field is not what is expected", updated_name, jsonNewPetName);
    }

    @Given("the following delete request that delete pet")
    public void theFollowingDeleteRequestThatDeletePet() {
        postRequestThatAddANewPet();
        JsonPath jsonPathPet = new JsonPath(postPet.body().asString());
        String jsonIdCreate = jsonPathPet.getString("id");
        deletePet = given().accept(ContentType.JSON).delete(jsonIdCreate);
    }

    @And("the response is {int} for the delete")
    public void theResponseIsForTheDelete(int status) {
        assertEquals("The response is " + status, status, deletePet.statusCode());
    }

    @Then("the body response contains {string} and {string}")
    public void theBodyResponseContainsAnd(String code, String message) {
        assertEquals("The value of the code field is not what is expected", "200", code);
        assertEquals("The value of the message field is not what is expected", "1408", message);
    }
}








