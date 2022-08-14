package com.martin.chatserver.registration;

import com.martin.chatserver.appuser.AppUser;
import com.martin.chatserver.appuser.AppUserRole;
import com.martin.chatserver.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final EmailValidator emailValidator;
    private final String NOT_VALID_EMAIL = "Email not valid. %s";

    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if (!isValidEmail) {
            throw new IllegalStateException(String.format(NOT_VALID_EMAIL, request.getEmail()));
        }
        return appUserService.signUpUser(new AppUser(
                request.getFirstName(),
                request.getLastName(),
                request.getPassword(),
                request.getEmail(),
                AppUserRole.USER,
                false,
                true
        ));
    }
}
