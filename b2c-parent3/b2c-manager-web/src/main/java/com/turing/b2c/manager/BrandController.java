package com.turing.b2c.manager;

import com.alibaba.dubbo.config.annotation.Reference;
import com.turing.b2c.model.dto.MsgBox;
import com.turing.b2c.model.dto.SearchParam;
import com.turing.b2c.model.dto.SearchResult;
import com.turing.b2c.model.pojo.Brand;
import com.turing.b2c.sellergoods.BrandService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager")
public class BrandController {

    @Reference(timeout = 5000)
    private BrandService brandService;
    @GetMapping("/brand/{id}")
    public Brand findById(@PathVariable("id") Long id) {
        return brandService.findById(id);
    }

    @PostMapping("/brand")
    public MsgBox save(@RequestBody Brand entity) {
        try {
            brandService.save(entity);
            return new MsgBox(true, "保存成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new MsgBox(false, "保存失败！");
        }
    }

    @PutMapping("/brand")
    public MsgBox update(@RequestBody Brand entity) {
        try {
            brandService.update(entity);
            return new MsgBox(true, "修改成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new MsgBox(false, "修改失败！");
        }
    }

    @DeleteMapping("/brand/{ids}")
    public MsgBox delete(@PathVariable("ids") Long[] ids) {
        try {
            brandService.delete(ids);
            return new MsgBox(true, "删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new MsgBox(false, "删除失败！");
        }
    }

    @GetMapping("/brands")
    public SearchResult<Brand> findPage(SearchParam searchParam) {

        return brandService.findPage(searchParam);
    }
    @GetMapping("brands2")
    public List<Brand> find(){
        return  brandService.findAll();
    }
}
