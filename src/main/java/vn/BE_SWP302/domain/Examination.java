package vn.BE_SWP302.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Examination {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long exam_id;

	@ManyToOne
	@JoinColumn(name = "booking_id", nullable = false)
	private Booking booking;

	private LocalDateTime exam_date;

	@Column(columnDefinition = "TEXT")
	private String diagnosis;

	@Column(columnDefinition = "TEXT")
	private String recommendation;

}
