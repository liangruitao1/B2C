package com.turing.b2c.Controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.turing.b2c.content.service.ContentService;
import com.turing.b2c.model.dto.MsgBox;
import com.turing.b2c.model.dto.SearchParam;
import com.turing.b2c.model.dto.SearchResult;
import com.turing.b2c.model.pojo.Content;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/portal")
public class ContentController {
    @Reference(timeout = 5000)
    private ContentService contentService;

    @GetMapping("/content/{id}")
    public Content findById(@PathVariable("id") Long id) {
        return contentService.findById(id);
    }

    @PostMapping("/content")
    public MsgBox save(@RequestBody Content entity) {
        try {
            contentService.save(entity);
            return new MsgBox(true, "保存成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new MsgBox(false, "保存失败！");
        }
    }

    @PutMapping("/content")
    public MsgBox update(@RequestBody Content entity) {
        try {
            contentService.update(entity);
            return new MsgBox(true, "修改成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new MsgBox(false, "修改失败！");
        }
    }

    @DeleteMapping("/content/{ids}")
    public MsgBox delete(@PathVariable("ids") Long[] ids) {
        try {
            contentService.delete(ids);
            return new MsgBox(true, "删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new MsgBox(false, "删除失败！");
        }
    }

    @GetMapping("/contents")
    public SearchResult<Content> findPage(SearchParam searchParam) {

        return contentService.findPage(searchParam);
    }
    @GetMapping("/contents2")
    public List<Content> find(){
        return  contentService.findAll();
    }

    @GetMapping("/contents/{categoryId}")
    public List<Content> findByCatedorgoryId(@PathVariable("categoryId")Long categoryId){

        return  contentService.findByCategoryId(categoryId);
    }


}
