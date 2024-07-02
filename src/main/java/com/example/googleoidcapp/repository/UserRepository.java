package com.example.googleoidcapp.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;

@Mapper
public interface UserRepository {

    @Insert("""
            INSERT INTO users (username)
            VALUES (#{username})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(UserRecord record);

    @Select("""
            SELECT
                u.id as id
              , u.username as username
            FROM users u
            JOIN user_social_logins usl ON u.id = usl.user_id
            WHERE
                usl.subject = #{subject}
            """)
    Optional<UserRecord> selectBySubject(String subject);


}
