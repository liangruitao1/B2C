package com.turing.b2c.sellergoods;


import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.turing.b2c.mapper.SpecificationOptionMapper;
import com.turing.b2c.mapper.TypeTemplateMapper;
import com.turing.b2c.model.dto.SearchParam;
import com.turing.b2c.model.dto.SearchResult;
import com.turing.b2c.model.pojo.*;
import com.turing.b2c.model.pojo.union.SpecificationUnion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
class TypeTemplateServiceImpl implements TypeTemplateService {
    @Autowired(required=false)
    private TypeTemplateMapper typeTemplateMapper;

    @Autowired
    private SpecificationOptionMapper specificationOptionMapper;

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Override
    public TypeTemplate findById(Long id) {
        return typeTemplateMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<TypeTemplate> findAll() {
        return typeTemplateMapper.selectByExample(null);
    }

    @Override
    public void save(TypeTemplate entity) {
        typeTemplateMapper.insertSelective(entity);
    }

    @Override
    public void update(TypeTemplate entity) {
        typeTemplateMapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public void delete(Long id) {
        typeTemplateMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void delete(Long[] ids) {
        TypeTemplateExample example = new TypeTemplateExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        typeTemplateMapper.deleteByExample(example);
    }

    @Override
    public SearchResult<TypeTemplate> findPage(SearchParam searchParam) {

        // 设置分页信息
        PageHelper.startPage(searchParam.getPageNum(), searchParam.getPageSize());
        // 后续有条件查询再根据情况添加example
        // 获取分页数据
        Page<TypeTemplate> page = (Page<TypeTemplate>) typeTemplateMapper.selectByExample(null);
        return new SearchResult<TypeTemplate>(page.getTotal(), page.getResult());
    }

    /**
     *
     */
    private void  cacheBrandListAndSpecUnionList(){
        List<TypeTemplate> list = findAll();
        list.forEach(typeTemplate -> {
            List<Brand> brandList = JSON.parseArray(typeTemplate.getBrandIds(),Brand.class);
            redisTemplate.opsForHash().put("brandList",typeTemplate.getId(),brandList);
            List<SpecificationUnion > specificationUnionList = findSpecUnionsById(typeTemplate.getId());
            redisTemplate.opsForHash().put("specificationUnion",typeTemplate.getId(),specificationUnionList);

         });
        System.out.println("缓存：类型模板id->品牌列表 / 类型模板id->规格列表");
    }

    @Override
    public List<SpecificationUnion> findSpecUnionsById(Long id) {
        List<SpecificationUnion> specificationUnions=new ArrayList<>();
        // 根据typeTemplateId查询模板
        TypeTemplate typeTemplate = typeTemplateMapper.selectByPrimaryKey(id);
        List<Specification> specs =null;

            specs =(List<Specification>) JSON.parseArray(typeTemplate.getSpecIds(),Specification.class);
            System.out.println(specs.get(0).getId());
            specs.forEach(spec -> {
                SpecificationOptionExample example = new SpecificationOptionExample();
                example.createCriteria().andSpecIdEqualTo(spec.getId());
                List<SpecificationOption> specOptionList = specificationOptionMapper.selectByExample(example);
                specificationUnions.add(new SpecificationUnion(spec,specOptionList));
            });
        return specificationUnions;
    }
}
