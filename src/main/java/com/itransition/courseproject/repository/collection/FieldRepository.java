package com.itransition.courseproject.repository.collection;

import com.itransition.courseproject.model.entity.collection.Collection;
import com.itransition.courseproject.model.entity.collection.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FieldRepository extends JpaRepository<Field, Long> {

    List<Field> findAllByCollection(Collection collection);

//    @Query(value = "SELECT collection_id FROM field GROUP BY collection_id ORDER BY COUNT(collection_id) DESC LIMIT 5", nativeQuery = true)
//    List<Long> findTopCollectionIds();

    List<Field> findAllByCollection_Id(Long id);

    List<Field> findAllByCollection_IdOrderByIdAsc(Long collectionId);

    void deleteFieldsByCollectionId(Long collectionId);
}
