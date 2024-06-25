package com.example.demo.v1.repositories;

import com.example.demo.v1.models.Saving;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ISavingRepository extends JpaRepository<Saving, UUID> {}
