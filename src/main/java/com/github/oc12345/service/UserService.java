package com.github.oc12345.service;

import com.github.oc12345.client.UserClient;
import com.github.oc12345.domain.City;
import com.github.oc12345.domain.User;
import com.github.oc12345.utility.CoordinateDistanceCalculator;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService {

    private final CityService cityService;
    private final UserClient userClient;
    private final CoordinateDistanceCalculator coordinateDistanceCalculator;

    public UserService(CityService cityService, UserClient userClient,
                       CoordinateDistanceCalculator coordinateDistanceCalculator) {
        this.cityService = cityService;
        this.userClient = userClient;
        this.coordinateDistanceCalculator = coordinateDistanceCalculator;
    }

    public List<User> findUsersInCityWithinDistance(String cityName, int distance) {
        return cityService.findCityByName(cityName).map(city ->
                Stream.of(findUsersListedInCity(cityName), findUsersInProximityToCity(city, distance))
                        .flatMap(Collection::stream)
                        .distinct()
                        .sorted(Comparator.comparing(User::getId))
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    private List<User> findUsersListedInCity(String city) {
        return userClient.findUsersInCity(city);
    }

    private List<User> findUsersInProximityToCity(City city, int distance) {
        return userClient.findAllUsers()
                .stream()
                .filter(user -> isLocationWithinDistance(user.getLatitude(), user.getLongitude(),
                        city.getLatitude(), city.getLongitude(), distance))
                .collect(Collectors.toList());
    }

    private boolean isLocationWithinDistance(double latitudeFrom, double longitudeFrom,
                                             double latitudeTo, double longitudeTo, int maximumDistance) {
        return coordinateDistanceCalculator.calculateDistance(latitudeFrom, longitudeFrom,
                latitudeTo, longitudeTo) <= maximumDistance;
    }

}
