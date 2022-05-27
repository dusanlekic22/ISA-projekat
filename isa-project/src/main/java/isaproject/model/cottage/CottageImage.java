package isaproject.model.cottage;

import static javax.persistence.FetchType.LAZY;

import static javax.persistence.FetchType.LAZY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
	@JoinColumn(name = "cottage_id")
	@JsonBackReference
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