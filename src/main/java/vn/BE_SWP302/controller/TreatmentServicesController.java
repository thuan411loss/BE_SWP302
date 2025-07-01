package vn.BE_SWP302.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import vn.BE_SWP302.domain.request.TreatmentServiceRequest;
import vn.BE_SWP302.domain.response.TreatmentServiceResponse;
import vn.BE_SWP302.service.TreatmentServicesService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/treatment-services")
@RequiredArgsConstructor
public class TreatmentServicesController  {


    private final TreatmentServicesService service;


    @PostMapping("/create")
    public ResponseEntity<TreatmentServiceResponse> create(@RequestBody TreatmentServiceRequest dto) {
        TreatmentServiceResponse response = service.create(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<TreatmentServiceResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TreatmentServiceResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TreatmentServiceResponse> update(@PathVariable Long id, @RequestBody TreatmentServiceRequest dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}