package vn.BE_SWP302.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Account")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "account_id")
	private Long accountId;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", unique = true)
	private User user;

	@Column(name = "username", unique = true, nullable = false, length = 50)
	private String username;

	@Column(name = "password_hash", nullable = false)
	private String passwordHash;

	@Column(name = "created_at")
	private LocalDateTime createdAt = LocalDateTime.now();

	@Column(name = "is_active")
	private Boolean isActive = true;

}
