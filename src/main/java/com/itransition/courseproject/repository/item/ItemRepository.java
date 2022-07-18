package com.itransition.courseproject.repository.item;

import com.itransition.courseproject.model.entity.item.Item;
import com.itransition.courseproject.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {


    @Query(value = "select collection_id from item group by collection_id order by count(collection_id) desc limit 5", nativeQuery = true)
    List<Long> findTopCollectionIds();

    void deleteAllByCollectionId(Long id);

    Boolean existsByIdAndLikedUsers_Id(Long itemId, Long userId);

    @Transactional
    @Modifying
    @Query(value = "insert into item_liked_users(item_id, liked_users_id) select :itemId, :userId where " +
            " not exists (select 1 from item_liked_users t where t.item_id = :itemId and t.liked_users_id = :userId)",
        nativeQuery = true
    )
    void insertItemLike(@Param("itemId") long itemId, @Param("userId") long userId);

    @Transactional
    @Modifying
    @Query(value = "delete from item_liked_users where " +
            " exists (select 1 from item_liked_users t where t.item_id = :itemId and t.liked_users_id = :userId)",
            nativeQuery = true
    )
    void deleteItemLike(@Param("itemId") long itemId, @Param("userId") long userId);

    boolean existsByIdAndCollection_UserEmailAndCollection_UserStatus(Long itemId, String email, Status status);

    @Query(value = "select i.collection_id from item i order by creation_date desc limit 10" , nativeQuery = true)
    List<Long> findLatest10ItemIds();
}
