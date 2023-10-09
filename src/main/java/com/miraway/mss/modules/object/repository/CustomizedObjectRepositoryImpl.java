package com.miraway.mss.modules.object.repository;

import com.miraway.mss.modules.object.dto.filter.ObjectFilter;
import com.miraway.mss.modules.object.entity.Object;
import com.miraway.mss.modules.object.enumaration.ObjectCategory;
import com.miraway.mss.modules.object.enumaration.ObjectType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Collation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import static com.miraway.mss.constants.Constants.*;
import static java.util.Locale.ENGLISH;

public class CustomizedObjectRepositoryImpl implements CustomizedObjectRepository{

    private final MongoTemplate mongoTemplate;

    public CustomizedObjectRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Page<Object> getObjectList(ObjectFilter filter, Pageable pageable) {
        Query query = buildObjectFilterQuery(filter);
        query.with(pageable);

        return PageableExecutionUtils.getPage(
            this.mongoTemplate.find(query, Object.class),
            pageable,
            () -> this.mongoTemplate.count(query.skip(0).limit(0), Object.class)
        );
    }

    private Query buildObjectFilterQuery(ObjectFilter filter) {
        Query query = new Query();

        query.collation(Collation.of(ENGLISH).strength(Collation.ComparisonLevel.secondary()));
        if (filter == null) {
            return query;
        }

        List<Criteria> criteriaList = new ArrayList<>();

        String text = filter.getText();
        Set<String> parentIds = filter.getParentIds();
        ObjectType type = filter.getType();
        Set<ObjectCategory> category = filter.getObjectCategory();


        if (StringUtils.isNotBlank(text)) {
            text = Pattern.quote(text);
            criteriaList.add(
                new Criteria()
                    .orOperator(
                        Criteria.where(DISPLAY_NAME).regex(text, "i")
                    )
            );
        }

        if (!CollectionUtils.isEmpty(parentIds)) {
            criteriaList.add(Criteria.where(PARENT_ID).in(parentIds));
        }

        if (type != null) {
            criteriaList.add(Criteria.where(TYPE).is(type));
        }

        if (!CollectionUtils.isEmpty(category)) {
            criteriaList.add(Criteria.where(CATEGORY).in(category));
        }

        if (!CollectionUtils.isEmpty(criteriaList)) {
            query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])));
        }

        return query;
    }
}
