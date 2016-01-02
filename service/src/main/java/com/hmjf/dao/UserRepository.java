package com.hmjf.dao;

import com.hmjf.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

/**
 * Created by jack on 16/1/2.
 */

public interface UserRepository extends BaseRepository<User,Long>{

    User findByNameAndPassword(String name,String password);

    @Modifying(clearAutomatically = true)
    @Query(value = "update User  set lastLoginTime=?2 where id=?1")
    int updateLoginTime(Long userId, Date date);

}
