package com.hmjf.dao;

import com.hmjf.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by jack on 16/1/2.
 */

public interface UserRepository extends BaseRepository<User,Long>{

    User findByNameAndPassword(String name,String password);

    @Modifying(clearAutomatically = true)
    @Query(value = "update User  set lastLoginTime=?2 where id=?1")
    int updateLoginTime(Long userId, Date date);

    @Query("select id from User as w where name=?1")
    Page<User> getNameList(String name, Pageable pageable);

    Page<User> findByRegisterIp(String registerIp,Pageable pageable);

}
