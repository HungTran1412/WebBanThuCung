package dev.backend.webbanthucung.service.Impl;

import dev.backend.webbanthucung.dto.model.PageProductDTO;
import dev.backend.webbanthucung.dto.model.ProductDTO;
import dev.backend.webbanthucung.entity.Product;
import dev.backend.webbanthucung.repository.ProductRepository;
import dev.backend.webbanthucung.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productsRepository;

    @Override
    public PageProductDTO productsDTO(Integer limit, Integer skip) {
        // TODO Auto-generated method stub
        Integer s = (int) productsRepository.count();
        List<Product> listProductRp;
        if (limit != null && skip != null) {
            if (s < (skip + limit)) {
                return null;
            }
            listProductRp = productsRepository.findByIdBetween(limit, skip + limit);
        } else if (limit != null && skip == null) {
            listProductRp = productsRepository.findByIdLessThanEqual(limit);
        } else if (limit == null && skip != null) {
            listProductRp = productsRepository.findByIdGreaterThanEqual(skip);
        } else {
            listProductRp = productsRepository.findAll();
        }
        PageProductDTO pageProduct = new PageProductDTO();
        List<ProductDTO> listProductDTO = new ArrayList<ProductDTO>();
        for (Product x : listProductRp) {

            ProductDTO a = new ProductDTO();
            a.setAge(x.getAge());
            a.setPrice(x.getPrice());
            a.setName(x.getName());
            a.setId(x.getId());
            a.setDes(x.getDes());
            a.setImg(x.getImg());
            a.setQuantity(x.getQuantity());
            a.setGender(x.getGender());

            listProductDTO.add(a);
        }
        pageProduct.setProduct(listProductDTO);
        pageProduct.setLimit(limit);
        pageProduct.setSkip(skip);
        pageProduct.setTotal(s);

        return pageProduct;
    }

//    public Product addProduct(ProductDTO productDTO) {
//        Product prod = new Product();
//
//        try {
//            prod.setAge(productDTO.getAge());
//            prod.setPrice(productDTO.getPrice());
//            prod.setName(productDTO.getName());
//            prod.setId(productDTO.getId());
//            prod.setDes(productDTO.getDes());
//            prod.setQuantity(productDTO.getQuantity());
//            prod.setGender(productDTO.getGender());
//
//            return productsRepository.save(prod);
//        } catch (Exception e) {
//            System.out.println("Loi! khong them duoc san pham");
//            throw new RuntimeException(e);
//        }
//    }
}
