package vn.BE_SWP302.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import vn.BE_SWP302.repository.TestReferenceRangeRepository;

@RestController
@RequestMapping("/api/reference-range")
@RequiredArgsConstructor
public class TestReferenceRangeController {
    private final TestReferenceRangeRepository repo;

    @GetMapping("/{testName}")
    public ResponseEntity<String> getNormalRange(@PathVariable String testName) {
        return repo.findByTestName(testName)
                .map(ref -> ResponseEntity.ok(ref.getNormalRange()))
                .orElse(ResponseEntity.notFound().build());
    }
}
