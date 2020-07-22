package com.github.oc12345.service;

import com.github.oc12345.client.UserClient;
import com.github.oc12345.domain.User;
import com.github.oc12345.utility.CoordinateDistanceCalculator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static com.github.oc12345.TestUtil.*;
import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserClient userClient;

    @Mock
    private CoordinateDistanceCalculator coordinateDistanceCalculator;

    @Mock
    private CityService cityService;

    @InjectMocks
    private UserService userService;

    @Test
    public void givenUsers_whenFindingUsersInCity_thenOnlyReturnUsersInOrAroundCity() {
        List<User> usersAroundCity = usersAroundCity();
        when(cityService.findCityByName(LONDON)).thenReturn(Optional.of(givenCity()));
        when(userClient.findUsersInCity(LONDON)).thenReturn(usersAroundCity);
        when(userClient.findAllUsers()).thenReturn(allUsers());
        when(coordinateDistanceCalculator.calculateDistance(51.501652, -0.145896,
                LONDON_LATITUDE, LONDON_LONGITUDE)).thenReturn((double) 2);
        when(coordinateDistanceCalculator.calculateDistance(51.530763, -0.151422,
                LONDON_LATITUDE, LONDON_LONGITUDE)).thenReturn((double) 2);
        when(coordinateDistanceCalculator.calculateDistance(51.466566, -0.451462,
                LONDON_LATITUDE, LONDON_LONGITUDE)).thenReturn((double) 15);
        when(coordinateDistanceCalculator.calculateDistance(53.477337, -2.230385,
                LONDON_LATITUDE, LONDON_LONGITUDE)).thenReturn((double) 162);

        Collection<User> returnedUsers = userService.findUsersInCityWithinDistance(LONDON, 50);

        assertThat(returnedUsers).containsExactlyElementsIn(usersAroundCity);
    }

    @Test
    public void givenUsers_whenNoUsersInCity_thenReturnEmptyCollection() {
        when(cityService.findCityByName(LONDON)).thenReturn(Optional.of(givenCity()));
        when(userClient.findUsersInCity(LONDON)).thenReturn(Collections.emptyList());
        when(userClient.findAllUsers()).thenReturn(usersOutsideCity());
        when(coordinateDistanceCalculator.calculateDistance(53.477337, -2.230385,
                LONDON_LATITUDE, LONDON_LONGITUDE)).thenReturn((double) 162);

        Collection<User> returnedUsers = userService.findUsersInCityWithinDistance(LONDON, 50);

        assertThat(returnedUsers).isEmpty();
    }

    @Test
    public void givenUsers_ifUnknownCityIsProvided_thenReturnEmptyList() {
        when(cityService.findCityByName(MANCHESTER)).thenReturn(Optional.empty());

        Collection<User> returnedUsers = userService.findUsersInCityWithinDistance(MANCHESTER, 50);

        assertThat(returnedUsers).isEmpty();
    }

    private List<User> usersOutsideCity() {
        return Collections.singletonList(new User(4, "Jane", "Doe", "jane.doe@test.local",
                "192.168.0.3", 53.477337, -2.230385));
    }

    private List<User> allUsers() {
        ArrayList<User> users = new ArrayList<>();
        users.addAll(usersAroundCity());
        users.addAll(usersOutsideCity());
        return users;
    }

}