package com.example.demo.service;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Bundle;
import com.example.demo.model.Dish;
import com.example.demo.repository.BundleRepository;
import com.example.demo.repository.DishRepository;


@Service
public class BundleService {

	@Autowired
	protected BundleRepository repo;
	@Autowired
	protected DishRepository dishRepo;
	
	public Bundle create(Bundle bundle) {
		return repo.save(bundle);
	}
	
	public List<Bundle> getAll(){
		return repo.findAll();
	}
	
	public Optional<Bundle> getById(Long id) {
		return repo.findById(id);
	}
	
	public boolean addDishById(Long bundleId, Long dishId) {
		Optional<Bundle> bundleRecord = repo.findById(bundleId);
		Optional<Dish> dishRecord = dishRepo.findById(dishId);
		if(bundleRecord.isPresent() && dishRecord.isPresent()) {
			Bundle bundle = bundleRecord.get();
			bundle.getBundleItems().add(dishRecord.get());
			repo.save(bundle);
	        return true;
		}
		return false;
	}
	
	public void update(Long id, Bundle oldBundle, Bundle newBundle) {
		oldBundle.setName(newBundle.getName());
		oldBundle.setDiscount(newBundle.getDiscount());
		repo.save(oldBundle);
	}
	
	public void delete(Bundle bundle) {
		repo.delete(bundle);
	}
}
