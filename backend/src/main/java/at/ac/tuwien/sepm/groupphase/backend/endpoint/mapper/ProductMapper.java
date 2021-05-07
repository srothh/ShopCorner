package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ProductDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper
public interface ProductMapper {
    ProductDto entityToDto(Product product);
    Product dtoToEntity(ProductDto productDto);
}
