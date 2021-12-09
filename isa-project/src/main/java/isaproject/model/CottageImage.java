package isaproject.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import isaproject.model.Cottage;

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
	private String image;
	@ManyToOne(fetch = LAZY)
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