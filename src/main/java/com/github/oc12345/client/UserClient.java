package com.github.oc12345.client;

import com.github.oc12345.domain.User;

import java.util.List;

public interface UserClient {

    List<User> findUsersInCity(String location);

    List<User> findAllUsers();

}
