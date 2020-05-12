package com.turing.b2c.controller;

import com.turing.b2c.model.dto.MsgBox;
import com.turing.b2c.model.dto.SearchParam;
import com.turing.b2c.model.dto.SearchResult;
import com.turing.b2c.model.pojo.Item;
import com.turing.b2c.search.ItemService;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/search")
public class ItemController  {

    @Reference
    private ItemService itemService;

    @GetMapping("/item/{id}")
    public Item findById(@PathVariable("id") Long id) {
        return itemService.findById(id);
    }

    @PostMapping("/item")
    public MsgBox save(@RequestBody Item entity) {
        try {
            itemService.save(entity);
            return new MsgBox(true, "保存成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new MsgBox(false, "保存失败！");
        }
    }

    @PutMapping("/item")
    public MsgBox update(@RequestBody Item entity) {
        try {
            itemService.update(entity);
            return new MsgBox(true, "修改成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new MsgBox(false, "修改失败！");
        }
    }

    @DeleteMapping("/item/{ids}")
    public MsgBox delete(@PathVariable("ids") Long[] ids) {
        try {
            itemService.delete(ids);
            return new MsgBox(true, "删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new MsgBox(false, "删除失败！");
        }
    }


    @GetMapping("/items")
    public SearchResult<Item> findPage(SearchParam searchParam){
        System.out.println(searchParam.getPageNum()+""+searchParam.getPageSize());
         return  itemService.findPage(searchParam);
    }
}
