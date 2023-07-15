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

import com.example.demo.model.Manufacturer;
import com.example.demo.service.ManufacturerService;


@CrossOrigin("*")
@RestController
@RequestMapping("/api/manufacturer")
public class ManufacturerController {
	
	@Autowired
	private ManufacturerService service;
	
	@CrossOrigin("*")
	@PostMapping("/")
	public ResponseEntity<?> create(@RequestBody Manufacturer manufacturer) {
		Manufacturer createdManufacturer = service.create(manufacturer);
		return ResponseEntity.
			created(ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(createdManufacturer.getId())
				.toUri())
		.build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Manufacturer> getById(@PathVariable Long id) {
		Optional<Manufacturer> record = service.getById(id);
		if (record.isPresent()) {
			return ResponseEntity.ok(record.get());
		}
		return new ResponseEntity<>(HttpStatusCode.valueOf(404));
		
	}

	@GetMapping("/list")
	public List<Manufacturer> getAll() {
		return service.getAll();
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Manufacturer manufacturer){
		Optional<Manufacturer> record = service.getById(id);
		if (record.isPresent()) {
			service.update(id, record.get(), manufacturer);
			return new ResponseEntity<>(HttpStatusCode.valueOf(200));
		}
		return new ResponseEntity<>(HttpStatusCode.valueOf(404));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		Optional<Manufacturer> record = service.getById(id);
		if (record.isPresent()) {
			service.delete(record.get());
			return new ResponseEntity<>(HttpStatusCode.valueOf(200));
		}
		return new ResponseEntity<>(HttpStatusCode.valueOf(404));
	}
}
