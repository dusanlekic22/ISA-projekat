package org.persistence;

import java.io.Serializable;
import javax.persistence.*;
import static javax.persistence.AccessType.FIELD;

@Entity
@Table(name = "Role")
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;

	public Role() {
	}

	@Id
	private long id;
	private String name;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String param) {
		this.name = param;
	}

}