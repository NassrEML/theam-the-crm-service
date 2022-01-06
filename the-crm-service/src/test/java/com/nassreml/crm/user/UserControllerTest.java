package com.nassreml.crm.user;

import com.nassreml.crm.user.web.response.ListUsersResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserControllerTest {

    private final UserService userService;
    private final UserController userController;

    public UserControllerTest() {
        userService = mock(UserService.class);
        userController = new UserController(userService);
    }

    @Test
    void whenThereIsUsersShouldReturnTheListAndOK() {
        // Given
        final User user = new User();
        when(userService.getUsers()).thenReturn(List.of(user));

        // When
        ResponseEntity<ListUsersResponse> response = userController.listUsers();

        // Then
        assertThat(response.getBody().getUsers().get(0)).isEqualTo(user);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void whenThereIsNotUsersShouldReturnEmptyListAndOK() {
        // Given
        final List<User> emptyList = List.of();
        when(userService.getUsers()).thenReturn(emptyList);

        // When
        ResponseEntity response = userController.listUsers();

        // Then
        assertThat(((List)response.getBody()).size()).isEqualTo(0);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
