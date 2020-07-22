package com.github.oc12345.repository;

import com.github.oc12345.domain.City;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CityRepository extends CrudRepository<City, Long> {

    Optional<City> findCityByName(String name);

}
