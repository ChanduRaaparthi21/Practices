package com.chandu.Spring_data_jpa.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chandu.Spring_data_jpa.Entity.Product;
import com.chandu.Spring_data_jpa.Repository.ProductDetailsRepository;

@Component
public class ProductService {
	
	@Autowired
	 ProductDetailsRepository detailsRepository;
	
//	@Autowired
//	 Product product;
	
	public String addProduct() {
		
		Product product = new Product();
		
		product.setPid(102);
		product.setPname("Xiomi");
		product.setPrice(35000);
	detailsRepository.save(product);
	
	return "saved";
		
	}
	

}
