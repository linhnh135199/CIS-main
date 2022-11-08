package com.example.demo.user.entity;

import com.example.demo.common.Constants;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@javax.persistence.Entity
@Table(name = Constants.TABLE_USER)
@Getter
@Setter
public class User implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -9078083261202261084L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	@JsonProperty
	private Long id;

	/** The name. */
	@Column(name = "NAME", nullable = true)
	@JsonProperty
	private String name;

	/** The email. */
	@Column(name = "EMAIL")
	@JsonProperty
	private String email;

	// Role
	@JoinColumn(name = "role_id")
	@JsonProperty
	private Long roleId;

	/** The phone number. */
	@Column(name = "PHONE_NUMBER", nullable = false)
	@JsonProperty
	private String phoneNumber;

	/** The password. */
	@Column(name = "PASSWORD", nullable = false)
	private String password;

	@Column(name = "BIRTH_DAY", nullable = false)
	@JsonProperty
	private Date birthday;

	@Column(name = "gender", nullable = false)
	@JsonProperty
	private String gender;

	@Column(name = "address")
	@JsonProperty
	private String address;

	/** The active. */
	@Column(name = "ACTIVE", nullable = false)
	private int active;

	/** The created time. */
	@Column(name = "CREATED_TIME")
	private Date createdTime;

	/** The modified time. */
	@Column(name = "MODIFIED_TIME")
	private Date modifiedTime;

	@Column(name = "avatar")
	@JsonProperty
	private String avatar;
}
