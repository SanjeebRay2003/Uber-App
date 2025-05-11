package Springboot.Uber.App.Repositories;

import Springboot.Uber.App.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
     Optional<User> findByEmail(String email);
}
