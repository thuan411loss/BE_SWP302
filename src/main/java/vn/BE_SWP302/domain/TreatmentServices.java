package vn.BE_SWP302.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "TreatmentService")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class TreatmentServices {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long service_id;

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
