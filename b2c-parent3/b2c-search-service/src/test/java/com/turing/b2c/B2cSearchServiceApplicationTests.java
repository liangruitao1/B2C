package com.turing.b2c;

import com.turing.b2c.search.ItemService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//当前测试类就当成Web层
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {B2cSearchServiceApplication.class})
class B2cSearchServiceApplicationTests {

    @Autowired
    private ItemService itemService;

    @Test
    void contextLoads() {
        itemService.index();
    }

}
