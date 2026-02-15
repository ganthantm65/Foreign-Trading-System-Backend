package com.example.Foreign.Trading.System.Repository;

import com.example.Foreign.Trading.System.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users,Integer> {
}
