package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Product;
import at.ac.tuwien.sepm.groupphase.backend.repository.MessageRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.ProductRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

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
    public Product createProduct(Product product) throws Exception {
        validateProduct(product);
        return this.productRepository.save(product);
    }
    public void validateProduct(Product product) throws Exception{
        //name is mandatory!
        if (product.getName() == null) {
            throw new Exception("name cannot be null!");
        }

        //a string with only whitespaces not allowed
        if (product.getName().trim().isEmpty()){
            throw new Exception("name consist of only whitespaces");
        }
        //maximum of characters exceeded!
        if (product.getName().length() > 20 ) {
            throw new Exception("name is too long!");

        }
        if (product.getDescription() != null){
            if (product.getDescription().trim().isEmpty()){
                throw new Exception("name consist of only whitespaces");
            }
            if (product.getDescription().length() > 50 ) {
                throw new Exception("name is too long!");
            }

        }

    }
}
