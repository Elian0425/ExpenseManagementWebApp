package com.expensemanagementapp.Model;

import com.expensemanagementapp.Validation.Custom.FieldMatching;
import com.expensemanagementapp.Validation.Custom.NotDefaultEnum;
import com.expensemanagementapp.Validation.Custom.NotUnderAge;
import com.expensemanagementapp.Validation.Group.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@FieldMatching(
        firstField = "userEmail",
        secondField = "userEmailConfirm",
        groups = {
                RegistrationValidationGroup.class,
                EmailUpdateValidationGroup.class
        }
)
@FieldMatching(
        firstField = "userPassword",
        secondField = "userPasswordConfirm",
        groups = {
                RegistrationValidationGroup.class,
                PasswordResetValidationGroup.class,
        }
)
public class UserModel {
    @NotEmpty(
            message = "Field is required.",
            groups = {
                    RegistrationValidationGroup.class,
                    AuthenticationValidationGroup.class,
                    EmailLookUpValidationGroup.class,
                    ForgotPasswordValidationGroup.class,
                    DebtorAddValidationGroup.class
            }
    )
    @Pattern(
            regexp = "^$|^[A-Za-z]+$",
            message = "Only letters are allowed. Ex: John.",
            groups = {
                    RegistrationValidationGroup.class,
                    AuthenticationValidationGroup.class,
                    EmailLookUpValidationGroup.class,
                    ForgotPasswordValidationGroup.class,
                    DebtorAddValidationGroup.class,
                    DebtorUpdateValidationGroup.class
            }
    )
    private String userFirstName;

    @NotEmpty(
            message = "Field is required.",
            groups = {
                    RegistrationValidationGroup.class,
                    AuthenticationValidationGroup.class,
                    EmailLookUpValidationGroup.class,
                    ForgotPasswordValidationGroup.class,
                    DebtorAddValidationGroup.class
            }
    )
    @Pattern(
            regexp = "^$|^[A-Za-z]+( [A-Za-z]+)*$",
            message = "Only letters and spaces in between are allowed. Ex: Doe Smith.",
            groups = {
                    RegistrationValidationGroup.class,
                    AuthenticationValidationGroup.class,
                    EmailLookUpValidationGroup.class,
                    ForgotPasswordValidationGroup.class,
                    DebtorAddValidationGroup.class,
                    DebtorUpdateValidationGroup.class
            }
    )
    private String userLastName;

    @NotEmpty(
            message = "Field is required.",
            groups = {
                    RegistrationValidationGroup.class,
                    EmailLookUpValidationGroup.class
            }
    )
    @Pattern(regexp = "^(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])-\\d{4}$",
            message = "Invalid date format. Ex: 01-01-2000.",
            groups = {
                    RegistrationValidationGroup.class,
                    EmailLookUpValidationGroup.class,
                    ForgotPasswordValidationGroup.class
            }
    )
    @NotUnderAge(
            groups = {
                    RegistrationValidationGroup.class,
                    EmailLookUpValidationGroup.class,
                    ForgotPasswordValidationGroup.class
            }
    )
    private String userDOB;

    @NotDefaultEnum(
            groups = {
                    RegistrationValidationGroup.class
            }
    )
    private String userGender;

    @NotEmpty(
            message = "Field is required.",
            groups = {
                    RegistrationValidationGroup.class,
                    EmailLookUpValidationGroup.class,
                    DebtorAddValidationGroup.class
            }
    )
    @Pattern(
            regexp = "^$|^[0-9]{10}$",
            message = "Invalid phone number. Please use 10 digits.",
            groups = {
                    RegistrationValidationGroup.class,
                    EmailLookUpValidationGroup.class,
                    DebtorAddValidationGroup.class,
                    DebtorUpdateValidationGroup.class
            }
    )
    private String userPhoneNumber;

    @NotEmpty(
            message = "Field is required.",
            groups = {
                    RegistrationValidationGroup.class,
                    AuthenticationValidationGroup.class,
                    EmailLookUpValidationGroup.class,
                    DebtorAddValidationGroup.class,
                    EmailUpdateValidationGroup.class
            }
    )
    @Pattern(
            regexp = "^$|^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "Invalid email address",
            groups = {
                    RegistrationValidationGroup.class,
                    ForgotPasswordValidationGroup.class,
                    EmailLookUpValidationGroup.class,
                    DebtorAddValidationGroup.class,
                    DebtorUpdateValidationGroup.class,
                    EmailUpdateValidationGroup.class
            }
    )
    private String userEmail;

    @NotEmpty(
            message = "Field is required.",
            groups = {
                    RegistrationValidationGroup.class,
                    EmailUpdateValidationGroup.class
            }
    )
    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "Invalid email address",
            groups = {
                    RegistrationValidationGroup.class,
                    EmailUpdateValidationGroup.class
            }
    )
    private String userEmailConfirm;

    @NotEmpty(
            message = "Field is required.",
            groups = {
                    RegistrationValidationGroup.class,
                    PasswordResetValidationGroup.class,
            }
    )
    @Length(
            min = 8,
            max = 16,
            message = "Password must be between 8 and 16 characters",
            groups = {
                    RegistrationValidationGroup.class,
                    PasswordResetValidationGroup.class
            }
    )
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d])\\S{8,}$",
            message = "Invalid password. Must contain: 1 uppercase and lowercase letter, 1 digit, and 1 special character. Ex: Password1!",
            groups = {
                    RegistrationValidationGroup.class,
                    PasswordResetValidationGroup.class
            }
    )
    private String userPassword;

    @NotEmpty(
            message = "Field is required.",
            groups = {
                    RegistrationValidationGroup.class,
                    PasswordResetValidationGroup.class
            }
    )
    @Length(
            min = 8,
            max = 16,
            message = "Password must be between 8 and 16 characters",
            groups = {
                    RegistrationValidationGroup.class,
                    PasswordResetValidationGroup.class
            }
    )
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d])\\S{8,}$",
            message = "Invalid password. Must contain: 1 uppercase and lowercase letter, 1 digit, and 1 special character. Ex: Password1!",
            groups = {
                    RegistrationValidationGroup.class,
                    PasswordResetValidationGroup.class,
            }
    )
    private String userPasswordConfirm;
}
