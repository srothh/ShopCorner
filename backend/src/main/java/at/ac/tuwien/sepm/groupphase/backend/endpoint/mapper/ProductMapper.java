package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ProductDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleProductDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Product;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Mapper
@Component
public interface ProductMapper  {
    ProductDto entityToDto(Product product);

    Product dtoToEntity(ProductDto productDto);

    SimpleProductDto simpleProductEntityToDto(Product product);

    default String map(byte[] value) {
        if (value != null) {
            return Base64.getEncoder().encodeToString(value);
        }
        return null;
    }

    default byte[] map(String str) {
        if (str != null) {
            return Base64.getDecoder().decode(str);
        }
        return null;
    }
}
