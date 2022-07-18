package com.itransition.courseproject.config.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.itransition.courseproject.dto.response.collection.CollectionResponse;
import com.itransition.courseproject.dto.response.comment.CommentResponse;
import com.itransition.courseproject.model.entity.collection.Collection;
import com.itransition.courseproject.model.entity.item.Comment;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AppComponents {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        //CommentResponse
        TypeMap<Comment, CommentResponse> typeMap = modelMapper
                .createTypeMap(Comment.class, CommentResponse.class);
        typeMap.addMappings(mapping -> mapping.map(Comment::getUser, CommentResponse::setCommentAuthor));
        typeMap.addMapping(src -> src.getItem().getId(), CommentResponse::setItemId);

        //CollectionResponse
        TypeMap<Collection, CollectionResponse> typeMap1 =
                modelMapper.createTypeMap(Collection.class, CollectionResponse.class);
        typeMap1.addMappings(mapping -> mapping.map(Collection::getUser, CollectionResponse::setUserResponse));
        //typeMap1.addMapping(source -> source.getTopic().getName(), CollectionResponse::setTopic);


        modelMapper.getConfiguration().setSkipNullEnabled(true);
        return modelMapper;
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(8);
    }
}
