package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Product;
import at.ac.tuwien.sepm.groupphase.backend.repository.MessageRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.ProductRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
@Service
public class ProductServiceImpl implements ProductService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(Product product) {
        if (validateProduct(product)) {
            return this.productRepository.save(product);
        }
        return null;
    }
    public boolean validateProduct(Product product){
        //name is mandatory!
        if (product.getName() == null) {
            System.out.println("property cannot be null");
            return false;
        }

        //a string with only whitespaces not allowed
        if (product.getName().trim().isEmpty()){
            System.out.println("name consist of only whitespaces");
            return false;
        }
        if (product.getName().length() > 20 ) {
            System.out.println("name too long!");
            return false;
        }
        if (product.getDescription() != null){
            if (product.getDescription().trim().isEmpty()){
                System.out.println("description consist of only whitespaces!");
                return false;
            }
            if (product.getDescription().length() > 50 ) {
                System.out.println("description too long!");
                return false;
            }

        }
        return true;
    }
}
