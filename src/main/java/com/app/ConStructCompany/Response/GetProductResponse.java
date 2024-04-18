package com.app.ConStructCompany.Response;

import com.app.ConStructCompany.Entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetProductResponse {
    private Page<Product> productPage;
}
