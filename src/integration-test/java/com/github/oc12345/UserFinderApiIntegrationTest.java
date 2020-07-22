package com.github.oc12345;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.oc12345.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.client.RestTemplate;

import static com.google.common.truth.Truth.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserFinderApiIntegrationTest {

    @Value("${api.base.uri}")
    private String testApiUri;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void givenUsersInSystem_whenRequestMadeToApiEndpoint_thenOnlyReturnUsersInProximityToCity() throws Exception {
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        mockServer.expect(requestTo(testApiUri + "/city/London/users"))
                .andRespond(withSuccess()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(TestUtil.getUsersInCity()));

        mockServer.expect(requestTo(testApiUri + "/users"))
                .andRespond(withSuccess()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(TestUtil.getAllUsers()));

        MvcResult result = mockMvc.perform(get("/users/vicinity/London"))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        User[] returnedUsers = objectMapper.readValue(result.getResponse().getContentAsString(), User[].class);
        User[] expectedUsers = objectMapper.readValue(TestUtil.getUsersNearCity(), User[].class);

        assertThat(returnedUsers).isEqualTo(expectedUsers);
    }

}
