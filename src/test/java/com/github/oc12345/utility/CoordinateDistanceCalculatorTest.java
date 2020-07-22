package com.github.oc12345.utility;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.github.oc12345.TestUtil.LONDON_LATITUDE;
import static com.github.oc12345.TestUtil.LONDON_LONGITUDE;
import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class CoordinateDistanceCalculatorTest {

    private final CoordinateDistanceCalculator coordinateDistanceCalculator = new CoordinateDistanceCalculator();

    @ParameterizedTest
    @MethodSource("coordinateProvider")
    public void givenGpsCoordinates_whenCalculatingTheDistance_thenEnsureResultIsAccurate(double latitude,
                                                                                          double longitude,
                                                                                          int expectedDistance) {
        double calculatedDistance = coordinateDistanceCalculator.calculateDistance(latitude, longitude,
                LONDON_LATITUDE, LONDON_LONGITUDE);

        assertThat(calculatedDistance).isWithin(1).of(expectedDistance);
    }

    private static Stream<Arguments> coordinateProvider() {
        return Stream.of(
                arguments(51.501652, -0.145896, 2),
                arguments(51.530763, -0.151422, 2),
                arguments(51.466566, -0.451462, 15),
                arguments(51.744449, -0.341935, 18),
                arguments(51.746536, -1.219779, 50),
                arguments(51.753848, -1.270100, 52),
                arguments(53.477337, -2.230385, 162)
        );
    }

}