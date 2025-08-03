package com.nutrifit.foodlog;

import com.nutrifit.foodlog.adapter.grpc.client.UserAccountGrpcService;
import com.nutrifit.foodlog.exception.ResourceNotFoundException;
import com.nutrifit.useraccount.v1.GrpcUserAccountResponse;
import io.grpc.StatusRuntimeException;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.transaction.Transactional;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

import static org.mockito.Mockito.when;


@Import({TestcontainersConfiguration.class, GrpcTestConfig.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@Sql(scripts = "/testdata/create_table_and_insert_food_log_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class FoodLogServiceApplicationTests {

	@LocalServerPort
	private Integer port;

	@Autowired
	private UserAccountGrpcService userAccountGrpcService;

	@BeforeEach
	void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
		Mockito.reset(userAccountGrpcService);
	}

	@Test
	void shouldCreateFoodLogForValidUser() {

		UUID userId = UUID.randomUUID();
		LocalDate date = LocalDate.now();

		when(userAccountGrpcService.getUserAccount(userId))
				.thenReturn(GrpcUserAccountResponse.newBuilder()
						.setUserId(userId.toString())
						.setUsername("testuser")
						.setEmail("test@user.com")
						.setFirstName("Test")
						.setLastName("User")
						.build());

		var request = Map.of(
				"userId", userId,
				"date", date
		);

		RestAssured.given()
				.contentType(ContentType.JSON)
				.body(request)
			.when()
				.post("/api/food-logs")
			.then()
				.statusCode(HttpStatus.CREATED.value())
				.body("id", Matchers.notNullValue())
				.body("userId", Matchers.equalTo(userId.toString()))
				.body("date", Matchers.equalTo(date.toString()));

	}

	@Test
	void shouldNotCreateFoodLogForInvalidUser() {

		UUID userId = UUID.randomUUID();
		LocalDate date = LocalDate.now();

		when(userAccountGrpcService.getUserAccount(userId))
				.thenThrow(new ResourceNotFoundException("User not found"));

		var request = Map.of(
				"userId", userId,
				"date", date
		);

		RestAssured.given()
				.contentType(ContentType.JSON)
				.body(request)
			.when()
				.post("/api/food-logs")
			.then()
				.statusCode(HttpStatus.NOT_FOUND.value())
				.body("message", Matchers.equalTo("Resource not found"));
	}

	@Test
	void shouldReturnBadRequestForInvalidDate() {

		UUID userId = UUID.randomUUID();
		String invalidDate = "invalid-date";

		var request = Map.of(
				"userId", userId,
				"date", invalidDate
		);

		RestAssured.given()
				.contentType(ContentType.JSON)
				.body(request)
			.when()
				.post("/api/food-logs")
			.then()
				.statusCode(HttpStatus.BAD_REQUEST.value())
				.body("message", Matchers.containsString("Invalid format"));
	}

}
