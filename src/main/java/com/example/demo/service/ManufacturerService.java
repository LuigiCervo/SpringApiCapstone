package com.example.demo.service;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Manufacturer;
import com.example.demo.repository.ManufacturerRepository;


@Service
public class ManufacturerService {

	@Autowired
	protected ManufacturerRepository repo;
	
	public Manufacturer create(Manufacturer manufacturer) {
		return repo.save(manufacturer);
	}
	
	public List<Manufacturer> getAll(){
		return repo.findAll();
	}
	
	public Optional<Manufacturer> getById(Long id) {
		return repo.findById(id);
	}
	
	public void update(Long id, Manufacturer oldManufacturer, Manufacturer newManufacturer) {
		oldManufacturer.setName(newManufacturer.getName());
		oldManufacturer.setDescription(newManufacturer.getDescription());
		oldManufacturer.setImage(newManufacturer.getImage());
		repo.save(oldManufacturer);
	}
	
	public void delete(Manufacturer manufacturer) {
		repo.delete(manufacturer);
	}
}
