package isaproject.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;


import javax.persistence.ManyToOne;
import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "CottageImage")
public class CottageImage implements Serializable {

	private static final long serialVersionUID = 1L;

	public CottageImage() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(columnDefinition="text", length=10485760)
	private String image;
	@ManyToOne(fetch = LAZY,targetEntity = Cottage.class)
	private Cottage cottage;
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String param) {
		this.image = param;
	}

	public Cottage getCottage() {
	    return cottage;
	}

	public void setCottage(Cottage param) {
	    this.cottage = param;
	}

}