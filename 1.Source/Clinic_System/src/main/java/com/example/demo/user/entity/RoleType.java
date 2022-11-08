package com.example.demo.user.entity;

import com.example.demo.common.Constants;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


@javax.persistence.Entity
@Table(name = Constants.TABLE_ROLE_TYPE)
@Getter
@Setter
public class RoleType implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5377423076956554886L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	/** The name. */
	@Column(name = "ROLE_NAME", nullable = false)
	private String name;

}
