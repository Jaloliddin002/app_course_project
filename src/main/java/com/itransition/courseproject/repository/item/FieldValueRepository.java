package com.itransition.courseproject.repository.item;

import com.itransition.courseproject.model.entity.collection.Field;
import com.itransition.courseproject.model.entity.item.FieldValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface FieldValueRepository extends JpaRepository<FieldValue, Long> {

    List<FieldValue> findAllByItem_CollectionId(Long collection_id);

    List<FieldValue> findAllByItem_Tags_Id(Long item_tags_id);

    List<FieldValue> findAllByItem_CollectionIdOrderByItem_IdAscField_IdAsc(Long collection_id);


    void deleteAllByItemId(Long itemId);

    int deleteAllByItem_CollectionId(Long item_collection_id);

    void deleteAllByFieldIn(Collection<Field> field);

    void deleteFieldValuesByField_CollectionId(Long fieldCollectionId);
}
