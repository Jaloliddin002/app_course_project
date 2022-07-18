package com.itransition.courseproject.repository.collection;

import com.itransition.courseproject.model.entity.collection.Collection;
import com.itransition.courseproject.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long> {

    List<Collection> findAllByUser_Id(Long userId);

    boolean existsByIdAndUserEmailAndUserStatus(Long id, String userEmail, Status userStatus);
}
