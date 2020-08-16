package com.application.todoapplication.services;

import com.application.todoapplication.dto.ItemPatchDto;
import com.application.todoapplication.dto.ItemPostDto;
import com.application.todoapplication.entities.Item;
import com.application.todoapplication.repos.ItemRepo;
import com.application.todoapplication.repos.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;

@Transactional
@AllArgsConstructor
@Service
@Slf4j
public class ItemService {
    @Autowired
    ItemRepo itemRepo;
    @Autowired
    UserRepository userRepository;
    public List<Item> findAll(Principal principal) {
         return  itemRepo.findAllByUserId(getUserId(principal));
    }
    public Item getOne(Long id) {
        return itemRepo.getOne(id);
    }
    public Item create(ItemPostDto itemPostDto, Principal principal){
        return  itemRepo.save(toItem(itemPostDto,principal));
    }
    public Item update(Long id, ItemPatchDto itemPatchDto){
        Item item = itemRepo.getOne(id);
        item.setName(itemPatchDto.getName());
        item.setStatus(itemPatchDto.getStatus());
        return  itemRepo.save(item);
    }
    public ResponseEntity<Object> delete(Long id){
        itemRepo.deleteById(id);
        return  ResponseEntity.noContent().build();
    }
    private Item toItem(ItemPostDto itemPostDto, Principal principal) {
        Item item = new Item();
        item.setName(itemPostDto.getName());
        item.setUser(userRepository.getOne(getUserId(principal)));
        return item;
    }

    private Long getUserId(Principal user){
       return userRepository.findByUsername(user.getName()).getId();
    }
}
