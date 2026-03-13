package com.lunarstore.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lunarstore.model.Category;
import com.lunarstore.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{
	public List<Product> findByActive(Boolean active);
	public Page<Product> findByActive(Boolean active, Pageable pageable);
	
	@Query("Select p from Product p where p.active = true and p.name like :keyword")
	public Page<Product> findByKeyword(@Param("keyword")String keyword, Pageable pageable);
	
	public Product findByActiveAndSlug(Boolean active, String slug);
	public List<Product> findByActiveAndCategory(Boolean active, Category category, Sort sort);
}
