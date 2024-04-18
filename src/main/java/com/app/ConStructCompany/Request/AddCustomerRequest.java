package com.app.ConStructCompany.Request;

import com.app.ConStructCompany.utils.ValidateUtils;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class AddCustomerRequest extends CustomerRequest{
    @NotNull(message = "Address cannot be null")
    private String address;

    @NotNull(message = "Company name cannot be null")
    private String companyName;

    @NotNull(message = "Tax code cannot be null")
    private String taxCode;

    @NotNull(message = "Position cannot be null")
    private String positionCustomer;

    @NotNull(message = "Representative customer cannot be null")
    private String representativeCustomer;

    @Pattern(regexp = "^0[0-9]{8,11}$", message = "Invalid phone number")
    private String phoneNumber;
    @Email(message = "not email")
    private String email;
    @Override
    public boolean isValidRequest() {
        return !address.isEmpty() && !companyName.isEmpty() && !taxCode.isEmpty() && !phoneNumber.isEmpty() && !positionCustomer.isEmpty() && !representativeCustomer.isEmpty();
    }

    @Override
    public boolean isValidTaxCode() {
        return ValidateUtils.isValidStringOfNumber(taxCode);
    }
}
