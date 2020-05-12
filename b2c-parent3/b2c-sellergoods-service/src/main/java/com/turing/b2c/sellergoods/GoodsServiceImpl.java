package com.turing.b2c.sellergoods;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.turing.b2c.mapper.GoodsMapper;
import com.turing.b2c.model.dto.SearchParam;
import com.turing.b2c.model.dto.SearchResult;
import com.turing.b2c.model.pojo.Goods;
import com.turing.b2c.model.pojo.GoodsExample;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

public class GoodsServiceImpl implements GoodsService{
    @Autowired(required=false)
    private GoodsMapper goodsMapper;

    @Override
    public Goods findById(Long id) {
        return goodsMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Goods> findAll() {
        return goodsMapper.selectByExample(null);
    }

    @Override
    public void save(Goods entity) {
        goodsMapper.insert(entity);
    }

    @Override
    public void update(Goods entity) {
        goodsMapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public void delete(Long id) {
        goodsMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void delete(Long[] ids) {
        GoodsExample example = new GoodsExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        goodsMapper.deleteByExample(example);
    }

    @Override
    public SearchResult<Goods> findPage(SearchParam searchParam) {

        // 设置分页信息
        PageHelper.startPage(searchParam.getPageNum(), searchParam.getPageSize());
        // 后续有条件查询再根据情况添加example
        // 获取分页数据
        Page<Goods> page = (Page<Goods>) goodsMapper.selectByExample(null);
        return new SearchResult<Goods>(page.getTotal(), page.getResult());
    }
}
