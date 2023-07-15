package com.example.demo.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "bundle")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Bundle {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, name = "name")
	private String name;
	@Column(nullable= true, name = "discount")
	private double discount;
	
	@ManyToMany(fetch=FetchType.EAGER, cascade= CascadeType.MERGE)
	@JoinTable(name= "bundle_dish", joinColumns=@JoinColumn(name="bundle_id", referencedColumnName="id"), inverseJoinColumns=@JoinColumn(name="dish_id", referencedColumnName="id"))
	private Set<Dish> bundleItems = new HashSet<>();
}
