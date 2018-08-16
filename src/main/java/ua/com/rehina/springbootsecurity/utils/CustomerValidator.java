package ua.com.rehina.springbootsecurity.utils;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.com.rehina.springbootsecurity.models.Customer;

@Component
public class CustomerValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(Customer.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Customer customer = (Customer) o;
        if (customer.getUsername().length() < 3) {
            errors.rejectValue("username", "message.length.error");
//            errors.rejectValue("username", "message.length.error");
//            errors.rejectValue("username", "message.length.error");
        }

    }
}
