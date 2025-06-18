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
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "treatment_schedules")
public class TreatmentSchedules {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "schedule_id")
	private Long scheduleId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "result_id")
	private MedicalResults medicalResult;

	@Column(name = "start_date")
	private LocalDate startDate;

	@Column(name = "end_date")
	private LocalDate endDate;

	@Column(name = "status", length = 50)
	private String status;

	@Column(name = "notes", columnDefinition = "TEXT")
	private String notes;

	@OneToMany(mappedBy = "treatmentSchedule", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<TreatmentProgress> treatmentProgresses;

}