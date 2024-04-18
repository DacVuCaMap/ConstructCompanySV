package com.app.ConStructCompany.Request;

import com.app.ConStructCompany.utils.ValidateUtils;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditCustomerRequest extends CustomerRequest{
    @NotNull(message = "ID is required and cannot be null")
    private Long id;

    @NotNull(message = "Address is required and cannot be null")
    private String address;

    @NotNull(message = "Company name is required and cannot be null")
    private String companyName;

    @NotNull(message = "Tax code is required and cannot be null")
    private String taxCode;


    @NotNull(message = "Position is required and cannot be null")
    private String positionCustomer;

    @NotNull(message = "Representative customer is required and cannot be null")
    private String representativeCustomer;

    @Pattern(regexp = "^0[0-9]{8,11}$", message = "Invalid phone number") @NotNull(message = "Phone number is null")
    private String phoneNumber;
    @Email(message = "Invalid Email")
    private String email;
    @Override
    public boolean isValidRequest() {
        return !address.isEmpty() && !companyName.isEmpty() && !taxCode.isEmpty() && !positionCustomer.isEmpty() && !representativeCustomer.isEmpty() && !phoneNumber.isEmpty();
    }

    @Override
    public boolean isValidTaxCode() {
        return ValidateUtils.isValidStringOfNumber(taxCode);
    }
}
