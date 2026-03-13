package com.lunarstore.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lunarstore.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{
	public List<Category> findByActive(Boolean active, Sort sort);
	public Category findByActiveAndSlug(Boolean active, String slug);
}
