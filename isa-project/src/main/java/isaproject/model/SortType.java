package isaproject.model;

public class SortType {


	public String  field;
	
	public String direction;

	public SortType(){}
	
	
	public SortType(String field,String direction){
		this.field= field;
		this.direction= direction;
	}


	public String getField() {
		return field;
	}


	public void setField(String field) {
		this.field = field;
	}


	public String getDirection() {
		return direction;
	}


	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	


}
