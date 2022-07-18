package com.itransition.courseproject.model.entity.collection;

import com.itransition.courseproject.model.entity.BaseEntity;
import com.itransition.courseproject.model.entity.user.User;
import com.itransition.courseproject.model.enums.Language;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Collection extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    private String imageUrl;

    @ManyToOne
    private Topic topic;

    @ManyToOne
    private User user;

    public List<String[]> toCSVString(String lang) {
        boolean isENG = lang.equalsIgnoreCase(Language.ENG.languageENG);
        return List.of(
                isENG ? new String[]{"name", "description", "image", "topic", "creation date", "author"} :
                new String[]{"название", "описание", "изображение", "заголовок", "дата создания", "автор"},
                new String[]{name, description, imageUrl, isENG ? topic.getNameENG() : topic.getNameRUS(), getCreationDate().toString(), user.getName()}
        );
    }
}
