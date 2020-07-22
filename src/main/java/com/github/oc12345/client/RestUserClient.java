package com.github.oc12345.client;

import com.github.oc12345.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class RestUserClient implements UserClient {

    private static final String USERS_PATH = "/users";
    private static final String USERS_IN_CITY_PATH = "/city/{city}/users";

    private final RestTemplate restTemplate;
    private final String apiBaseUri;

    public RestUserClient(RestTemplate restTemplate, @Value("${api.base.uri}") String apiBaseUri) {
        this.restTemplate = restTemplate;
        this.apiBaseUri = apiBaseUri;
    }

    @Override
    public List<User> findUsersInCity(String city) {
        String getUsersInCityUri = UriComponentsBuilder
                .fromUriString(apiBaseUri)
                .path(USERS_IN_CITY_PATH)
                .buildAndExpand(city)
                .toString();
        User[] users = restTemplate.getForEntity(getUsersInCityUri, User[].class).getBody();

        if (users != null) {
            return Arrays.asList(users);
        }
        return Collections.emptyList();
    }

    @Override
    public List<User> findAllUsers() {
        String getUsersUri = UriComponentsBuilder
                .fromUriString(apiBaseUri)
                .path(USERS_PATH)
                .build()
                .toString();
        User[] users = restTemplate.getForEntity(getUsersUri, User[].class).getBody();

        if (users != null) {
            return Arrays.asList(users);
        }
        return Collections.emptyList();
    }

}
