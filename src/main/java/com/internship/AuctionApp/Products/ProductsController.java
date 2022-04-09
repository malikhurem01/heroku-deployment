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
    private static final int PRODUCTS_PER_PAGE = 8;
    private final ProductService productService;

    @Autowired
    public ProductsController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(path = "/get/products/filter")
    public ResponseEntity<?> getProductsWithFilter(@RequestBody(required = false) FilterRequest filterRequest) {
        ProductFilterResponse productFilterResponse = productService.filter(filterRequest);
        return ResponseEntity.ok().body(productFilterResponse);
    }

    @GetMapping(path = "/get/products")
    public ResponseEntity<?> getProducts(@RequestParam(required = false, defaultValue = "createdAt") final String sort,
                                         @RequestParam(required = false, defaultValue = "0") final int offset,
                                         @RequestParam(required = false, defaultValue = "all") final String filter,
                                         @RequestParam(required = false, defaultValue = "0") final String id) {
        ProductFilterRequest filterRequest = null;
        try {
            if (filter.equals("all")) {
                filterRequest = new ProductFilterRequest(offset, PRODUCTS_PER_PAGE, sort);
            } else {
                filterRequest = new ProductFilterRequest(
                        Long.valueOf(id),
                        offset,
                        PRODUCTS_PER_PAGE,
                        sort);
            }
            final ProductFilterResponse filteredProducts = productService.filter(filterRequest, filter);
            return ResponseEntity.ok().body(filteredProducts);
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
