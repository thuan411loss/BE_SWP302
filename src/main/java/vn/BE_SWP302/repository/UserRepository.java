package vn.BE_SWP302.repository;

import org.springframework.stereotype.Repository;

import vn.BE_SWP302.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public User findByEmail(String email);

    User findByRefreshTokenAndEmail(String token, String email);

    boolean existsByEmail(String email);

}
