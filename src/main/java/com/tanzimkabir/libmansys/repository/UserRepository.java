package com.tanzimkabir.libmansys.repository;

import com.tanzimkabir.libmansys.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User getByUserName(String userName);
}
