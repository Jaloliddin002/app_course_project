package com.itransition.courseproject.model.entity.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.itransition.courseproject.model.entity.BaseEntity;
import com.itransition.courseproject.model.entity.collection.Collection;
import com.itransition.courseproject.model.entity.tag.Tag;
import com.itransition.courseproject.model.entity.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class Item extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "int default 0")
    private long likes;

    @ManyToOne
    private Collection collection;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="item_tags",
            joinColumns=@JoinColumn(name="item_id"),
            inverseJoinColumns=@JoinColumn(name="tags_id")
    )
    private List<Tag> tags;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name="item_liked_users",
            joinColumns=@JoinColumn(name="item_id"),
            inverseJoinColumns=@JoinColumn(name="liked_users_id")
    )
    private Set<User> likedUsers = new HashSet<>();

    public long getLikes() {
        return likedUsers.size();
    }

    public Item(String name, Collection collection, List<Tag> tagList) {
        this.name = name;
        this.collection = collection;
        this.tags = tagList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return  Objects.equals(name, item.name) && Objects.equals(super.getId(), item.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, super.getId());
    }

}
