package ua.com.rehina.springbootsecurity.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ua.com.rehina.springbootsecurity.models.Customer;

import java.beans.PropertyEditorSupport;

@Component
public class CustomerEditor extends PropertyEditorSupport {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void setValue(Object value){
        Customer customer = (Customer) value;
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));

    }
}
