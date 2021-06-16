package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ProductDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleProductDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Product;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.List;

@Mapper
@Component
public interface ProductMapper {
    @Named("toDto")
    ProductDto entityToDto(Product product);

    @IterableMapping(qualifiedByName = "toDto")
    List<ProductDto> entityToDto(List<Product> product);

    @Named("toEntity")
    Product dtoToEntity(ProductDto productDto);

    @IterableMapping(qualifiedByName = "toEntity")
    List<Product> dtoToEntity(List<ProductDto> productDto);

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
