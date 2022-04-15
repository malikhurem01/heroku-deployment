package com.internship.AuctionApp.Products;

import com.internship.AuctionApp.authentication.AuthenticationController;
import com.internship.AuctionApp.DTOs.ProductDTO;
import com.internship.AuctionApp.Exceptions.EntityNotFoundException;
import com.internship.AuctionApp.Exceptions.ServiceException;
import com.internship.AuctionApp.Models.Product;
import com.internship.AuctionApp.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@RestController
@RequestMapping(path = "/api/v1")
@CrossOrigin(origins = {"http://localhost:3000", "https://auction-app-internship-fr.herokuapp.com/"})
public class ProductsController {

    private final static Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    private final ProductService productService;

    @Autowired
    public ProductsController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(path = "/get/products")
    public ResponseEntity<?> getProductsWithFilter(@RequestBody(required = false) ProductFilterRequest filterRequest) {
        ProductFilterResponse productFilterResponse = productService.filter(filterRequest);
        return ResponseEntity.ok().body(productFilterResponse);
    }

    @GetMapping(path = "/get/product")
    public ResponseEntity<?> getProduct(@RequestParam(name = "productId") final String id) {
        final Long product_id = Long.valueOf(id);
        try {
            Product product = productService.getProduct(product_id);
            ProductDTO productResponse = new ProductDTO(product);
            String dateString = LocalDate.now().toString();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Date", dateString);
            return ResponseEntity.ok().headers(httpHeaders).body(productResponse);
        } catch (EntityNotFoundException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
