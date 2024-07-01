package com.example.googleoidcapp.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface UserRepository {

    @Insert("""
            INSERT INTO users (username)
            VALUES (#{username})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(UserRecord record);


}
