package com.javamentor.developer.social.platform.service.impl.model.group;

import com.javamentor.developer.social.platform.dao.abstracts.model.group.GroupCategoryDao;
import com.javamentor.developer.social.platform.models.entity.group.GroupCategory;
import com.javamentor.developer.social.platform.service.abstracts.model.group.GroupCategoryService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GroupCategoryServiceImpl extends GenericServiceAbstract<GroupCategory, Long> implements GroupCategoryService {

    private final GroupCategoryDao groupCategoryDao;

    @Autowired
    public GroupCategoryServiceImpl( GroupCategoryDao dao ) {
        super(dao);
        this.groupCategoryDao = dao;
    }

    @Override
    @Transactional
    public Optional<GroupCategory> getByCategory( String category ) {
        return groupCategoryDao.getByCategory(category);
    }

    /**
     * Можно было и void, но тогда не понять - сохранилась ли новая категория или уже была.
     * О результате операции необходимо сообщить на фронт,
     * а так как описывать любую сервисную логику в контроллере - дурной тон,
     * мы возвращаем булевское значение и в зависимости от него - код состояния и сообщение.
     */
    @Transactional
    public boolean createCategory( GroupCategory category ) {
        String categoryName = category.getCategory();

        Optional<GroupCategory> tempCategory = getByCategory(categoryName);
        if(tempCategory.isPresent()) {
            return false;
        }
        groupCategoryDao.createCategory(category);
        return true;
    }

    @Transactional
    public int deleteCategory( GroupCategory category ) {

        return groupCategoryDao.deleteCategory(category);

    }

    public List<GroupCategory> getAllCategories() {
        return groupCategoryDao.getAllCategories();
    }
}
