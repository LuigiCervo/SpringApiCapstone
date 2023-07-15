package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Bundle;

public interface BundleRepository extends JpaRepository<Bundle, Long> {
}
