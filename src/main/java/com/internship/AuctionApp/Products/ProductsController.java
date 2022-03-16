package com.internship.AuctionApp.Products;

import com.internship.AuctionApp.Models.Image;
import com.internship.AuctionApp.Models.Product;
import com.internship.AuctionApp.services.ImageService;
import com.internship.AuctionApp.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1")
@CrossOrigin(origins = {"http://localhost:3000", "https://auction-app-internship-fr.herokuapp.com/"})
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
        String dateString = LocalDate.now().toString();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Date", dateString);

        return ResponseEntity.ok().headers(httpHeaders).body(productResponse);
    }

}
