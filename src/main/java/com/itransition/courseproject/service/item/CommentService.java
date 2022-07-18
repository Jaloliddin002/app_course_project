package com.itransition.courseproject.service.item;

import com.itransition.courseproject.dto.request.comment.CommentRequest;
import com.itransition.courseproject.dto.response.APIResponse;
import com.itransition.courseproject.dto.response.comment.CommentResponse;
import com.itransition.courseproject.exception.resource.ResourceNotFoundException;
import com.itransition.courseproject.exception.user.UserNotFoundException;
import com.itransition.courseproject.model.entity.item.Comment;
import com.itransition.courseproject.model.entity.item.Item;
import com.itransition.courseproject.model.entity.user.User;
import com.itransition.courseproject.repository.item.CommentRepository;
import com.itransition.courseproject.repository.item.ItemRepository;
import com.itransition.courseproject.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.itransition.courseproject.util.constant.ResourceConstants.*;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final ModelMapper modelMapper;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;


    public CommentResponse create(CommentRequest commentRequest) {

        User user = userRepository.findById(commentRequest.getUserId()).orElseThrow(() -> {
            throw new UserNotFoundException(commentRequest.getUserId());
        });
        Item item = itemRepository.findById(commentRequest.getItemId()).orElseThrow(() -> {
            throw new ResourceNotFoundException(ITEM_ENG,ITEM_RUS, commentRequest.getItemId());
        });

        return modelMapper.map(commentRepository
                        .save(new Comment(commentRequest.getText(), user, item)),
                        CommentResponse.class);
    }

    public APIResponse get(Long id) {
        return APIResponse.success(commentRepository
                .findById(id)
                .orElseThrow(()-> {throw new ResourceNotFoundException(COMMENT_ENG,COMMENT_RUS, id);}));
    }

    public APIResponse getCommentsByItemId(Long itemId) {
        return APIResponse.success(List.of(modelMapper.map(commentRepository.findAllByItemIdOrderByCreationDateDesc(itemId), CommentResponse[].class)));
    }
}
