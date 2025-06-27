package vn.BE_SWP302.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "TreatmentService")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class TreatmentServices {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "service_id")
	private Long serviceId;

	@Column(name = "name", nullable = false)
	private String name;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false) // Người tạo dịch vụ (thường là admin hoặc bác sĩ)
	private User user;

	@Column(name = "description", columnDefinition = "TEXT")
	private String description;

	@Column(name = "type", length = 50)
	private String type;

	@Column(name = "fee")
	private Double fee;

	@Column(name = "duration_minutes")
	private Integer durationDays;

	@Column(name = "is_active")
	private Boolean isActive = true;

}
