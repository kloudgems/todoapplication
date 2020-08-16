package com.application.todoapplication.repos;

import com.application.todoapplication.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepo extends JpaRepository<Item,Long> {

    List<Item> findAllByUserId(Long userId);
}
