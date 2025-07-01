package vn.BE_SWP302.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.BE_SWP302.domain.request.TreatmentServicesRequest;
import vn.BE_SWP302.domain.response.TreatmentServicesResponse;
import vn.BE_SWP302.service.TreatmentServicesService;
import java.util.List;

@RestController
@RequestMapping("/api/v1/treatment-services")
@RequiredArgsConstructor
public class TreatmentServicesController {

    private final TreatmentServicesService service;

    @PostMapping("/create")
    public ResponseEntity<TreatmentServicesResponse> create(@RequestBody TreatmentServicesRequest dto) {
        TreatmentServicesResponse response = service.create(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<TreatmentServicesResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TreatmentServicesResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TreatmentServicesResponse> update(@PathVariable Long id,
            @RequestBody TreatmentServicesRequest dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
