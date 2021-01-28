package com.anyu.test.common;


import com.anyu.common.util.SensitiveFilter;
import com.anyu.test.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class SensitiveFilterTest extends BaseTest {
    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Test
    void filter() {
        var contents = new String[]{
                "张三 吸 毒嫖-娼，还赌博",
                "张三 吸毒 嫖娼，还赌**博",
                "张三 吸，毒嫖 娼，还赌博",
                "张三 吸毒发给嫖娼，还赌博",
                "张三 吸毒嫖娼，还赌博",

        };
        for (String content : contents) {
            String filter = sensitiveFilter.filter(content);
            System.out.println(filter);
        }
    }

}
