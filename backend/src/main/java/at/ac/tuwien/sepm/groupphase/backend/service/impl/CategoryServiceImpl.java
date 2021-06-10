package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Category;
import at.ac.tuwien.sepm.groupphase.backend.entity.Product;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.CategoryRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());


    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category createCategory(Category category) {
        LOGGER.trace("createCategory({})", category);
        return categoryRepository.save(category);
    }

    @Transactional
    @Override
    public Page<Category> getAllCategoriesPerPage(int page, int pageCount) {
        LOGGER.trace("getAllCategories({},{})", page, pageCount);
        if (pageCount == 0) {
            pageCount = 15;
        } else if (pageCount > 50) {
            pageCount = 50;
        }
        Pageable pages = PageRequest.of(page, pageCount);
        return this.categoryRepository.findAll(pages);
    }

    @Transactional
    @Override
    public List<Category> getAllCategories() {
        LOGGER.trace("getAllCategories()");
        return this.categoryRepository.findAll();
    }

    @Override
    public Long getCategoriesCount() {
        LOGGER.trace("getAllCategoriesCount()");
        return this.categoryRepository.count();
    }

    @Override
    public void updateCategory(Long categoryId, Category category) {
        LOGGER.trace("updateCategory({})", category);
        Category updatedCategory = this.categoryRepository
            .findById(categoryId).orElseThrow(() -> new NotFoundException("Could not find category with Id:" + categoryId));
        updatedCategory.setName(category.getName());
        categoryRepository.save(updatedCategory);
    }


}
