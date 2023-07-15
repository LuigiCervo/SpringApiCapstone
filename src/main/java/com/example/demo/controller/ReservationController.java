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

import com.example.demo.model.Reservation;
import com.example.demo.service.ReservationService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

	@Autowired
	private ReservationService service;
	
	@PostMapping("/")
	public ResponseEntity<?> create(@RequestBody Reservation r) {
		Reservation createdReservation = service.create(r);
		return ResponseEntity.
			created(ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(createdReservation.getId())
				.toUri())
		.build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Reservation> getById(@PathVariable Long id) {
		Optional<Reservation> record = service.getById(id);
		if (record.isPresent()) {
			return ResponseEntity.ok(record.get());
		}
		return new ResponseEntity<>(HttpStatusCode.valueOf(404));
		
	}

	@GetMapping("/list")
	public List<Reservation> getAll() {
		return service.getAll();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Reservation r){
		Optional<Reservation> record = service.getById(id);
		if (record.isPresent()) {
			service.update(id, record.get(), r);
			return new ResponseEntity<>(HttpStatusCode.valueOf(200));
		}
		return new ResponseEntity<>(HttpStatusCode.valueOf(404));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		Optional<Reservation> record = service.getById(id);
		if (record.isPresent()) {
			service.delete(record.get());
			return new ResponseEntity<>(HttpStatusCode.valueOf(200));
		}
		return new ResponseEntity<>(HttpStatusCode.valueOf(404));
	}
}
