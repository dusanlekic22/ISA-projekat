package isaproject.model;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import isaproject.model.Address;

import javax.persistence.Inheritance;
import static javax.persistence.InheritanceType.TABLE_PER_CLASS;
import static javax.persistence.InheritanceType.JOINED;
import javax.persistence.DiscriminatorColumn;
import static javax.persistence.DiscriminatorType.INTEGER;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import static javax.persistence.DiscriminatorType.STRING;

@Entity
@Inheritance(strategy = JOINED)
@Table(name = "AppUser")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	public User() {
	}

	@Id
	private long id;
	@Basic(optional = false)
	@Column(name = "username")
	private String username;
	@Basic(optional = false)
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private Boolean enabled;
	private String verificationCode;
	@ManyToMany(fetch = EAGER, cascade = ALL)
	@JoinTable(joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private List<Role> roles;
	private String phoneNumber;
	@OneToOne
	private Address address;
	@OneToOne


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String param) {
		this.username = param;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String param) {
		this.password = param;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String param) {
		this.firstName = param;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String param) {
		this.lastName = param;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String param) {
		this.email = param;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String param) {
		this.verificationCode = param;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> param) {
		this.roles = param;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String param) {
		this.phoneNumber = param;
	}

	public Address getAddress() {
	    return address;
	}

	public void setAddress(Address param) {
	    this.address = param;
	}



}