package vn.BE_SWP302.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.BE_SWP302.domain.TreatmentServices;
import vn.BE_SWP302.domain.request.TreatmentServicesRequest;
import vn.BE_SWP302.service.TreatmentServicesService;

@RestController
@RequestMapping("/api/v1/treatment-services")
@RequiredArgsConstructor
public class TreatmentServicesController  {

    private final TreatmentServicesService treatmentServicesService;

@PostMapping("create")
public ResponseEntity<?> create(@RequestBody TreatmentServicesRequest request) {
    return ResponseEntity.ok(treatmentServicesService.create(request));
}

@GetMapping
public ResponseEntity<?> getAll() {
    return ResponseEntity.ok(treatmentServicesService.findAll());
}

@GetMapping("/{id}")
public ResponseEntity<?> getById(@PathVariable Long id) {
    TreatmentServices result = treatmentServicesService.findById(id);
    if (result == null) {
        return ResponseEntity.badRequest().body("Service not found");
    }
    return ResponseEntity.ok(result);
}

@DeleteMapping("/{id}")
public ResponseEntity<?> delete(@PathVariable Long id) {
    treatmentServicesService.delete(id);
    return ResponseEntity.ok("Deleted successfully");
}
}

