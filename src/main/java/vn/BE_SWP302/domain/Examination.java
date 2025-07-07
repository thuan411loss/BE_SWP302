package vn.BE_SWP302.domain;

import java.time.LocalDateTime;

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
@Setter
@Getter
@Table(name = "examination")
@NoArgsConstructor
@AllArgsConstructor
public class Examination {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long examId;

	@Column(name = "name")
	private String name;

	@ManyToOne
	@JoinColumn(name = "booking_id", nullable = false)
	private Booking booking;

	@Column(name = "exam_date")
	private LocalDateTime examDate;

	@Column(columnDefinition = "TEXT")
	private String diagnosis;

	@Column(columnDefinition = "TEXT")
	private String recommendation;

	@Column(name = "normal_range", length = 100)
	private String normalRange;

}
