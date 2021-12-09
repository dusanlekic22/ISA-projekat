package isaproject.dto;

import isaproject.model.Customer;

public class CustomerDTO{
 
    private Customer customer;
     
    public CustomerDTO(Customer customer) {
        this.customer = customer;
    }
 

    public boolean isEnabled() {
        return customer.getEnabled();
    }
     

}