package com.cucumber.stepdefs;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import java.io.File;
import java.io.Serializable;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.assertEquals;

public class PetStoreImplementation implements Serializable {
    private Response petInventoryByStatus = null;
    private Response postPetOrder = null;
    private Response petOrderById = null;
    private Response deleteOrderById = null;

    @Before
    public void before() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2/store/";
    }

    @Given("obtain pet inventory by status")
    public ValidatableResponse obtainPetInventoryByStatus() {
        petInventoryByStatus = given().get("inventory");
        return petInventoryByStatus.then().log().body();
    }

    @And("the response is {int} for return inventory by status")
    public void theResponseIsForReturnInventoryByStatus(int status) {
        assertEquals("The response is " + status, status, petInventoryByStatus.statusCode());
        }

    @Given("post request that place an order for a pet")
    public Response postRequestThatPlaceAnOrderForAPet() {
        File jsonOrderFile = new File("src/test/java/data/petOrder.json");
        postPetOrder = given().contentType(ContentType.JSON).body(jsonOrderFile).post("/order");
        return postPetOrder;
    }

    @And("the response is {int} for the order placed")
    public void theResponseIsForTheOrderPlaced(int status) {
        assertEquals("The response is " + status, status, postPetOrder.statusCode());
    }

    @Then("the body contain the {string}, {string} and {string}of the placed order")
    public void theBodyContainTheAndOfThePlacedOrder( String petId, String quantity, String status) {
        JsonPath jsonPathPetStore = new JsonPath(postPetOrder.body().asString());
        //String jsonPetOrderId = jsonPathPetStore.getString("id");
        String jsonPetId = jsonPathPetStore.getString("petId");
        String jsonPetQuantity = jsonPathPetStore.getString("quantity");
        String jsonPetStatus = jsonPathPetStore.getString("status");
        //assertEquals("The value of the ID field is not what is expected", id, jsonPetOrderId);
        assertEquals("The value of the PetId field is not what is expected", petId, jsonPetId);
        assertEquals("The value of the Quantity field is not what is expected", quantity, jsonPetQuantity);
        assertEquals("The value of the Status field is not what is expected", status, jsonPetStatus);
    }

    @Given("obtain pet order by {int}")
    public ValidatableResponse obtainPetOrderByID(int id) {
        petOrderById = given().get("order/" + id);
        return petOrderById.then().log().body();
    }

    @And("the response is {int} for pet order by ID")
    public void theResponseIsForPetOrderByID(int status) {
        assertEquals("The response is " + status, status, petOrderById.statusCode());
    }

    @Given("the following delete request that delete pet order by id = {int}")
    public ValidatableResponse theFollowingDeleteRequestThatDeletePetOrder(int id) {
        deleteOrderById = given().get("order/" + id);
        return deleteOrderById.then().log().body();
    }

    @And("the response is {int} for the delete pet order")
    public void theResponseIsForTheDeletePetOrder(int status) {
        assertEquals("The response is " + status, status, deleteOrderById.statusCode());
    }

    @Then("the delete body response contains {string} and {string}")
    public void theDeleteBodyResponseContainsAnd(String code, String message) {
        assertEquals("The value of the code field is not what is expected", "200", code);
        assertEquals("The value of the message field is not what is expected", "2", message);
    }

}



