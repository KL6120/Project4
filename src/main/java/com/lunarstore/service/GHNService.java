package com.lunarstore.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GHNService {
	String token = "104ba9da-1130-11f1-a3d6-dac90fb956b5";
	Integer shopId = 199387;

	@Autowired
	RestTemplate restTemplate;

	public Integer getShippingFee(int toDistrictId, String toWardCode) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Token", token);
		headers.set("ShopId", String.valueOf(shopId));
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, Object> body = Map.of("from_district_id", 1457, "service_type_id", 2, "to_district_id",
				toDistrictId, "height", 10, "length", 20, "weight", 200, "width", 20);

		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
		ResponseEntity<Map> response = restTemplate.postForEntity(
				"https://dev-online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/fee", entity, Map.class);
		Map<String, Object> data = (Map<String, Object>) response.getBody().get("data");
		return (Integer) data.get("total");

	}
}
