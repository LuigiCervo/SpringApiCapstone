package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Dish;

public interface DishRepository extends JpaRepository<Dish, Long> {
}
