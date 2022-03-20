package com.internship.AuctionApp.Products;

import com.internship.AuctionApp.DTOs.ProductDTO;
import com.internship.AuctionApp.DTOs.ProductPaginationDTO;
import com.internship.AuctionApp.Models.Image;
import com.internship.AuctionApp.Models.Product;
import com.internship.AuctionApp.services.ImageService;
import com.internship.AuctionApp.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping(path = "/api/v1")
@CrossOrigin(origins = {"http://localhost:3000", "https://auction-app-internship-fr.herokuapp.com/"})
public class ProductsController {

    private static final int PRODUCTS_PER_PAGE = 4;

    private final ProductService productService;

    private final ImageService imageService;

    @Autowired
    public ProductsController(final ProductService productService,
                              final ImageService imageService) {
        this.productService = productService;
        this.imageService = imageService;
    }

    @GetMapping(path = "/get/products")
    public ResponseEntity<?> getProducts(@RequestParam(required = false, defaultValue = "createdAt") final String sort,
                                         @RequestParam(required = false, defaultValue = "0") final int offset) {
        Page<Product> productsPage = productService.findAllProductsWithPagination(offset, PRODUCTS_PER_PAGE, sort);
        ProductPaginationDTO productPaginationDTO = new ProductPaginationDTO(productsPage);
        return ResponseEntity.ok().body(productPaginationDTO);
    }

    @GetMapping(path = "/get/product")
    public ResponseEntity<?> getProduct(@RequestParam(name = "productId") final String id) {
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
