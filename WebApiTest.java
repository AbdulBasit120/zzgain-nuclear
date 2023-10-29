package apiTesting_java;

import static io.restassured.RestAssured.given;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import io.restassured.RestAssured;

public class WebApiTest {

	private static final String BASE_URL_REST_ASSURED = "http://loocalHost3000"; // Replace with your actual API base

	// Helper method to send HTTP GET requests using HttpClient
	private static HttpResponse<String> sendGetRequest(String baseUrl, String endpoint) throws Exception {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(baseUrl + endpoint)).GET().build();

		return client.send(request, HttpResponse.BodyHandlers.ofString());
	}

	@BeforeClass
	public static void setup() {
		// Set the base URI for the API using RestAssured
		RestAssured.baseURI = BASE_URL_REST_ASSURED;
	}

	@Test
	public void verifyApiEndpointAvailability_RestAssured() {
		given().when().get("/api/home").then().statusCode(200);
	}

	@Test
	public void verifyApiEndpointAvailability_HttpClient() throws Exception {
		HttpResponse<String> response = sendGetRequest(BASE_URL_HTTP_CLIENT, "/api/home");
		int statusCode = response.statusCode();
		System.out.println("Verify API Endpoint Availability (HttpClient) - Response Status Code: " + statusCode);
		assert statusCode == 200 : "Expected status code 200, but got " + statusCode;
	}

	@Test
	public void verifyResponseFormat_RestAssured() {
		given().when().get("/sample-endpoint").then().contentType("application/json");
	}

	@Test
	public void verifyResponseFormat_HttpClient() throws Exception {
		HttpResponse<String> response = sendGetRequest(BASE_URL_HTTP_CLIENT, "/sample-endpoint");
		String contentType = response.headers().firstValue("Content-Type").orElse("");
		System.out.println("Verify Response Format (HttpClient) - Content-Type: " + contentType);
		assert contentType.equalsIgnoreCase("application/json") : "Expected JSON response";
	}

	@Test
	public void testUnauthorizedAccess_RestAssured() {
		given().when().get("/protected-endpoint").then().statusCode(401);
	}

	@Test
	public void testUnauthorizedAccess_HttpClient() throws Exception {
		HttpResponse<String> response = sendGetRequest(BASE_URL_HTTP_CLIENT, "/protected-endpoint");
		int statusCode = response.statusCode();
		System.out.println("Test Unauthorized Access (HttpClient) - Response Status Code: " + statusCode);
		assert statusCode == 401 : "Expected status code 401, but got " + statusCode;
	}

	@Test
	public void testPositiveScenario_RestAssured() {
		given().when().get("/positive-scenario-endpoint").then().statusCode(200)
				.body("someKey", equalTo("expectedValue")).body("anotherKey", containsString("partialExpectedValue"));
	}

	@Test
	public void testPositiveScenario_HttpClient() throws Exception {
		HttpResponse<String> response = sendGetRequest(BASE_URL_HTTP_CLIENT, "/positive-scenario-endpoint");
		int statusCode = response.statusCode();
		String responseBody = response.body();
		System.out.println("Test Positive Scenario (HttpClient) - Response Status Code: " + statusCode);
		System.out.println("Response Body: " + responseBody);
		assert statusCode == 200 : "Expected status code 200, but got " + statusCode;
		assert responseBody.contains("expectedValue") : "Response body does not contain expected value";
		// Add more assertions based on the expected response body
	}

	@Test
	public void testAnotherScenario_RestAssured() {
		given().when().get("/another-scenario-endpoint").then().statusCode(200).body("someKey",
				equalTo("anotherExpectedValue"));
	}

	@Test
	public void testAnotherScenario_HttpClient() throws Exception {
		HttpResponse<String> response = sendGetRequest(BASE_URL_HTTP_CLIENT, "/another-scenario-endpoint");
		int statusCode = response.statusCode();
		String responseBody = response.body();
		System.out.println("Test Another Scenario (HttpClient) - Response Status Code: " + statusCode);
		System.out.println("Response Body: " + responseBody);
		assert statusCode == 200 : "Expected status code 200, but got " + statusCode;
		assert responseBody.contains("anotherExpectedValue") : "Response body does not contain expected value";
	}
}
