package com.turing.b2c.search;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.turing.b2c.mapper.ItemMapper;
import com.turing.b2c.model.dto.SearchParam;
import com.turing.b2c.model.dto.SearchResult;
import com.turing.b2c.model.pojo.Item;
import com.turing.b2c.model.pojo.ItemExample;
import com.turing.b2c.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Override
    public Item findById(Long id) {
        return itemMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Item> findAll() {
        return itemMapper.selectByExample(null);
    }

    @Override
    public void save(Item entity) {
        itemMapper.insert(entity);
    }

    @Override
    public void update(Item entity) {
        itemMapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public void delete(Long id) {
        itemMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void delete(Long[] ids) {
        ItemExample example = new ItemExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        itemMapper.deleteByExample(example);
    }

    @Override
    public SearchResult<Item> findPage(SearchParam searchParam) {
        SearchResult<Item> result = new SearchResult<>();

        //使用分页插件，初始化
        PageHelper.startPage(searchParam.getPageNum(), searchParam.getPageSize());
        //查询
        Page<Item> page = (Page<Item>) itemMapper.selectByExample(null);
        return new SearchResult<>(page.getTotal(), page.getResult());
    }

    @Override
    public void index() {
        //把数据中的所有Item对象查询出来
        List<Item> items = itemMapper.selectByExample(null);
        items.forEach(item -> {
            //处理JSON字段
            item.setSpecMap(JSON.parseObject(item.getSpec(), Map.class));
            //循环放到ES中去
            itemRepository.index(item);//类似于Put方法
        });
    }
}
