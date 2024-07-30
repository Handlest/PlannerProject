//package com.example.plannerapi;
//
//import com.example.plannerapi.domain.dto.requests.TaskCreateRequest;
//import com.example.plannerapi.domain.dto.requests.UserSignUpRequest;
//import com.example.plannerapi.domain.dto.responces.UserJwtAuthenticationResponse;
//import com.example.plannerapi.domain.entities.TaskEntity;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.github.dockerjava.api.model.AuthResponse;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.testcontainers.containers.GenericContainer;
//import org.testcontainers.containers.PostgreSQLContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Testcontainers
//public class UserAccessTest {
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    @Container
//    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer()
//            .withPassword("inmemory")
//            .withUsername("inmemory");
//
//    private static GenericContainer redis;
//
//    @DynamicPropertySource
//    static void redisProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.data.redis.url", () -> "redis://" + redis.getContainerIpAddress() + ":" + redis.getMappedPort(6379));
//    }
//
//    static {
//        redis = new GenericContainer("redis:6.0.4").withExposedPorts(6379);
//        redis.start();
//    }
//
//    @DynamicPropertySource
//    static void postgresqlProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
//        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
//        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
//    }
//
//
//    @Test
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public void toDoTest() throws Exception {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        objectMapper.findAndRegisterModules();
//
//        UserSignUpRequest userSignUpRequest = new UserSignUpRequest("123456789", "123456@gmail.com", "12345678");
//
//        ResponseEntity<String> response = restTemplate.postForEntity("/api/v1/auth/register", userSignUpRequest, String.class);
//        UserJwtAuthenticationResponse authResponse = objectMapper.readValue(response.getBody(), UserJwtAuthenticationResponse.class);
//
//        String accessToken = authResponse.getAccessToken();
//        System.out.println("Access Token: " + accessToken);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer " + accessToken);
//
//
//        TaskCreateRequest taskCreateRequest = TaskCreateRequest.builder().title("Hello").description("test").priority(1).build();
//
//        HttpEntity<TaskCreateRequest> requestEntity = new HttpEntity<>(taskCreateRequest, headers);
//
//        ResponseEntity<String> response2 = restTemplate.exchange(
//                "/tasks",
//                HttpMethod.POST,
//                requestEntity,
//                String.class
//        );
//
//        TaskEntity taskResponse = objectMapper.readValue(response2.getBody(), TaskEntity.class); // User in Entity and userId in response!
//        System.out.println("Task Response: " + taskResponse);
//    }
//}
