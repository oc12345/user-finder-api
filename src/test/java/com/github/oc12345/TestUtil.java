package com.github.oc12345;

import com.github.oc12345.domain.City;
import com.github.oc12345.domain.User;

import java.util.Arrays;
import java.util.List;

public class TestUtil {

    public static final String LONDON = "London";
    public static final String MANCHESTER = "Manchester";
    public static final double LONDON_LATITUDE = 51.509865;
    public static final double LONDON_LONGITUDE = -0.118092;

    public static City givenCity() {
        City city = new City();
        city.setName(LONDON);
        city.setLatitude(LONDON_LATITUDE);
        city.setLongitude(LONDON_LONGITUDE);
        return city;
    }

    public static List<User> usersAroundCity() {
        User user1 = new User(1, "John", "Doe", "john.doe@test.local",
                "127.0.0.1", 51.501652, -0.145896);
        User user2 = new User(2, "Joe", "Bloggs", "joe.bloggs@test.local",
                "192.168.0.1", 51.530763, -0.151422);
        User user3 = new User(3, "John", "Smith", "john.smith@test.local",
                "192.168.0.2", 51.466566, -0.451462);

        return Arrays.asList(user1, user2, user3);
    }

}
