package com.example.demo.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reservation")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column (nullable = false, name = "reservation_timestamp")
	private LocalDateTime reservationTime;
	@Column (nullable = false, name = "seats")
	private int seats;

	@ManyToOne
	@JoinColumn(nullable=false, name="user_id")
	private User guest;

	public LocalDateTime getExpirationTime() {
		return reservationTime.plusMinutes(30);
	}
}
