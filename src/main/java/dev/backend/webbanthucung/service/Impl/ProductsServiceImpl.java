package dev.backend.webbanthucung.service.Impl;

import dev.backend.webbanthucung.dto.model.PageProductsDTO;
import dev.backend.webbanthucung.dto.model.ProductDetail;
import dev.backend.webbanthucung.dto.model.ProductsDTO;
import dev.backend.webbanthucung.entities.Product;
import dev.backend.webbanthucung.repository.ProductsRepository;
import dev.backend.webbanthucung.repository.custom.specification.ProductSpecification;
import dev.backend.webbanthucung.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductsServiceImpl implements ProductsService {
    @Autowired
    ProductsRepository productsRepository;
    Specification<Product> spec;

    @Override
    public PageProductsDTO productSearch(String name, Integer skip, Integer limit, String filter) {

        Specification<Product> spec = ProductSpecification.isFilter(filter)
                .and(ProductSpecification.hasName(name));

        Integer s = (int) productsRepository.count();
        List<Product> listProductRp;
        PageProductsDTO pageProduct = new PageProductsDTO();
        Page<Product> pageResult;
        Pageable pageable;
        pageable = PageRequest.of(0, s);
        pageResult = productsRepository.findAll(spec, pageable);
        listProductRp = pageResult.getContent();
        s = listProductRp.size();
        if (limit != null && skip != null) {
            pageProduct.setLimit(limit);
            pageProduct.setSkip(skip);
            if (skip > s)
                listProductRp = null;
            else if (listProductRp.size() > skip + limit) {
                listProductRp = pageResult.getContent().subList(skip, skip + limit);
            } else {

                listProductRp = pageResult.getContent().subList(skip, listProductRp.size());

            }
        } else if (limit != null) {
            pageProduct.setLimit(limit);
            pageProduct.setSkip(0);
            listProductRp = pageResult.getContent().subList(0, limit);
        } else if (skip != null) {
            pageProduct.setLimit(listProductRp.size());
            pageProduct.setSkip(skip);
            if (skip >= s)
                listProductRp = null;
            else {
                listProductRp = pageResult.getContent().subList(skip, listProductRp.size());
            }
        } else {
            pageProduct.setSkip(0);
            pageProduct.setLimit(listProductRp.size());
            listProductRp = pageResult.getContent();
        }
        List<ProductsDTO> listProductDTO = new ArrayList<ProductsDTO>();
        if(listProductRp != null) {
            for (Product x : listProductRp) {

                ProductsDTO a = new ProductsDTO();
                a.setAge(x.getAge());
                a.setPrice(x.getPrice());
                a.setName(x.getName());
                a.setId(x.getId());
                a.setBreed(x.getBreed());
                a.setColor(x.getColor());
                a.setImg(x.getImg());
                a.setDescription(x.getDescription());
                a.setQuantity(x.getQuantity());
                a.setSize(x.getSize());
                listProductDTO.add(a);
            }
        }
        pageProduct.setProduct(listProductDTO);
        pageProduct.setTotal(s);
        return pageProduct;
    }

    @Override
    public ProductDetail Detail(Integer id) {

        ProductDetail productDetail = new ProductDetail();
        Optional<Product> pro = productsRepository.findById(id);
        ProductsDTO proDTO = new ProductsDTO();
        proDTO.setId(id);
        proDTO.setBreed(pro.get().getBreed());
        proDTO.setColor(pro.get().getColor());
        proDTO.setSize(pro.get().getSize());
        proDTO.setImg(pro.get().getImg());
        proDTO.setAge(pro.get().getAge());
        proDTO.setName(pro.get().getName());
        proDTO.setDescription(pro.get().getDescription());
        proDTO.setPrice(pro.get().getPrice());
        proDTO.setQuantity(pro.get().getQuantity());
        productDetail.setProduct(proDTO);

        spec = ProductSpecification.RelatedProducts(pro.get().getBreed(), pro.get().getColor(), pro.get().getSize(),
                id);
        List<Product> listEntity = productsRepository.findAll(spec);
        List<ProductsDTO> listDTO = new ArrayList<>();

        for (Product tt : listEntity) {
            if (listDTO.size() >= 8)
                break;
            ProductsDTO x = new ProductsDTO();
            x.setAge(tt.getAge());
            x.setDescription(tt.getDescription());
            x.setId(tt.getId());
            x.setBreed(tt.getBreed());
            x.setColor(tt.getColor());
            x.setImg(tt.getImg());
            x.setPrice(tt.getId());
            x.setName(tt.getName());
            listDTO.add(x);

        }
        productDetail.setProductList(listDTO);
        return productDetail;
    }

    @Override
    public Product saveProduct(Product product) {

        return productsRepository.save(product);
    }

    @Override
    public Product updateProduct(Integer id, Product productDetails) {
        Optional<Product> product = productsRepository.findById(id);
        product.get().setAge(productDetails.getAge());
        product.get().setName(productDetails.getName());
        product.get().setDescription(productDetails.getDescription());
        product.get().setImg(productDetails.getImg());
        product.get().setAge(productDetails.getAge());
        product.get().setBreed(productDetails.getBreed());
        product.get().setColor(productDetails.getColor());
        product.get().setSize(productDetails.getSize());
        product.get().setPrice(productDetails.getPrice());
        product.get().setQuantity(productDetails.getQuantity());

        return productsRepository.save(product.get());
    }

    @Override
    public void deleteProduct(Integer id) {
        Optional<Product> product = productsRepository.findById(id);
        productsRepository.delete(product.get());
    }

    public PageProductsDTO findProductsByRangeWithSpecification(Integer skip, Integer limit, String filter) {
        Integer s = (int) productsRepository.count();
        List<Product> listProductRp;
        PageProductsDTO pageProduct = new PageProductsDTO();
        Specification<Product> spec = ProductSpecification.isFilter(filter);
        Page<Product> pageResult;
        Pageable pageable;
        pageable = PageRequest.of(0, s);
        pageResult = productsRepository.findAll(spec, pageable);
        listProductRp = pageResult.getContent();
        s = listProductRp.size();
        if (limit != null && skip != null) {

            pageProduct.setLimit(limit);
            pageProduct.setSkip(skip);
            if (skip > s)
                listProductRp = null;
            else if (listProductRp.size() > skip + limit) {
                listProductRp = pageResult.getContent().subList(skip, skip + limit);
            } else {
                listProductRp = pageResult.getContent().subList(skip, listProductRp.size());
            }

        } else if (limit != null) {
            pageProduct.setLimit(limit);
            pageProduct.setSkip(0);
            listProductRp = pageResult.getContent().subList(0, limit);

        } else if (skip != null) {
            pageProduct.setLimit(listProductRp.size());
            pageProduct.setSkip(skip);
            if (skip >= s)
                listProductRp = null;
            else
                listProductRp = pageResult.getContent().subList(skip, listProductRp.size());

        } else {
            pageProduct.setSkip(0);
            pageProduct.setLimit(listProductRp.size());
            listProductRp = pageResult.getContent().subList(0, listProductRp.size());

        }
        List<ProductsDTO> listProductDTO = new ArrayList<ProductsDTO>();
        if(listProductRp != null) {
            for (Product x : listProductRp) {

                ProductsDTO a = new ProductsDTO();
                a.setAge(x.getAge());
                a.setPrice(x.getPrice());
                a.setName(x.getName());
                a.setId(x.getId());
                a.setBreed(x.getBreed());
                a.setColor(x.getColor());
                a.setDescription(x.getDescription());
                a.setQuantity(x.getQuantity());
                a.setImg(x.getImg());
                a.setSize(x.getSize());
                listProductDTO.add(a);
            }
        }
        pageProduct.setProduct(listProductDTO);

        pageProduct.setTotal(s);
        return pageProduct;
    }
}
