package com.github.oc12345.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.oc12345.TestUtil;
import com.github.oc12345.domain.User;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.truth.Truth.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@SpringBootTest
@ActiveProfiles("test")
public class RestUserClientIntegrationTest {

    @Value("${api.base.uri}")
    private String testApiUri;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RestUserClient restUserClient;

    private MockRestServiceServer mockServer;

    @Test
    public void givenUsersExistInCity_whenRequestMadeToFindUsersInCity_thenReturnListOfUsers() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        List<User> usersAroundLondon = usersAroundCity();

        mockServer.expect(requestTo(testApiUri + "/city/London/users"))
                .andRespond(withSuccess()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(TestUtil.getUsersInCity()));

        List<User> returnedUsers = restUserClient.findUsersInCity("London");

        assertThat(returnedUsers).containsExactlyElementsIn(usersAroundLondon);
    }

    @Test
    public void givenUsers_whenRequestMadeToFindAllUsers_thenReturnAllUsers() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        List<User> allUsers = allUsers();
        mockServer.expect(requestTo(testApiUri + "/users"))
                .andRespond(withSuccess()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(TestUtil.getAllUsers()));

        List<User> returnedUsers = restUserClient.findAllUsers();

        assertThat(returnedUsers).containsExactlyElementsIn(allUsers);
    }


    private static List<User> usersAroundCity() {
        User user1 = new User(1, "John", "Doe", "john.doe@test.local",
                "127.0.0.1", 51.509865, -0.118092);
        User user2 = new User(2, "Jane", "Doe", "jane.doe@test.local",
                "127.0.0.1", 51.509864, -0.118091);

        return Arrays.asList(user1, user2);
    }

    private List<User> allUsers() {
        User user3 = new User(3, "John", "Smith", "john.smith@test.local",
                "127.0.0.1", 53.477337, -2.230385);

        User user4 = new User(4, "Jane", "Snow", "jane.snow@test.local",
                "127.0.0.1", 51.744449, -0.341935);

        List<User> additionalUsers = Arrays.asList(user3, user4);
        return Stream.concat(usersAroundCity().stream(), additionalUsers.stream())
                .collect(Collectors.toList());
    }

}
