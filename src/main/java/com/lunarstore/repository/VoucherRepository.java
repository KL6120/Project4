package com.lunarstore.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lunarstore.model.Voucher;

public interface VoucherRepository extends JpaRepository<Voucher, Integer> {
	Voucher findByCode(String code);

	@Query("Select v from Voucher v "
			+ "where v.actived = true and "
			+ "v.startedAt <= CURRENT_TIMESTAMP and "
			+ "v.endAt >= CURRENT_TIMESTAMP and "
			+ "v.quantity > 0 and "
			+ "v.id not in :ids")
	List<Voucher> findVoucherValidList(@Param("ids") List<Integer> ids, Sort sort);
}
