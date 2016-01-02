package com.hmjf.service;

import com.hmjf.dao.UserRepository;
import com.hmjf.entity.User;
import com.hmjf.utils.CopyUtils;
import com.hmjf.utils.encrypt.MD5Util;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.function.Consumer;

/**
 * Created by jack on 16/1/2.
 */
@Service
public class UserService {

    private String salt = "234h5ladfhn4##@";

    @Resource
    UserRepository userRepository;

    public boolean registerUser(User user){
        user.password = MD5Util.md5Hex(user.password+"-"+salt);
        userRepository.save(user);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public User login(User user){
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


}
