package vn.BE_SWP302.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name = "prescription")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Prescription {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long prescriptionId;

	private String instruction;
	private String dosage;
	private String duration;
	private String frequency;
	private String medicineName;
	private LocalDate prescribedDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "treatment_schedule_id")
	private TreatmentSchedule treatmentSchedule;
}