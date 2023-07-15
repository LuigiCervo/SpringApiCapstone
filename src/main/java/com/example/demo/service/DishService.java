package com.example.demo.service;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Dish;
import com.example.demo.repository.DishRepository;


@Service
public class DishService {

	@Autowired
	protected DishRepository repo;
	
	public Dish create(Dish dish) {
		return repo.save(dish);
	}
	
	public List<Dish> getAll(){
		return repo.findAll();
	}
	
	public Optional<Dish> getById(Long id) {
		return repo.findById(id);
	}
	
	public void update(Long id, Dish oldDish, Dish newDish) {
		oldDish.setName(newDish.getName());
		oldDish.setDescription(newDish.getDescription());
		oldDish.setImage(newDish.getImage());
		oldDish.setManufacturer(newDish.getManufacturer());
		oldDish.setPrice(newDish.getPrice());
		repo.save(oldDish);
	}
	
	public void delete(Dish dish) {
		repo.delete(dish);
	}
}
