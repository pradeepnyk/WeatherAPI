package test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import environment.EnvData;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.module.jsv.JsonSchemaValidatorSettings;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import utils.Helper;

public class GetWeatherByLotLong {

	public Response resDdata, response;
	public static JsonSchemaValidatorSettings settings;

	private static final Logger logger = LogManager.getLogger(NGTestListener.class);

	@BeforeSuite
	public void init() {
		RestAssured.baseURI = EnvData.url;
		RestAssured.basePath = EnvData.basePath;

		logger.info("URI is setup successfully before the execution of all tests");
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

	}

	@Parameters({ "lon", "lat", "key" })
	@Test
	public void getDataByLonLat(String lon, String lat, String key) {

		response = given().urlEncodingEnabled(false).contentType(EnvData.contentType).queryParam("lat", lat).and()
				.queryParam("lon", lon).and().queryParam("units", "S").and().queryParam("key", key).when().get();

		resDdata = response.then().statusCode(200).extract().response();

		logger.info("Response object is retrived from the server by the GET request method");

	}

	@Test
	public void validateStatusCodeLL() {
		response.then().assertThat().statusCode(200);
		response.then().assertThat().statusLine("HTTP/1.1 200 OK");

		logger.info("Status code and status line test success");
	}

	@Parameters({ "lon", "lat" })
	@Test
	public void validateResponseBodyLonLat(String lon, String lat) {

		String responseString = resDdata.asString();
		JsonPath jspath = new JsonPath(responseString);

		String lonVal = resDdata.jsonPath().getString("data.lon").toString();
		String latVal = resDdata.jsonPath().getString("data.lat").toString();

		Assert.assertEquals(Helper.formatDataString(lonVal), lon);
		Assert.assertEquals(Helper.formatDataString(latVal), lat);

		logger.info("Required response body is retrived");

	}

	@Test
	public void validateSchemaLonLat() {

		try {

			InputStream schema = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("WeatherData_Lat.json");
			response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(schema));

			logger.info("Schema level testing is successful");
		} catch (Exception e) {
			logger.error("Schema mismatch, please double check the output");
		}
	}

	@Test
	public void validateRespTimeLonLat() {
		logger.info("Response time getting weather by Latitude & longitude is: " + response.getTime() + "ms");
		response.then().time(lessThan(3000l));
	}

	@AfterSuite
	public void tearDown() {
		logger.info("END OF WEATHER API AUTOMATION");
	}

}
