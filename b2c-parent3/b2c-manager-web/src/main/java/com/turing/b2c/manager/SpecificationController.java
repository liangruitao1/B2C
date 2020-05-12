package com.turing.b2c.manager;

import com.alibaba.dubbo.config.annotation.Reference;
import com.turing.b2c.model.dto.MsgBox;
import com.turing.b2c.model.dto.SearchParam;
import com.turing.b2c.model.dto.SearchResult;
import com.turing.b2c.model.pojo.Specification;
import com.turing.b2c.model.pojo.union.SpecificationUnion;
import com.turing.b2c.sellergoods.SpecificationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager")
public class SpecificationController {

    @Reference
    private SpecificationService specificationService;

    //分页查询所有
    @GetMapping("/specifications")
    public SearchResult<Specification> findAll(SearchParam searchParam) {
        return specificationService.findPage(searchParam);
    }

    //查询单个
    @GetMapping("/specification/{id}")
    public SpecificationUnion findById(@PathVariable("id") Long id) {
        return specificationService.findUnionById(id);
    }

    //添加
    @PostMapping("/specification")
    public MsgBox save(@RequestBody SpecificationUnion specUnion) {
        try {
            specificationService.saveUnion(specUnion);
            return new MsgBox(true, "保存成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new MsgBox(false, "保存失败！");
        }
    }

    //修改
    @PutMapping("/specification")
    public MsgBox update(@RequestBody SpecificationUnion specUnion) {
        try {
            specificationService.updateUnion(specUnion);
            return new MsgBox(true, "修改成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new MsgBox(false, "修改失败！");
        }
    }

    //删除
    @DeleteMapping("/specification/{ids}")
    public MsgBox delete(@PathVariable Long[] ids) {
        try {
            specificationService.delete(ids);
            return new MsgBox(true, "删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new MsgBox(false, "删除失败！");
        }
    }
    @GetMapping("specifications2")
    public List<Specification> findAll(){
        return  specificationService.findAll();
    }
}
