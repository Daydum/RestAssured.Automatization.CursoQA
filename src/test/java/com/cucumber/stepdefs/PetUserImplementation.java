package com.cucumber.stepdefs;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
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
    private Response getLogout = null;
    private Response deleteUser = null;

    @Given("the following post request for create a new user")
    public Response thePostRequestForCreateANewUser() {
        File jsonUserFile = new File("src/test/java/data/petUser.json");
        postUser = given().contentType(ContentType.JSON).body(jsonUserFile).post("/user");
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
        userByUsername = given().get("/user/day");
        return userByUsername.then().log().body();
    }

    @And("the response is {int} for user by username")
    public void theResponseIsForUserByUsername(int status) {
        assertEquals("The response is " + status, status, userByUsername.statusCode());
    }

    @Given("the user provides the username {string} and the password {string}")
    public Response theUserProvidesTheUsernameAndThePassword(String username, String password) {
        getLogin  = given().get("/user/login?username" + username + "&password=" + password);
        return getLogin;
    }

    @And("the response is {int} for user Login")
    public void theResponseIsForUserLogin(int status) {
        assertEquals("The response is " + status, status, getLogin.statusCode());
    }

    @Given("the following request for session logout")
    public Response theFollowingRequestForSessionLogout() {
        getLogout = given().get("/user/logout");
        return getLogout;
    }

    @And("the response is {int} for logout the session")
    public void theResponseIsForLogoutTheSession(int status) {
        assertEquals("The response is " + status, status, getLogout.statusCode());
    }

    @Given("the following put request that update the created user")
    public void theFollowingPutRequestThatUpdateTheCreatedUser() {
        thePostRequestForCreateANewUser();
        HashMap<String, String> bodyRequestMapPut = new HashMap<>();
        bodyRequestMapPut.put("username", "ladyDi");
        putUser = given().contentType(ContentType.JSON).body(bodyRequestMapPut).put("/user/day");
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

    @Given("the following delete request that delete user")
    public Response theFollowingDeleteRequestThatDeleteUser() {
        deleteUser = given().delete("/user/day");
        return deleteUser;
    }

    @And("the response is {int} for the delete user")
    public void theResponseIsForTheDeleteUser(int status) {
        assertEquals("The response is " + status, status, deleteUser.statusCode());
    }

    @Then("the delete user body response contains {string} and {string}")
    public void theDeleteUserBodyResponseContainsAnd(String code, String message) {
        assertEquals("The value of the code field is not what is expected", "200", code);
        assertEquals("The value of the message field is not what is expected", "day", message);
    }
}
