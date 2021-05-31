package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Category;
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
        // to fetch categories in a lazy fetched behaviour( because of products) there are 2 steps necessary
        // 1. search all Ids in a paginated manner 2. get all entries that corresponds to these ids and fetch the
        // related products -> and eventually return a new Page - Object
        Pageable pages = PageRequest.of(page, pageCount);
        List<Long> idsOfPageableEntries = categoryRepository.geAllIds(pages);
        List<Category> categories = categoryRepository.findAllCategoriesPerPage(idsOfPageableEntries);
        return new PageImpl<Category>(categories, pages, categories.size());
    }

    @Transactional
    @Override
    public List<Category> getAllCategories() {
        LOGGER.trace("getAllCategories()");
        return this.categoryRepository.getAllCategories();
    }

    @Override
    public Long getCategoriesCount() {
        LOGGER.trace("getAllCategoriesCount()");
        return this.categoryRepository.count();
    }


}
