package com.turing.b2c.seller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.turing.b2c.model.dto.MsgBox;
import com.turing.b2c.model.dto.SearchParam;
import com.turing.b2c.model.dto.SearchResult;
import com.turing.b2c.model.pojo.Goods;
import com.turing.b2c.sellergoods.GoodsService;
import com.turing.b2c.utils.FastDFSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/seller")
public class goodsController {
    @Reference(timeout = 10000)
    private GoodsService goodService;

    @Value("${file.server.path}")
    private  String fileServerPath;
    @GetMapping("/good/{id}")
    public Goods findById(@PathVariable("id") Long id) {
        return goodService.findById(id);
    }

    @PostMapping("/upload")
    public MsgBox save(@RequestParam("pic") MultipartFile pic) {
        try {
            FastDFSClient client = new FastDFSClient("fdfs_client.conf");
            String fileId = fileServerPath + client.uploadFile(pic.getBytes(),"jpg");
            return  new MsgBox(true,fileId);
        } catch (Exception e) {
            e.printStackTrace();
            return new MsgBox(false, "保存失败！");
        }
    }

    @PutMapping("/good")
    public MsgBox update(@RequestBody Goods entity) {
        try {
            goodService.update(entity);
            return new MsgBox(true, "修改成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new MsgBox(false, "修改失败！");
        }
    }

    @DeleteMapping("/good/{ids}")
    public MsgBox delete(@PathVariable("ids") Long[] ids) {
        try {
            goodService.delete(ids);
            return new MsgBox(true, "删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new MsgBox(false, "删除失败！");
        }
    }

    @GetMapping("/goods")
    public SearchResult<Goods> findPage(SearchParam searchParam) {

        return goodService.findPage(searchParam);
    }
}
