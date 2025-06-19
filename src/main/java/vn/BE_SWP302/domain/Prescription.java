package vn.BE_SWP302.domain;

import jakarta.persistence.Column;
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

@Entity
@Table(name = "prescription")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Prescription {
	@Id

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "prescription_id")
	private Long prescriptionId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "result_id")
	private MedicalResults medicalResult;

	@Column(name = "medicine_name")
	private String medicineName;

	@Column(name = "dosage", length = 100)
	private String dosage;

	@Column(name = "usage_instruction", columnDefinition = "TEXT")
	private String usageInstruction;

}
