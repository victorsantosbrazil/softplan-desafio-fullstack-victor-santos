package com.victorsantos.processmanagementapi.users.usercases.admin.updateuser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.victorsantos.processmanagementapi.exceptions.NotFoundException;
import com.victorsantos.processmanagementapi.exceptions.ValidationException;
import com.victorsantos.processmanagementapi.users.data.SaveUserDataRequest;
import com.victorsantos.processmanagementapi.users.data.UserDataGateway;
import com.victorsantos.processmanagementapi.users.data.UserDataResponse;
import com.victorsantos.processmanagementapi.users.entities.CommonUser;
import com.victorsantos.processmanagementapi.users.entities.User;
import com.victorsantos.processmanagementapi.users.entities.UserFactory;
import com.victorsantos.processmanagementapi.users.entities.validation.UserValidator;
import com.victorsantos.processmanagementapi.utils.validation.ConstraintViolation;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UpdateUserUserCaseImpl.class)
class UpdateUserUserCaseTest {
    @Autowired
    private UpdateUserUserCase userCase;

    @MockBean
    private UserDataGateway userDataGateway;

    @MockBean
    private UserFactory userFactory;

    @MockBean
    private UserValidator userValidator;

    @Test
    void shouldUpdateUser() {
        String id = "fl3eifafda";
        String encodedPassword = "fadfaçljnvbaçvdaçfkda";

        UpdateUserUserCaseRequest request = UpdateUserUserCaseRequest.builder()
                .id(id)
                .name("Jonh Snow")
                .email("jonh.snow@gmail.com")
                .role("ADMIN")
                .build();

        UserDataResponse mockCurrentUserData = UserDataResponse.builder()
                .id(id)
                .name("João das Neves")
                .email("joao@test.com")
                .password(encodedPassword)
                .role("PROCESS_SCREENER")
                .build();

        SaveUserDataRequest expectedSavedData = SaveUserDataRequest.builder()
                .id(request.getId())
                .name(request.getName())
                .email(request.getEmail())
                .password(encodedPassword)
                .role(request.getRole())
                .build();

        UserDataResponse mockDataGatewayResponse = UserDataResponse.builder()
                .id(id)
                .name(request.getName())
                .email(request.getEmail())
                .password(encodedPassword)
                .role(request.getRole())
                .build();

        UpdateUserUserCaseResponse expectedResponse = UpdateUserUserCaseResponse.builder()
                .id(id)
                .name(request.getName())
                .email(request.getEmail())
                .role(request.getRole())
                .build();

        when(userDataGateway.findById(id)).thenReturn(Optional.of(mockCurrentUserData));

        ArgumentCaptor<SaveUserDataRequest> dataGatewayRequestCaptor = ArgumentCaptor
                .forClass(SaveUserDataRequest.class);
        when(userDataGateway.save(dataGatewayRequestCaptor.capture())).thenReturn(mockDataGatewayResponse);

        UpdateUserUserCaseResponse response = userCase.run(request);

        assertEquals(expectedSavedData, dataGatewayRequestCaptor.getValue());
        assertEquals(expectedResponse, response);
    }

    @Test
    void shouldThrowValidationExceptionWhenDataIsInvalid() {
        String id = "fl3eifafda";
        String encodedPassword = "fadfaçljnvbaçvdaçfkda";

        UpdateUserUserCaseRequest request = UpdateUserUserCaseRequest.builder()
                .id(id)
                .name("")
                .email("jonh.snow@gmail.com")
                .role("ADMIN")
                .build();

        UserDataResponse mockCurrentUserData = UserDataResponse.builder()
                .id(id)
                .name("João das Neves")
                .email("joao@test.com")
                .password(encodedPassword)
                .role("PROCESS_SCREENER")
                .build();

        User user = CommonUser.builder()
                .name(request.getName())
                .email(request.getEmail())
                .role(request.getRole())
                .build();

        when(userDataGateway.findById(id)).thenReturn(Optional.of(mockCurrentUserData));
        when(userFactory.create(request.getId(), request.getName(), request.getEmail(), null,
                request.getRole()))
                .thenReturn(user);

        List<ConstraintViolation> violations = new ArrayList<>();
        violations.add(new ConstraintViolation("name", "Name is empty"));

        when(userValidator.validate(user, Arrays.asList("password"))).thenReturn(violations);

        assertThrows(ValidationException.class, () -> userCase.run(request));
    }

    @Test
    void shouldThrowNotFoundExceptionWhenUserIsNotFound() {
        String id = "fçkdafkdçafdafd";

        UpdateUserUserCaseRequest request = UpdateUserUserCaseRequest.builder()
                .id(id)
                .name("Jonh")
                .email("jonh.snow@gmail.com")
                .role("ADMIN")
                .build();

        when(userDataGateway.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userCase.run(request));
    }
}
