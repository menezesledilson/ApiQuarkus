package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import org.acme.dto.ProductDTO;
import org.acme.entity.ProductEntity;
import org.acme.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ProductService {

    @Inject
    ProductRepository productRepository;

    //Método que busca
    public List<ProductDTO> getAllProducts(){

      List<ProductDTO>products = new ArrayList<>();
      productRepository.findAll().stream().forEach(item->{
          products.add(mapProductEntityToDTO(item));
      });
      return products;
    }

    //Método que Cria
    public  void createNewProduct(ProductDTO product){
        productRepository.persist(mapProductDTOEntity(product));
    }

    //Método que Altualiza
    public void changeProduct(Long id, ProductDTO product) {
        ProductEntity productEntity = productRepository.findById(id);
        if (productEntity != null) {
            productEntity.setName(product.getName());
            productEntity.setDescription(product.getDescription());
            productEntity.setCategory(product.getCategory());
            productEntity.setModel(product.getModel());
            productEntity.setPrice(product.getPrice());

            productRepository.persist(productEntity);
        } else {
            throw new WebApplicationException("Product with id " + id + " not found", 404);
        }
    }


    //Método que remover
    public  void deleteProduct(Long id){
        productRepository.deleteById(id);
    }

    private ProductDTO mapProductEntityToDTO(ProductEntity productEntity){
        ProductDTO product = new ProductDTO();

        product.setName(productEntity.getName());
        product.setDescription(productEntity.getDescription());
        product.setCategory(productEntity.getCategory());
        product.setModel(productEntity.getModel());
        product.setPrice(productEntity.getPrice());

        return product;
    }

    private ProductEntity mapProductDTOEntity(ProductDTO productDto){
        ProductEntity product = new ProductEntity();

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setCategory(productDto.getCategory());
        product.setModel(productDto.getModel());
        product.setPrice(productDto.getPrice());

        return product;
    }

}
