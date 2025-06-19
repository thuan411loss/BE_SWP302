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
@Table(name = "bookings")
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookingId;

	@ManyToOne
	@JoinColumn(name = "customer_id", nullable = false)
	private User customer;

	@ManyToOne
	@JoinColumn(name = "service_id", nullable = false)
	private TreatmentServices service;

	@ManyToOne
	@JoinColumn(name = "work_id", nullable = false)
	private WorkSchedule work;

	private LocalDateTime bookingDate = LocalDateTime.now();

	private String status = "Pending";

	@Column(columnDefinition = "TEXT")
	private String note;

}
