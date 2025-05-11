package Springboot.Uber.App.controllers;


import Springboot.Uber.App.DTO.OnBoardDriverDTO;
import Springboot.Uber.App.DTO.SignUpDTO;
import Springboot.Uber.App.Entities.Enums.Role;
import Springboot.Uber.App.Entities.User;
import Springboot.Uber.App.Repositories.CustomerRepository;
import Springboot.Uber.App.Repositories.UserRepository;
import Springboot.Uber.App.TestContainerConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import org.springframework.test.web.reactive.server.WebTestClient;


import java.util.Set;

@AutoConfigureWebTestClient(timeout = "100000")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestContainerConfiguration.class)
class Authentication_ControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private User user;

    @BeforeEach
    void setUpEach() {
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setRoles(Set.of(Role.CUSTOMER));
    }

    @Test
    void testSignUp_success() {
        SignUpDTO signupDto = new SignUpDTO();
        signupDto.setEmail("test@example.com");
        signupDto.setName("Test name");
        signupDto.setPassword("password");

        webTestClient.post()
                .uri("/auth/signup")
                .bodyValue(signupDto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.data.email").isEqualTo(signupDto.getEmail())
                .jsonPath("$.data.name").isEqualTo(signupDto.getName());
    }

//    @Test
//    @WithUserDetails("admin@gmail.com")
    void testOnboardDriver_success() {

        if (!userRepository.existsById(1L)) {
            userRepository.save(user);
        }

        OnBoardDriverDTO onboardDriverDto = new OnBoardDriverDTO();
        onboardDriverDto.setVehicleId("ABC123");

        webTestClient
                .post()
                .uri("/auth/onBoardNewDriver/1")
                .bodyValue(onboardDriverDto)
                .exchange()
                .expectStatus().isCreated();
    }
}