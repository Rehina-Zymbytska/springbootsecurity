package ua.com.rehina.springbootsecurity.service;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ua.com.rehina.springbootsecurity.models.Customer;

public interface CustomerService extends UserDetailsService {

    void save(Customer customer);


    UserDetails loadUserByEmail(String email) throws UsernameNotFoundException;

    UserDetails loadUserById(int id);
}
