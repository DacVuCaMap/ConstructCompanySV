package com.app.ConStructCompany.Controller;

import com.app.ConStructCompany.Entity.Product;
import com.app.ConStructCompany.Repository.ProductRepository;
import com.app.ConStructCompany.Request.ProductAddRequest;
import com.app.ConStructCompany.Request.ProductEditRequest;
import com.app.ConStructCompany.Response.GetProductResponse;
import com.app.ConStructCompany.Service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductRepository productRepository;

//    public ProductController(ProductService productService, ProductRepository productRepository) {
//        this.productService = productService;
//        this.productRepository = productRepository;
//    }

    @PostMapping("/add-product")
    public ResponseEntity<?> addProduct(@RequestBody @Valid ProductAddRequest productAddRequest){
        Product product = productService.addProduct(productAddRequest);
        if (product==null){
            return ResponseEntity.badRequest().body("Đã ton tại");
        }
        return ResponseEntity.ok(product);
    }

    @PutMapping("/edit-product")
    public ResponseEntity<?> editProduct(@RequestBody @Valid ProductEditRequest productEditRequest){
        Product product = productService.editProduct(productEditRequest);
        if(product!=null){
            return ResponseEntity.ok(product);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lỗi edit product");
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProductById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Sản phẩm đã được xóa thành công");
    }

    @GetMapping("/get")
    public ResponseEntity<?> getProducts(
           @RequestParam(value = "size", required = false) Integer size,
           @RequestParam(value = "page", required = false) Integer page,
           @RequestParam(value = "filter", required = false) String filter
        ){
        GetProductResponse getProductResponse = new GetProductResponse();
        if(filter==null || filter.isEmpty()){
            PageRequest pageRequest = PageRequest.of(page,size);
            Page<Product> productPage = productService.findByDeletedFalse(pageRequest);
            getProductResponse.setProductPage(productPage);
            return ResponseEntity.ok(getProductResponse);
        }
        if(filter=="create"){
            PageRequest pageRequest = PageRequest.of(page,size, Sort.by("create_at").descending());
            Page<Product> productPage = productService.findByDeletedFalse(pageRequest);
            getProductResponse.setProductPage(productPage);
            return ResponseEntity.ok(getProductResponse);
        }
        if(filter=="inventory"){
            PageRequest pageRequest = PageRequest.of(page,size, Sort.by("inventory").descending());
            Page<Product> productPage = productService.findByDeletedFalse(pageRequest);
            getProductResponse.setProductPage(productPage);
            return ResponseEntity.ok(getProductResponse);
        }
        if(filter=="price"){
            PageRequest pageRequest = PageRequest.of(page,size, Sort.by("price").descending());
            Page<Product> productPage = productService.findByDeletedFalse(pageRequest);
            getProductResponse.setProductPage(productPage);
            return ResponseEntity.ok(getProductResponse);
        }
        return ResponseEntity.badRequest().body("Invalid filter parameter.");
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchProductByName(
            @RequestParam("name") String name,
            @RequestParam("page") int page,
            @RequestParam("size") int size){
        GetProductResponse getProductResponse = new GetProductResponse();
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Product> productPage = productService.findByDeletedFalseAndNameContaining(name, pageRequest);
        getProductResponse.setProductPage(productPage);
        return ResponseEntity.ok(getProductResponse);
    }
    @GetMapping("/find-cus/{name}")
    public ResponseEntity<?> findCusWithName(@PathVariable String name){
        Optional<Product> productOptional = productRepository.findByProNameIgnoreCaseAndDeletedFalse(name);
        if (productOptional.isEmpty()){
            return ResponseEntity.badRequest().body("khong thay product");
        }
        return ResponseEntity.ok().body(productOptional.get());
    }
}






