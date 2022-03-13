package com.internship.AuctionApp.Products;

import com.internship.AuctionApp.Models.Image;
import com.internship.AuctionApp.Models.Product;
import com.internship.AuctionApp.Repositories.ImageRepository;
import com.internship.AuctionApp.Repositories.ProductRepository;
import com.internship.AuctionApp.services.ImageService;
import com.internship.AuctionApp.services.ProductService;
import com.internship.AuctionApp.services.ProductServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1")
@CrossOrigin(origins = {"http://localhost:3000"})
public class ProductsController {

    @Autowired
    private final ProductService productService;

    @Autowired
    private final ImageService imageService;

    @GetMapping(path = "/get/products")
    public ResponseEntity<?> getProducts() {
        final List<Product> productsList = productService.findAllProducts();
        return ResponseEntity.ok().body(productsList);
    }

    @GetMapping(path = "/get/product/{id}")
    public ResponseEntity<?> getProduct(@PathVariable String id) throws Exception {
        final Long product_id = Long.valueOf(id);
        Product product = null;
        try {
            product = productService.getProduct(product_id);
        } catch (Exception err) {
            return ResponseEntity.badRequest().build();
        }
        final List<Image> imagesList = imageService.findAllByProductId(product);
        ProductResponse productResponse = new ProductResponse(product, imagesList);
        return ResponseEntity.ok().body(productResponse);
    }

}
