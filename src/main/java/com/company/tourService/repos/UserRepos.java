package com.company.tourService.repos;

import com.company.tourService.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepos extends JpaRepository<User,Long> {
    User findByUsername(String name);
}
