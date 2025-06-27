package vn.BE_SWP302.domain;

import java.time.LocalDateTime;
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
@Table(name = "treatment_progress")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentProgress {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "progress_id")
	private Long progressId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "schedule_id")
	private TreatmentSchedules treatmentSchedule;

	@Column(name = "progress_date")
	private LocalDateTime progressDate;

	@Column(name = "description")
	private String description;

	@Column(name = "status", length = 50)
	private String status;

	@OneToMany(mappedBy = "treatmentProgress", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Notification> notifications;

}
