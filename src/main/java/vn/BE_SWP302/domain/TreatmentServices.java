package vn.BE_SWP302.domain;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "treatment_service")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class TreatmentServices {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "service_id")
	private Long service_id;

	@Column(name = "name")
	private String name;

	private String category;
	private String title;

	@Column(columnDefinition = "TEXT")
	private String description;

	private String duration;
	private String successRate;

	@ElementCollection
	private List<String> features;

	private String priceRange;
	private String currency;
	private String badge;

}
