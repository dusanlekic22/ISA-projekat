package isaproject.dto;

import isaproject.model.cottage.Cottage;

public class AdditionalServiceDTO {
	
	private long id;
	private String name;
	private String price;
	private Cottage cottage;
	
	public AdditionalServiceDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public Cottage getCottage() {
		return cottage;
	}
	public void setCottage(Cottage cottage) {
		this.cottage = cottage;
	}
}
