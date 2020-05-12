package com.turing.b2c.content.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.turing.b2c.content.service.ContentService;
import com.turing.b2c.mapper.ContentMapper;
import com.turing.b2c.model.dto.SearchParam;
import com.turing.b2c.model.dto.SearchResult;
import com.turing.b2c.model.pojo.Content;
import com.turing.b2c.model.pojo.ContentExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    private ContentMapper contentMapper;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;


    @Override
    public void save(Content entity) {
        contentMapper.insertSelective(entity);
        redisTemplate.opsForHash().delete("contentList", entity.getCategoryId());
    }

    @Override
    public void update(Content entity) {
        // 清除原分类列表
        Content content = contentMapper.selectByPrimaryKey(entity.getId());
        redisTemplate.opsForHash().delete("contentList", content.getCategoryId());

        contentMapper.updateByPrimaryKeySelective(entity);

        // 清除现分类列表
        if (!content.getCategoryId().equals(entity.getCategoryId())) {
            redisTemplate.opsForHash().delete("contentList", entity.getCategoryId());
        }
    }

    @Override
    public void delete(Long id) {
        Content content = contentMapper.selectByPrimaryKey(id);
        redisTemplate.opsForHash().delete("contentList", content.getCategoryId());
        contentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            Content content = contentMapper.selectByPrimaryKey(id);
            redisTemplate.opsForHash().delete("contentList", content.getCategoryId());
        }
        ContentExample example = new ContentExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        contentMapper.deleteByExample(example);
    }

    @Override
    public Content findById(Long id) {
        return contentMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Content> findAll() {
        return contentMapper.selectByExample(null);
    }

    @Override
    public SearchResult<Content> findPage(SearchParam searchParam) {

        // 设置分页信息
        PageHelper.startPage(searchParam.getPageNum(), searchParam.getPageSize());
        // 后续有条件查询再根据情况添加example
        // 获取分页数据
        Page<Content> page = (Page<Content>) contentMapper.selectByExample(null);
        return new SearchResult<Content>(page.getTotal(), page.getResult());
    }

    @Cacheable(cacheNames = "contentList")
    @Override
    public List<Content> findByCategoryId(Long categoryId) {
        System.out.println("findByCategoryId......");
        ContentExample example = new ContentExample();
        example.createCriteria().andCategoryIdEqualTo(categoryId).andStatusEqualTo("1");
        example.setOrderByClause("sort_order");
        return contentMapper.selectByExample(example);
    }
}
