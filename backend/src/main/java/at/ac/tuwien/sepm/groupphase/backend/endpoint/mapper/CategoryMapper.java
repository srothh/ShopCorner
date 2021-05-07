package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CategoryDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ProductDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Category;
import at.ac.tuwien.sepm.groupphase.backend.entity.Product;
import org.mapstruct.Mapper;

@Mapper
public interface CategoryMapper {

        CategoryDto entityToDto(Category category);
        Category dtoToEntity(CategoryDto categoryDto);
}
