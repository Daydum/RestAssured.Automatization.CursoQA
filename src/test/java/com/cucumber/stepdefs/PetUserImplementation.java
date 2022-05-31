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
import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PetUserImplementation {
    private Response postUser = null;
    private Response putUser = null;
    private Response userByUsername = null;
    private Response getLogin = null;




    @Before
    public void before() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2/user/";
    }

    @Given("the following post request for create a new user")
    public Response thePostRequestForCreateANewUser() {
        File jsonUserFile = new File("src/test/java/data/petUser.json");
        postUser = given().contentType(ContentType.JSON).body(jsonUserFile).post();
        return postUser;
    }

    @And("the response is {int} for create a new user")
    public void theResponseIsForCreateANewUser(int status) {
        assertEquals("The response is " + status, status, postUser.statusCode());
    }

    @Then("the body response for create a new user contains {string} and {string}")
    public void theBodyResponseForCreateANewUserContainsAnd(String code, String message) {
        assertEquals("The value of the code field is not what is expected", "200", code);
        assertEquals("The value of the message field is not what is expected", "1408", message);
    }

    @Given("obtain user by username")
    public ValidatableResponse obtainUserByUsername() {
        userByUsername = given().get("day");
            return userByUsername.then().log().body();
    }

    @And("the response is {int} for user by username")
    public void theResponseIsForUserByUsername(int status) {
        assertEquals("The response is " + status, status, userByUsername.statusCode());
    }

    @Given("the user provides the username {string} and the password {string}")
    public Response theUserProvidesTheUsernameAndThePassword(String username, String password) {
        getLogin  = given().get("login?username=" + username + "&password=" + password);
        return getLogin;
    }

    @And("the response is {int} for user Login")
    public void theResponseIsForUserLogin(int status) {
        assertEquals("The response is " + status, status, getLogin.statusCode());
    }

    @Given("the following put request that update the created user")
    public void theFollowingPutRequestThatUpdateTheCreatedUser() {
        thePostRequestForCreateANewUser();
        HashMap<String, String> bodyRequestMapPut = new HashMap<>();
        bodyRequestMapPut.put("username", "ladyDi");
        putUser = given().contentType(ContentType.JSON).body(bodyRequestMapPut).put("day");
    }

    @And("the response is {int} for the updated user")
    public void theResponseIsForTheUpdatedUser(int status) {
        assertEquals("The response is " + status, status, putUser.statusCode());
    }

    @Then("the body response for update a new user contains {string} and {string}")
    public void theBodyResponseForUpdateANewUserContainsAnd(String code, String message) {
        assertEquals("The value of the code field is not what is expected", "200", code);
        assertEquals("The value of the message field is not what is expected", "1408", message);
    }


}
