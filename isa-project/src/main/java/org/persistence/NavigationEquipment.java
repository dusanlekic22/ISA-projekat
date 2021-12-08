package org.persistence;

import static javax.persistence.FetchType.LAZY;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class NavigationEquipment implements Serializable {

	private static final long serialVersionUID = 1L;

	public NavigationEquipment() {
	}

	@Id
	private long id;
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "boat_id", referencedColumnName = "id")
	private Boat boat;
	private String name;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Boat getBoat() {
		return boat;
	}

	public void setBoat(Boat param) {
		this.boat = param;
	}

	public String getName() {
		return name;
	}

	public void setName(String param) {
		this.name = param;
	}

}