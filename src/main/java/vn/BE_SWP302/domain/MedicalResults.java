package vn.BE_SWP302.domain;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "medical_results")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MedicalResults {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "result_id")
	private Long resultId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "exam_id", nullable = false)
	private Examination examination;

	@Column(name = "test_name", length = 100)
	private String testName;

	@Column(name = "result_value")
	private String resultValue;

	@Column(name = "result_date")
	private LocalDate resultDate;

	@Column(name = "conclusion", columnDefinition = "TEXT")
	private String conclusion;

	@Column(name = "normal_range", length = 100)
	private String normalRange;

	@OneToMany(mappedBy = "medicalResult", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<TreatmentSchedules> treatmentSchedules;

	@OneToMany(mappedBy = "medicalResult", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Prescription> prescriptions;

	@OneToMany(mappedBy = "medicalResult", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<TreatmentRecord> treatmentRecords;

}
