package com.dao;

import com.entity.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by User on 2017/3/30.
 */

public interface TestEntityDao extends JpaRepository<TestEntity,Long> {
    @Query("select te from TestEntity te")
    List<TestEntity> findAll();
}
