package com.binyougou;

import com.pinyougou.mapper.UserMapper;
import com.pinyougou.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

/**
 * @program:PinYouGou01
 * @description:测试类
 * @author:Mr.lu
 * @create:2019-07-09 20:41
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/applicationContext-*.xml")
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testGetAllUser(){
        //查询所有品牌
        List<User> select = userMapper.select(null);
        for (User user : select) {
            System.out.println(user);
        }
    }

    /***
     * 插入数据
     */
    @Test
    public void testInsert(){
        final User user = new User();
        user.setUsername("风一般的男子");
        user.setAddress("湖北黄冈");
        user.setBirthday(new Date());

        userMapper.insert(user);
    }

    /***
     * 根据主键修改数据
     */
    @Test
    public void testUpdate(){
        final User user = new User();
        user.setId(37);
        user.setAddress("宝安中心");
        userMapper.updateByPrimaryKey(user);
    }
}

