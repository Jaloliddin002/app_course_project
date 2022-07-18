package com.itransition.courseproject.model.entity.collection;

import com.itransition.courseproject.model.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class Topic extends BaseEntity {

    @Column(nullable = false)
    private String nameENG;

    @Column(nullable = false)
    private String nameRUS;
}
