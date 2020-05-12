package com.turing.b2c.manager;

import com.alibaba.dubbo.config.annotation.Reference;
import com.turing.b2c.model.dto.MsgBox;
import com.turing.b2c.model.dto.SearchParam;
import com.turing.b2c.model.dto.SearchResult;
import com.turing.b2c.model.pojo.TypeTemplate;
import com.turing.b2c.sellergoods.TypeTemplateService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manager")
public class TypeTemplateController {

    @Reference(timeout = 5000)
    private TypeTemplateService typeTemplateService;
    @GetMapping("/typeTemplate/{id}")
    public TypeTemplate findById(@PathVariable("id") Long id) {
        return typeTemplateService.findById(id);
    }

    @PostMapping("/typeTemplate")
    public MsgBox save(@RequestBody TypeTemplate entity) {
        try {
            typeTemplateService.save(entity);
            return new MsgBox(true, "保存成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new MsgBox(false, "保存失败！");
        }
    }

    @PutMapping("/typeTemplate")
    public MsgBox update(@RequestBody TypeTemplate entity) {
        try {
            typeTemplateService.update(entity);
            return new MsgBox(true, "修改成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new MsgBox(false, "修改失败！");
        }
    }

    @DeleteMapping("/typeTemplate/{ids}")
    public MsgBox delete(@PathVariable("ids") Long[] ids) {
        try {
            typeTemplateService.delete(ids);
            return new MsgBox(true, "删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new MsgBox(false, "删除失败！");
        }
    }

    @GetMapping("/typeTemplates")
    public SearchResult<TypeTemplate> findPage(SearchParam searchParam) {
        return typeTemplateService.findPage(searchParam);
    }
}
