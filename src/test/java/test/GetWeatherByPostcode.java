package test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import environment.EnvData;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.module.jsv.JsonSchemaValidatorSettings;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import utils.Helper;

public class GetWeatherByPostcode {

	public Response resDdata, response;
	public static JsonSchemaValidatorSettings settings;

	private static final Logger logger = LogManager.getLogger(NGTestListener.class);

	@Parameters({ "postcode", "key", "country" })
	@Test
	public void getDataByPostcode(String postcode, String key, String country) {

		response = given().urlEncodingEnabled(false).contentType(EnvData.contentType)
				.queryParam("postal_code", postcode).and().queryParam("key", key).and().queryParam("country", country)
				.when().get();

		resDdata = response.then().statusCode(200).extract().response();

		logger.info("Response object is retrived from the server by the GET request method of getDataByPostcode");

	}

	@Test(dependsOnMethods = { "getDataByPostcode" })
	public void validateStatusCodePC() {
		response.then().assertThat().statusCode(200);
		response.then().assertThat().statusLine("HTTP/1.1 200 OK");

		logger.info("Status code and status line test success - validateStatusCodePC");
	}

	@Parameters({ "country" })
	@Test(dependsOnMethods = { "getDataByPostcode" })
	public void validateResponseBody(String country) {

		String responseString = resDdata.asString();
		JsonPath jspath = new JsonPath(responseString);

		String countryValue = resDdata.jsonPath().getString("data.country_code").toString();

		Assert.assertEquals(Helper.formatDataString(countryValue), country);

		logger.info("Required response body is retrived in validateStatusCodePC");
	}

	@Test(dependsOnMethods = { "getDataByPostcode" })
	public void validateSchemaByPC() {

		try {
			InputStream schema = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("WeatherData_Lat.json");
			response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(schema));
			logger.info("Schema level testing is successful");
		} catch (Exception e) {
			logger.error("Schema mismatch, please double check the output");
		}
	}

	@Test(dependsOnMethods = { "getDataByPostcode" })
	public void validateRespTimePC() {
		logger.info("Response time getting weather by Postcode is: " + response.getTime() + "ms");
		response.then().time(lessThan(3000l));
	}

}
