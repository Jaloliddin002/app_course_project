package com.itransition.courseproject.model.entity.collection;

import com.itransition.courseproject.model.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Getter
@Setter
@Entity
public class Field extends BaseEntity {

    @Column(nullable = false)
    private String name;

    private int type;

    @ManyToOne
    private Collection collection;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Field field = (Field) o;
        return type == field.type && Objects.equals(name, field.name) && Objects.equals(super.getId(), field.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, super.getId());
    }
}
