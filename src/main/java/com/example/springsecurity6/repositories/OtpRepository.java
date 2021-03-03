package com.example.springsecurity6.repositories;

import com.example.springsecurity6.entities.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp, Integer> {

    Optional<List<Otp>> findAllByUsername(String username);

}
