package ua.com.rehina.springbootsecurity.controllers;

import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ua.com.rehina.springbootsecurity.dao.CustomerDAO;
import ua.com.rehina.springbootsecurity.models.Customer;
import ua.com.rehina.springbootsecurity.service.CustomerService;
import ua.com.rehina.springbootsecurity.utils.CustomerEditor;
import ua.com.rehina.springbootsecurity.utils.CustomerValidator;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@PropertySource("classpath:validation.properties")
public class MainController {


    @Autowired
    private JavaMailSender sender;


    @Autowired
    private Environment environment;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerDAO customerDAO;


    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/ok")
    public String ok() {
        return "success";
    }

    @GetMapping("/login")
    public String asd() {
        return "login";
    }

    @GetMapping("/login-error")
    public String loginError() {
        return "login-error";
    }

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomerEditor customerEditor;
    @Autowired
    private CustomerValidator customerValidator;

    @PostMapping("/save")
    public String save(Customer customer, BindingResult result, Model model, @RequestParam("email") String email) throws MessagingException, javax.mail.MessagingException {

        customerValidator.validate(customer, result);
        if (result.hasErrors()) {
            String errorMessage = "";
            List<ObjectError> allErrors = result.getAllErrors();
            for (ObjectError error : allErrors) {
                error.getCodes();
                errorMessage += " " + environment.getProperty(error.getCode());
            }
            model.addAttribute("error", errorMessage);

            return "index";
        }

        customerEditor.setValue(customer);

//        String password = customer.getPassword();
//        String encode = passwordEncoder.encode(password);
//        customer.setPassword(encode);
//        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customerService.save(customer);
        //System.out.println("befor send mail");
        sendMail(email);
        //System.out.println("after");
        return "login";
    }


    @GetMapping("/registry/{id}")
    public String registry(@PathVariable int id){
        Customer customer = customerDAO.findById(id);
        if (!customer.isEnabled()){
            customer.setEnabled(true);
        }
        return "login";
    }

    private void sendMail(String email) throws MessagingException, javax.mail.MessagingException {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        //UserDetails userDetails = customerService.loadUserByEmail(email).;
        int customerId = customerDAO.findByEmail(email).getId();

        helper.setText("<a href='http://localhost:8080/registry/" + customerId + "'>Activate link</a>", true);
        helper.setSubject("mail from Rehina");
        helper.setTo(email);

        sender.send(message);
    }


}
