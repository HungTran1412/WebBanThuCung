package dev.backend.webbanthucung.controller;

import dev.backend.webbanthucung.dto.model.PageProductsDTO;
import dev.backend.webbanthucung.dto.model.ProductDetail;
import dev.backend.webbanthucung.entity.Product;
import dev.backend.webbanthucung.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ProductsController {
    @Autowired
    private ProductsService productService;

//    @CrossOrigin(origins = "http://127.0.0.1:5500", methods = { RequestMethod.GET, RequestMethod.POST })
    @GetMapping(value = "/products/")
    public PageProductsDTO testAPI(@RequestParam(value = "skip", required = false) Integer skip,
                                   @RequestParam(value = "limit", required = false) Integer limit,
                                   @RequestParam(value = "filter", required = false) String filter) {

        PageProductsDTO listProductRp = productService.findProductsByRangeWithSpecification(skip, limit, filter);
        return listProductRp;

    }

    @GetMapping(value = "/products/search")
    public  PageProductsDTO testAPI2(@RequestParam(value = "name", required = false) String name,
                                     @RequestParam(value = "skip", required = false) Integer skip,
                                     @RequestParam(value = "limit", required = false) Integer limit,
                                     @RequestParam(value = "filter", required = false) String filter) {

        PageProductsDTO ab = productService.productSearch(name,skip,limit,filter);
        return ab;
    }

    @GetMapping(value = "/products/{id}")
    public ProductDetail detail(@PathVariable Integer id) {
        ProductDetail a = productService.Detail(id);
        return a;
    }

    @PostMapping(value = "/products/create")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product savedProduct = productService.saveProduct(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @PutMapping(value = "/products/update/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer id, @RequestBody Product product) {
        Product savedProduct = productService.updateProduct(id, product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/products/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
