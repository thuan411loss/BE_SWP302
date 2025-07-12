package vn.BE_SWP302.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "treatment_record")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    private String diagnosis;

    private String treatmentPlan;

    private LocalDateTime createdDate;
    @ManyToOne
    @JoinColumn(name = "medical_result_id")
    private MedicalResults medicalResult;

    @OneToMany(mappedBy = "treatmentRecord", cascade = CascadeType.ALL)
    private List<Prescription> prescriptions;
}
