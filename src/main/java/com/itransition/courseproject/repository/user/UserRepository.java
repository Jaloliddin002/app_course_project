package com.itransition.courseproject.repository.user;

import com.itransition.courseproject.model.entity.user.User;
import com.itransition.courseproject.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailAndStatusIsNot(String email, Status status);

    Optional<User> findByEmailAndStatus(String email, Status status);

    Boolean existsByEmailAndStatus(String email, Status status);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.status = :status  WHERE u.id IN :ids")
    void updateUserStatus(@Param("status") Status status,
                             @Param("ids") Iterable<Long> ids);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.roles = :role  WHERE u.id IN :ids")
    void updateUserRole(@Param("role") Integer role,
                        @Param("ids") Iterable<Long> ids);
}

