package com.ygnapi.ygn.repository;

import com.ygnapi.ygn.model.CStores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CStoresRepo extends JpaRepository<CStores,Long> {

    List<CStores>  findByUserId(String userId);
}
