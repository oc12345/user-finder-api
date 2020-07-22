package com.github.oc12345.controller;

import com.github.oc12345.domain.User;
import com.github.oc12345.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;

import static com.github.oc12345.TestUtil.LONDON;
import static com.github.oc12345.TestUtil.usersAroundCity;
import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    public void givenControllerMethodIsInvoked_whenUsersExistInCity_thenCollectionOfUsersShouldBeReturned() {
        List<User> usersAroundCity = usersAroundCity();
        when(userService.findUsersInCityWithinDistance(LONDON, 50)).thenReturn(usersAroundCity);

        Collection<User> returnedUsers = userController.getUsersAroundCity(LONDON, 50);

        assertThat(returnedUsers).containsExactlyElementsIn(usersAroundCity);
    }

}