package api_testCases;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class api_testMenu_21f9433{
    public static void main(String[] args) {
        RestAssured.baseURI = "https://api.testsite.org";

        // Scenario 1: Successfully create a new user in the system
        String createUserPayload = "{\"username\":\"newUser\",\"email\":\"newUser@example.com\",\"password\":\"password123\"}";
        Response createUserResponse = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(createUserPayload)
                .post("/api/users");

        if (createUserResponse.getStatusCode() == 201) {
            System.out.println("User is created successfully");
        } else {
            System.out.println("User creation failed");
        }

        // Scenario 2: Successfully retrieve user details by ID
        int userId = 1;
        Response getUserResponse = RestAssured.get("/api/users/" + userId);

        if (getUserResponse.getStatusCode() == 200) {
            System.out.println("User details are successfully retrieved");
        } else {
            System.out.println("Failed to retrieve user details");
        }

        // Scenario 3: Successfully update user information
        int updateUserPayload = "{\"email\":\"updatedEmail@example.com\"}";
        Response updateUserResponse = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(updateUserPayload)
                .put("/api/users/" + userId);

        if (updateUserResponse.getStatusCode() == 200) {
            System.out.println("User information is successfully updated");
        } else {
            System.out.println("Failed to update user information");
        }

        // Scenario 4: Successfully delete a user from the system
        Response deleteUserResponse = RestAssured.delete("/api/users/" + userId);

        if (deleteUserResponse.getStatusCode() == 204) {
            System.out.println("User is deleted successfully");
        } else {
            System.out.println("Failed to delete the user");
        }

        // Scenario 5: Handle invalid data when creating a user
        String invalidUserPayload = "{\"username\":\"\",\"email\":\"invalidEmail\",\"password\":\"123\"}";
        Response createInvalidUserResponse = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(invalidUserPayload)
                .post("/api/users");

        if (createInvalidUserResponse.getStatusCode() == 400) {
            System.out.println("Error is returned for invalid user data");
        } else {
            System.out.println("Invalid user data was accepted");
        }
    }
}

