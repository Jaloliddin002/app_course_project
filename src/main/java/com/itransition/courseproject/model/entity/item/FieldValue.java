package com.itransition.courseproject.model.entity.item;

import com.itransition.courseproject.model.entity.BaseEntity;
import com.itransition.courseproject.model.entity.collection.Field;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.Objects;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class FieldValue extends BaseEntity {

    private String value;

    @ManyToOne
    private Item item;

    @OneToOne
    private Field field;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FieldValue that = (FieldValue) o;
        return Objects.equals(value, that.value) && Objects.equals(super.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getId(), value);
    }
}
