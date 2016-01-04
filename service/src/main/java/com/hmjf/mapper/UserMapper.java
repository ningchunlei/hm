package com.hmjf.mapper;

import com.hmjf.entity.Address;
import com.hmjf.entity.User;
import com.hmjf.framework.MyBatisRepository;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by user on 16/1/4.
 */
@MyBatisRepository
public interface UserMapper {


    @Select("select user.*,address.address as ad_address,address.uid as ad_uid from user,address where user.id=address.uid")
    @ResultMap({"userAndAddress"})
    List<User> getUsers();

}
