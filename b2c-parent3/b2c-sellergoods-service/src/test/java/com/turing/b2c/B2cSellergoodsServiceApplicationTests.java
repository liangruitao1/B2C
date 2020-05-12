package com.turing.b2c;

import com.turing.b2c.model.dto.SearchParam;
import com.turing.b2c.sellergoods.ItemCatService;
import com.turing.b2c.sellergoods.TypeTemplateService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {B2cSellergoodsServiceApplication.class})
class B2cSellergoodsServiceApplicationTests {

    @Autowired(required = false)
    private ItemCatService itemCatService;
    @Autowired(required = false)
    private TypeTemplateService typeTemplateService;

    @Test
    public void contextLoads() throws Exception{

    }
    @Test
    public void testCacheTypeTemplateId() {
        itemCatService.findByParentId(0L);
    }

    @Test
    public void testCacheBrandListAndSpecUnionList() {
        SearchParam searchParam = new SearchParam(1, 4);
        typeTemplateService.findPage(searchParam);
    }
}
