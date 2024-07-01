package com.example.googleoidcapp.repository;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;

@Mapper
public interface UserSocialLoginRepository {
    @Select("""
            SELECT
                id
              , user_id
              , subject
            FROM user_social_logins
            WHERE
              subject = #{subject}
            """)
    Optional<UserSocialLogin> selectBySubject(String subject);

    @Insert("""
            INSERT INTO user_social_logins (user_id, provider, subject)
            VALUES (#{userId}, #{provider}, #{subject})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(UserSocialLoginRecord newUserSocialLogin);
}
