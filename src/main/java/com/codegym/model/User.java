package com.codegym.model;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements Validator {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String email;
    private String pass;
    private String address;
    private int age;

    @OneToMany(targetEntity = Product.class)
    private List<Product> products;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public User() {
    }

    public User(String name, String email, String pass, String address, int age) {
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.address = address;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean index(String email, String pass) {
        return this.email.equals(email) && this.pass.equals(pass);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        String name = user.getName();
        ValidationUtils.rejectIfEmpty(errors, "name", "name.empty");
        if (name.length() < 5) {
            errors.rejectValue("name", "name.length");
        }

        String email = user.getEmail();
        ValidationUtils.rejectIfEmpty(errors, "email", "email.empty");
        if (!email.matches("(^[A-Za-z0-9]+[A-Za-z0-9]*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)$)")) {
            errors.rejectValue("email", "email.matches");
        }

        String pass = user.getPass();
        ValidationUtils.rejectIfEmpty(errors, "pass", "password.empty");
        if (pass.length() < 6) {
            errors.rejectValue("pass", "password.length");
        }

        if (!pass.matches("(^[A-Za-z0-9]*[0-9|A-Z]+[A-Za-z0-9]*$)")) {
            errors.rejectValue("pass", "password.matches");
        }

        ValidationUtils.rejectIfEmpty(errors, "address", "address.empty");

        int age = user.getAge();
        if (age < 4) {
            errors.rejectValue("age", "age.min");
        }
    }
}
