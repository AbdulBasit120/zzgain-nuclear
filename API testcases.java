import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;

public class APITestMenu {

    public static void main(String[] args) {
        RestAssured.baseURI = "https://api.testsite.org";

        String createUserPayload = "{\"username\":\"newUser\",\"email\":\"newUser@example.com\",\"password\":\"password123\"}";
        Response createUserResponse = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(createUserPayload)
                .post("/api/users");

        Assert.assertEquals("User is created successfully", 201, createUserResponse.getStatusCode());
        int userId = 1;
        Response getUserResponse = RestAssured.get("/api/users/" + userId);

        Assert.assertEquals("User details are successfully retrieved", 200, getUserResponse.getStatusCode());

        String updateUserPayload = "{\"email\":\"updatedEmail@example.com\"}";
        Response updateUserResponse = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(updateUserPayload)
                .put("/api/users/" + userId);

        Assert.assertEquals("User information is successfully updated", 200, updateUserResponse.getStatusCode());

        Response deleteUserResponse = RestAssured.delete("/api/users/" + userId);

        Assert.assertEquals("User is deleted successfully", 204, deleteUserResponse.getStatusCode());

      
        String invalidUserPayload = "{\"username\":\"\",\"email\":\"invalidEmail\",\"password\":\"asadsabir227\"}";
        Response createInvalidUserResponse = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(invalidUserPayload)
                .post("/api/users");

        Assert.assertEquals("Error is returned for invalid user data", 400, createInvalidUserResponse.getStatusCode());
    }
}
