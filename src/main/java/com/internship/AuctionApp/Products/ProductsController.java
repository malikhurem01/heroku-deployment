package com.internship.AuctionApp.Products;

import com.internship.AuctionApp.DTOs.ProductDTO;
import com.internship.AuctionApp.Models.Image;
import com.internship.AuctionApp.Models.Product;
import com.internship.AuctionApp.services.ImageService;
import com.internship.AuctionApp.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


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
    public ResponseEntity<?> getProducts(@RequestParam(required = false) String sort) {
        List<Product> productsList = null;
        if (sort != null) {
            if (sort.equals("created_at")) {
                productsList = productService.findAllOrderByCreatedAt();
            } else if (sort.equals("auction_end")) {
                productsList = productService.findAllOrderByAuctionDateEnd();
            }
        } else {
            productsList = productService.findAllProducts();
        }
        return ResponseEntity.ok().body(productsList);
    }

    @GetMapping(path = "/get/product")
    public ResponseEntity<?> getProduct(@RequestParam(name = "productId") String id) {
        System.out.println("Uslo je");
        final Long product_id = Long.valueOf(id);
        Product product = null;
        try {
            product = productService.getProduct(product_id);
        } catch (Exception err) {
            return ResponseEntity.badRequest().build();
        }
        final List<Image> imagesList = imageService.findAllByProductId(product);
        ProductDTO productResponse = new ProductDTO(product, imagesList);
        String dateString = LocalDate.now().toString();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Date", dateString);

        return ResponseEntity.ok().headers(httpHeaders).body(productResponse);
    }

}
