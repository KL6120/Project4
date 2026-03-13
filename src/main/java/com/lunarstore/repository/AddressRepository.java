package com.lunarstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lunarstore.model.Address;

public interface AddressRepository extends JpaRepository<Address, Integer>{

}
