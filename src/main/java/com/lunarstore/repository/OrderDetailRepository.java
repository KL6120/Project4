package com.lunarstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lunarstore.model.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer>{

}
