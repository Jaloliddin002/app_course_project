package com.itransition.courseproject.controller.item;

import com.itransition.courseproject.controller.CRUDController;
import com.itransition.courseproject.dto.request.item.ItemRequest;
import com.itransition.courseproject.dto.response.APIResponse;
import com.itransition.courseproject.service.item.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.itransition.courseproject.controller.ControllerUtils.ITEM_URI;


@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.DELETE, RequestMethod.GET,RequestMethod.PUT,RequestMethod.POST})
@RequestMapping(ITEM_URI)
public class ItemController extends CRUDController<ItemService, Long, ItemRequest> {

    protected ItemController(ItemService service) {
        super(service);
    }

    @GetMapping("/collection/{id}")
    public ResponseEntity<APIResponse> getItemsByCollectionId(@PathVariable Long id){
        return ResponseEntity.ok(service.getItemsByCollectionId(id));
    }

    @GetMapping("/tag/{id}")
    public ResponseEntity<APIResponse> getItemsByTagId(@PathVariable Long id){
        return ResponseEntity.ok(service.getItemsByTagId(id));
    }
    //// TODO: 05.07.2022 use paramRequest instead of path variable
    @GetMapping("/{user_id}/{item_id}")
    public ResponseEntity<APIResponse> getItemResponse(
            @PathVariable("user_id") Long userId,
            @PathVariable("item_id") Long itemId){
        return ResponseEntity.ok(service.getItemResponse(itemId, userId));
    }

    @GetMapping("/like/{user_id}/{item_id}")
    public ResponseEntity<APIResponse> updateItemLike(
            @PathVariable("user_id") Long userId,
            @PathVariable("item_id") Long itemId,
            @RequestParam(name = "isLiked") boolean isLiked){
        return ResponseEntity.ok(service.updateItemLike(userId, itemId, isLiked));
    }

    @GetMapping("/latest")
    public ResponseEntity<APIResponse> getLatestItems(){
        return ResponseEntity.ok(service.getLatestItems());
    }
}
