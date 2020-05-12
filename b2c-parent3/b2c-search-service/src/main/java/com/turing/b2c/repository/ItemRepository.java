package com.turing.b2c.repository;


import com.turing.b2c.model.pojo.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ItemRepository extends ElasticsearchRepository<Item,Long> {
}
