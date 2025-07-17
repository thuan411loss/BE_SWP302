package vn.BE_SWP302.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.BE_SWP302.domain.TestReferenceRange;

public interface TestReferenceRangeRepository extends JpaRepository<TestReferenceRange, Long> {
    Optional<TestReferenceRange> findByTestName(String testName);
}
