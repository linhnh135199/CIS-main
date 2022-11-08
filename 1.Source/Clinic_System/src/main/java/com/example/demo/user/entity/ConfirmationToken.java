package com.example.demo.user.entity;

import com.example.demo.common.Constants;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@javax.persistence.Entity
@Table(name = Constants.TABLE_CONFIRMATION_TOKEN)
@Getter
@Setter
public class ConfirmationToken {

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	/** The confirmation token. */
	@Column(name = "CONFIRMATION_TOKEN")
	private String confirmationToken;

	/** The user id. */
	@Column(name = "USER_ID")
	private Long userId;

	/** The expiredate. */
	@Column(name = "EXPIRE_DATE")
	private Date expiredate;

	/** The type. */
	@Column(name = "TYPE")
	private String type;

}
