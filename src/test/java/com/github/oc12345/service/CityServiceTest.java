package com.github.oc12345.service;

import com.github.oc12345.TestUtil;
import com.github.oc12345.domain.City;
import com.github.oc12345.repository.CityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.github.oc12345.TestUtil.LONDON;
import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CityServiceTest {

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private CityService cityService;

    @Test
    public void givenCityExists_whenFindCityByNameIsCalled_thenCityShouldBeReturned() {
        Optional<City> expectedCity = Optional.of(givenCity());
        when(cityRepository.findCityByName(LONDON)).thenReturn(expectedCity);

        Optional<City> returnedCity = cityService.findCityByName(LONDON);

        assertThat(returnedCity).isEqualTo(expectedCity);
    }

    @Test
    public void givenCityDoesNotExist_whenFindCityByNameIsCalled_thenNoCityShouldBeReturned() {
        when(cityRepository.findCityByName(LONDON)).thenReturn(Optional.empty());

        Optional<City> returnedCity = cityService.findCityByName(LONDON);

        assertThat(returnedCity).isEqualTo(Optional.empty());
    }

    public static City givenCity() {
        City city = new City();
        city.setName(LONDON);
        city.setLatitude(TestUtil.LONDON_LATITUDE);
        city.setLongitude(TestUtil.LONDON_LONGITUDE);
        return city;
    }

}