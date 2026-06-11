package org.example.project.repository;

import org.example.project.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail( String email);
    boolean existsByPhoneNumber( String phone);
    boolean existsByEmailAndIdNot(String email, Long id);
    boolean existsByUsernameAndIdNot(String username, Long id);
    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Long id);
    @Query("select u from User u where u.isEnabled=true and lower(u.fullName) like lower(concat('%',:search,'%'))")
    Page<User> findAllBySearch(@Param("search") String search, Pageable pageable);
}
