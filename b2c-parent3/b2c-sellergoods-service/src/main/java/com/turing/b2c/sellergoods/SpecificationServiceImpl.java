package com.turing.b2c.sellergoods;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.turing.b2c.mapper.SpecificationMapper;
import com.turing.b2c.mapper.SpecificationOptionMapper;
import com.turing.b2c.model.dto.SearchParam;
import com.turing.b2c.model.dto.SearchResult;
import com.turing.b2c.model.pojo.Specification;
import com.turing.b2c.model.pojo.SpecificationExample;
import com.turing.b2c.model.pojo.SpecificationOption;
import com.turing.b2c.model.pojo.SpecificationOptionExample;
import com.turing.b2c.model.pojo.union.SpecificationUnion;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

@Service
public class SpecificationServiceImpl implements SpecificationService {
    @Autowired
    private SpecificationMapper specificationMapper;
    @Autowired
    private SpecificationOptionMapper specificationOptionMapper;

    @Override
    public Specification findById(Long id) {
        return specificationMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Specification> findAll() {
        return specificationMapper.selectByExample(null);
    }

    @Override
    public void save(Specification entity) {
        specificationMapper.insert(entity);
    }

    @Override
    public void update(Specification entity) {
        specificationMapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public void delete(Long id) {
        specificationMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void delete(Long[] ids) {
        //删除规格明细
        SpecificationOptionExample ex = new SpecificationOptionExample();
        ex.createCriteria().andSpecIdIn(Arrays.asList(ids));
        specificationOptionMapper.deleteByExample(ex);
        //删除规格
        SpecificationExample example = new SpecificationExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        specificationMapper.deleteByExample(example);
    }

    @Override
    public SearchResult<Specification> findPage(SearchParam searchParam) {
        if(searchParam!=null){
            //使用分页插件，初始化
            PageHelper.startPage(searchParam.getPageNum(), searchParam.getPageSize());
            //查询
            Page<Specification> page = (Page<Specification>) specificationMapper.selectByExample(null);
            return new SearchResult<>(page.getTotal(), page.getResult());
        }else{
            PageHelper.startPage(1, 4);
            //查询
            Page<Specification> page = (Page<Specification>) specificationMapper.selectByExample(null);
            return new SearchResult<>(page.getTotal(), page.getResult());
        }

    }

    @Override
    public SpecificationUnion findUnionById(Long id) {
        //根据id查询规格
        Specification spec = specificationMapper.selectByPrimaryKey(id);
        //根据规格查询对应的规格明细列表
        SpecificationOptionExample example = new SpecificationOptionExample();
        example.createCriteria().andSpecIdEqualTo(spec.getId());
        List<SpecificationOption> specOptionList = specificationOptionMapper.selectByExample(example);
        //封装并返回
        return new SpecificationUnion(spec, specOptionList);
    }

    @Override
    public void saveUnion(SpecificationUnion specUnion) {
        //通过联合对象，获取规格对象
        Specification spec = specUnion.getSpec();
        //保存规格对象
        specificationMapper.insertSelective(spec);

        //通过联合对象，获取规格明细对象
        List<SpecificationOption> specOptionList = specUnion.getSpecOptionList();
        //循环
        for (SpecificationOption specOption : specOptionList) {
            //给规格明细设置规格id(设置外键的值)
            specOption.setSpecId(spec.getId());
            //保存规格明细对象
            specificationOptionMapper.insertSelective(specOption);
        }
    }

    @Override
    public void updateUnion(SpecificationUnion specUnion) {
        //通过联合对象，获取规格对象
        Specification spec = specUnion.getSpec();
        //修改规格对象
        specificationMapper.updateByPrimaryKeySelective(spec);

        //先删除
        SpecificationOptionExample example = new SpecificationOptionExample();
        example.createCriteria().andSpecIdEqualTo(spec.getId());
        specificationOptionMapper.deleteByExample(example);

        //再保存
        //通过联合对象，获取规格明细对象
        List<SpecificationOption> specOptionList = specUnion.getSpecOptionList();
        //循环
        for (SpecificationOption specOption : specOptionList) {
            //给规格明细设置规格id(设置外键的值)
            specOption.setSpecId(spec.getId());
            //保存规格明细对象
            specificationOptionMapper.insertSelective(specOption);
        }
    }
}
