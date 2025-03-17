package dev.backend.webbanthucung.controller;

import dev.backend.webbanthucung.dto.model.PageProductDTO;
import dev.backend.webbanthucung.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {
    @Autowired
    private ProductService productService;

    @CrossOrigin(origins = "http://127.0.0.1:5500", methods = { RequestMethod.GET, RequestMethod.POST })
    @GetMapping(value = "/product")
    public PageProductDTO testAPI(@RequestParam(value="limit",required=false) Integer limit , @RequestParam(value="skip",required=false) Integer skip) {

        PageProductDTO an = productService.productsDTO(limit,skip);
        return an;

    }
}
