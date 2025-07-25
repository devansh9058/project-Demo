package com.example.Authentication.Repository;

import com.example.Authentication.Entity.Role;
import com.example.Authentication.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {


    Optional<User> findByEmail(String email);
    User findByRole(Role role);


}
