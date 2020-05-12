package com.turing.b2c.content.service;

import com.turing.b2c.model.dto.SearchParam;
import com.turing.b2c.model.dto.SearchResult;
import com.turing.b2c.model.pojo.Content;

import java.util.List;

public interface ContentService {

    Content findById(Long id);

    List<Content> findAll();

    void save(Content entity);

    void update(Content entity);

    void delete(Long id);

    void delete(Long[] ids);

    SearchResult<Content> findPage(SearchParam searchParam);

    List<Content> findByCategoryId(Long categoryId);
}
