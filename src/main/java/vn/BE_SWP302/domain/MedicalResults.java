package vn.BE_SWP302.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
	private Long resultId; // Tự sinh

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "exam_id", nullable = false)
	private Examination examination; // Liên kết với Examination

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "doctor_id")
	private User doctor; // Người nhập kết quả (bác sĩ đang đăng nhập)

	private String testName; // Lấy từ examination.name
	private LocalDateTime examDate; // Lấy từ examination.examDate

	private LocalDate resultDate; // Người dùng chọn
	private String resultValue; // Nhập từ form
	private String conclusion; // Nhập từ form

	@OneToMany(mappedBy = "medicalResult")
	private List<TreatmentRecord> treatmentRecords;

}
