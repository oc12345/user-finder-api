package com.github.oc12345.service;

import com.github.oc12345.domain.City;
import com.github.oc12345.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CityService {

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public Optional<City> findCityByName(String cityName) {
        return cityRepository.findCityByName(cityName);
    }

}
