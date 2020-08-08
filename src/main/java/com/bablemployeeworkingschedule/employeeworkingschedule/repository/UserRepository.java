package com.bablemployeeworkingschedule.employeeworkingschedule.repository;

import com.bablemployeeworkingschedule.employeeworkingschedule.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
    long deleteById(long id);
}
