package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Reservation;
import com.example.demo.repository.ReservationRepository;

@Service
public class ReservationService {
	
	@Autowired
	protected ReservationRepository repo;
	
	public Reservation create(Reservation r) {
		return repo.save(r);
	}
	
	public List<Reservation> getAll(){
		return repo.findAll();
	}
	
	public Optional<Reservation> getById(Long id) {
		return repo.findById(id);
	}
	
	public void update(Long id, Reservation oldReservation, Reservation newReservation) {
		oldReservation.setGuest(newReservation.getGuest());
		oldReservation.setReservationTime(newReservation.getReservationTime());
		oldReservation.setSeats(newReservation.getSeats());

		repo.save(oldReservation);
	}
	
	public void delete(Reservation r) {
		repo.delete(r);
	}

}
