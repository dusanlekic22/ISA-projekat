package isaproject.model;

import javax.persistence.Embeddable;

import isaproject.model.cottage.Cottage;

@Embeddable
public class CottageGrade extends Grade{
	
	private Cottage cottage;

	public Cottage getCottage() {
		return cottage;
	}

	public void setCottage(Cottage cottage) {
		this.cottage = cottage;
	}
	
}
