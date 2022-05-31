package isaproject.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity()
public class Address implements Serializable {

	private static final long serialVersionUID = 1L;

	public Address() {
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String street;
	private String city;
	private String country;
	private Double latitude;
	private Double longitude;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double param) {
		this.latitude = param;
	}

	public Double getlongitude() {
		return longitude;
	}

	public void setlongitude(Double param) {
		this.longitude = param;
	}

}