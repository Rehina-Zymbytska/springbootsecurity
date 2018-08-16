package ua.com.rehina.springbootsecurity.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.com.rehina.springbootsecurity.models.Customer;

public interface CustomerDAO extends JpaRepository<Customer, Integer> {

//@Query("select  c from Customer c where c.email=:email")
//    Customer findByEmail(@Param("email") String email);

    Customer findByUsername(String username);
//    Customer deleteAllByAccountNonExpired(String accountNonExpired);
    Customer findByEmail(String email);

    Customer findById(int id);



}
