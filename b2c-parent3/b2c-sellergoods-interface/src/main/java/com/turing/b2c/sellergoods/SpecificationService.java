package com.turing.b2c.sellergoods;

import com.turing.b2c.model.dto.SearchParam;
import com.turing.b2c.model.dto.SearchResult;
import com.turing.b2c.model.pojo.Specification;
import com.turing.b2c.model.pojo.union.SpecificationUnion;

import java.util.List;

public interface SpecificationService {
    Specification findById(Long id);

    List<Specification> findAll();

    void save(Specification entity);

    void update(Specification entity);

    void delete(Long id);

    void delete(Long[] ids);

    SearchResult<Specification> findPage(SearchParam searchParam);

    //新增联合对象的方法
    SpecificationUnion findUnionById(Long id);

    void saveUnion(SpecificationUnion specUnion);

    void updateUnion(SpecificationUnion specUnion);
}
