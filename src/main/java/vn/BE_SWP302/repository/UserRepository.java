package vn.BE_SWP302.repository;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.BE_SWP302.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    public User findByEmail(String email);

    User findByRefreshTokenAndEmail(String token, String email);

    boolean existsByEmail(String email);

    @Query("SELECT COUNT(u) FROM User u WHERE u.role.roleName = :roleName")
    long countByRoleName(String roleName);
    @Query("SELECT u FROM User u JOIN FETCH u.role WHERE u.email = :email")
    User findByEmailWithRole(@Param("email") String email);

    @Query("SELECT u FROM User u JOIN FETCH u.role WHERE u.email = :email AND u.refreshToken = :refreshToken")
    User findByEmailAndRefreshTokenWithRole(@Param("email") String email, @Param("refreshToken") String refreshToken);


    User findByName(String name);

}
