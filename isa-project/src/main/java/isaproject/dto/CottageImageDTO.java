package isaproject.dto;

import isaproject.model.Cottage;

public class CottageImageDTO {
	private long id;
	private String image;
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
	public void setImage(String image) {
		this.image = image;
	}
	public Cottage getCottage() {
		return cottage;
	}
	public void setCottage(Cottage cottage) {
		this.cottage = cottage;
	}
	
}
