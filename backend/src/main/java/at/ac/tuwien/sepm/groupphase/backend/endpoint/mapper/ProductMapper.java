package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ProductDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.Base64;

@Mapper
public interface ProductMapper {
    ProductDto entityToDto(Product product);

    Product dtoToEntity(ProductDto productDto);

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
