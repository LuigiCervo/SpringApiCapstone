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

import com.example.demo.model.Dish;
import com.example.demo.service.DishService;


@RestController
@RequestMapping("/api/dish")
public class DishController {
	
	@Autowired
	private DishService service;
	
	//Create (/api/dish/)
	//201 CREATED -> {Headers: {Location: "/api/dish/{id}"}}
	@CrossOrigin("*")
	@PostMapping("/")
	public ResponseEntity<?> create(@RequestBody Dish dish) {
		Dish createdDish = service.create(dish);
		return ResponseEntity.
			created(ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(createdDish.getId())
				.toUri())
		.build();
	}
	
	//Read (/api/dish/{id})
	//200 OK -> Dish richiesto
	//404 NOT FOUND -> ID non presente nel DB
	@CrossOrigin("*")
	@GetMapping("/{id}")
	public ResponseEntity<Dish> getById(@PathVariable Long id) {
		Optional<Dish> record = service.getById(id);
		if (record.isPresent()) {
			return ResponseEntity.ok(record.get());
		}
		return new ResponseEntity<Dish>(HttpStatusCode.valueOf(404));
		
	}

	//Read (/api/dish/list)
	//200 OK -> Lista (anche vuota) di dish
	@CrossOrigin("*")
	@GetMapping("/list")
	public List<Dish> getAll() {
		return service.getAll();
	}
	
	//Update (/api/dish/{id})
	//200 OK -> Modificato con successo
	//404 NOT FOUND -> id non esistente nel DB
	@CrossOrigin("*")
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Dish dish){
		Optional<Dish> record = service.getById(id);
		if (record.isPresent()) {
			service.update(id, record.get(), dish);
			return new ResponseEntity<Dish>(HttpStatusCode.valueOf(200));
		}
		return new ResponseEntity<Dish>(HttpStatusCode.valueOf(404));
	}

	//Delete (/api/dish/{id})
	//200 OK -> Cancellato con successo
	//404 NOT FOUND -> id non esistente nel DB
	@CrossOrigin("*")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		Optional<Dish> record = service.getById(id);
		if (record.isPresent()) {
			service.delete(record.get());
			return new ResponseEntity<Dish>(HttpStatusCode.valueOf(200));
		}
		return new ResponseEntity<Dish>(HttpStatusCode.valueOf(404));
	}
}
