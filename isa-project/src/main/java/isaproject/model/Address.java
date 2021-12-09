package isaproject.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Address implements Serializable {

	private static final long serialVersionUID = 1L;

	public Address() {
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String street;
	private String city;
	private String country;
	private String latitude;
	private String longitude;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String param) {
		this.street = param;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String param) {
		this.city = param;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String param) {
		this.country = param;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String param) {
		this.latitude = param;
	}

	public String getlongitude() {
		return longitude;
	}

	public void setlongitude(String param) {
		this.longitude = param;
	}

}