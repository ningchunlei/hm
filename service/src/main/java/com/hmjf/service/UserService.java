package com.hmjf.service;

import com.hmjf.dao.UserRepository;
import com.hmjf.domain.PageList;
import com.hmjf.entity.User;
import com.hmjf.mapper.UserMapper;
import com.hmjf.utils.CopyUtils;
import com.hmjf.utils.PageUtils;
import com.hmjf.utils.encrypt.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by jack on 16/1/2.
 */
@Service
public class UserService {

    static Logger logger = LoggerFactory.getLogger(UserService.class);

    private String salt = "234h5ladfhn4##@";

    @Resource
    UserRepository userRepository;
    @Resource
    UserMapper userMapper;

    public boolean registerUser(User user){
        user.password = MD5Util.md5Hex(user.password+"-"+salt);
        userRepository.save(user);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public User login(User user){
        List<User> list = userMapper.getUsers();
        String password = MD5Util.md5Hex(user.password+"-"+salt);
        User ret = userRepository.findByNameAndPassword(user.name,password);

        if(ret!=null){
            userRepository.updateLoginTime(ret.id,new Date());
        }
        return ret;
    }

    public User user(Long uid){
        User user = userRepository.findOne(uid);
        User ret = new User();
        CopyUtils.clone(user,ret,new Consumer<User>() {
            @Override
            public void accept(User xuser) {
                xuser.password = "";
            }
        });
        return ret;
    }


    public PageList<User> listUser(){
        Page<User> page = userRepository.findByRegisterIp("127.0.0.1",new PageRequest(0,1, Sort.Direction.DESC,"id"));
        return PageUtils.page(page,0,1);
    }

}
