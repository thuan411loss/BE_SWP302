package vn.BE_SWP302.repository;

import org.springframework.stereotype.Repository;

import vn.BE_SWP302.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    public User findByEmail(String email);

    User findByRefreshTokenAndEmail(String token, String email);

    boolean existsByEmail(String email);

    @Query("SELECT COUNT(u) FROM User u WHERE u.role.roleName = :roleName")
    long countByRoleName(String roleName);

    @Query("SELECT u FROM User u WHERE LOWER(u.name) = LOWER(:name)")
    User findByNameIgnoreCase(@Param("name") String name);

    long countByRole_RoleNameIgnoreCase(String roleName);

    @Query("SELECT u FROM User u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<User> searchByKeyword(@Param("keyword") String keyword);

}
