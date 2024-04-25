package com.app.ConStructCompany.Service;

import com.app.ConStructCompany.Entity.Product;
import com.app.ConStructCompany.Repository.ProductRepository;
import com.app.ConStructCompany.Request.ProductAddRequest;
import com.app.ConStructCompany.Request.ProductEditRequest;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class ProductService {
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product addProduct(ProductAddRequest productAddRequest){
        Product product = new Product();
        product.setProName(productAddRequest.getProName());
        product.setUnit(productAddRequest.getUnit());
        product.setPrice(productAddRequest.getPrice());
        product.setInventory(productAddRequest.getInventory());
        product.setDescription(productAddRequest.getDescription());
        product.setImportPrice(productAddRequest.getImportPrice());
        product.setCreateAt(new Date());
        product.setDeleted(false);
        return productRepository.save(product);
    }

    public Product editProduct(ProductEditRequest productEditRequest){
        Optional<Product> productOptional = productRepository.findById(productEditRequest.getId());
        if(productOptional.isPresent()){
            Product product = productOptional.get();
            product.setProName(productEditRequest.getProName());
            product.setUnit(productEditRequest.getUnit());
            product.setPrice(productEditRequest.getPrice());
            product.setImportPrice(productEditRequest.getImportPrice());
            product.setDescription(productEditRequest.getDescription());
            product.setUpdateAt(new Date());
            product.setInventory(productEditRequest.getInventory());
            return productRepository.save(product);
        }else {
            return null;
        }
    }

    public void deleteProductById(Long id) {
        // Kiểm tra xem sản phẩm có tồn tại trong cơ sở dữ liệu không
        if (productRepository.existsById(id)) {
            // Nếu sản phẩm tồn tại, thì xóa sản phẩm
            Product product = productRepository.findById(id).get();
            product.setDeleted(true);
        } else {
            // Nếu sản phẩm không tồn tại
            throw new RuntimeException("Sản phẩm không tồn tại");
        }
    }

    public Page<Product> findByDeletedFalse(Pageable pageable){
        return productRepository.findByDeletedFalse(pageable);
    }

    public Page<Product> findByDeletedFalseAndNameContaining(String name, Pageable pageable){
        return productRepository.findByProNameContainingIgnoreCaseAndDeletedFalse(name, pageable);
    }

    public int countProduct(){
        return productRepository.countByIsDeletedFalse();
    }
}
