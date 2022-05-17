package isaproject.dto;

public class UserChangePasswordDTO {
    
	private Long id;
    private String password;
    
    public UserChangePasswordDTO() {
	}

	public Long getId() {
		return id;
	}
	
    public void setId(Long id) {
		this.id = id;
	}
	
    public String getPassword() {
		return password;
	}
	
    public void setPassword(String password) {
		this.password = password;
	}
    
}
