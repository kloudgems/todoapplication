package com.application.todoapplication.controller;

import com.application.todoapplication.dto.ItemPatchDto;
import com.application.todoapplication.dto.ItemPostDto;
import com.application.todoapplication.entities.Item;
import com.application.todoapplication.services.ItemService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Api(
        tags = {"Item Controller"},
        description = " "
)
@RestController
@RequestMapping( value = "/api/item", produces = MediaType.APPLICATION_JSON_VALUE )
public class ItemController {
    @Autowired
    ItemService itemService;
    @GetMapping("")
    public List<Item> getAll(Principal user){
        return itemService.findAll(user);
    }
    @PostMapping("")
    public Item create(@RequestBody ItemPostDto itemPostDto, Principal user){
        return itemService.create(itemPostDto,user);
    }
    @PatchMapping(value = "/{itemId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Item create(@PathVariable Long itemId, @RequestBody ItemPatchDto itemPatchDto){
        return itemService.update(itemId,itemPatchDto);
    }
    @DeleteMapping("/{itemId}")
    public ResponseEntity<Object> delete(@PathVariable Long itemId){
        return itemService.delete(itemId);
    }
}
