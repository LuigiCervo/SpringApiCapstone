package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.model.Bundle;
import com.example.demo.service.BundleService;


@CrossOrigin("*")
@RestController
@RequestMapping("/api/bundle")
public class BundleController {
	
	@Autowired
	private BundleService service;
	
	@PostMapping("/")
	public ResponseEntity<?> create(@RequestBody Bundle bundle) {
		Bundle createdBundle = service.create(bundle);
		return ResponseEntity.
			created(ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(createdBundle.getId())
				.toUri())
		.build();
	}

	@PostMapping("/{bundleId}/add/{dishId}")
	public ResponseEntity<?> addDish(@PathVariable Long bundleId, @PathVariable Long dishId){
		if(service.addDishById(bundleId, dishId)) {
			return new ResponseEntity<>(HttpStatusCode.valueOf(200));
		}
		return new ResponseEntity<>(HttpStatusCode.valueOf(404));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Bundle> getById(@PathVariable Long id) {
		Optional<Bundle> record = service.getById(id);
		if (record.isPresent()) {
			return ResponseEntity.ok(record.get());
		}
		return new ResponseEntity<>(HttpStatusCode.valueOf(404));
		
	}

	@GetMapping("/list")
	public List<Bundle> getAll() {
		return service.getAll();
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Bundle bundle){
		Optional<Bundle> record = service.getById(id);
		if (record.isPresent()) {
			service.update(id, record.get(), bundle);
			return new ResponseEntity<>(HttpStatusCode.valueOf(200));
		}
		return new ResponseEntity<>(HttpStatusCode.valueOf(404));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		Optional<Bundle> record = service.getById(id);
		if (record.isPresent()) {
			service.delete(record.get());
			return new ResponseEntity<>(HttpStatusCode.valueOf(200));
		}
		return new ResponseEntity<>(HttpStatusCode.valueOf(404));
	}
}
