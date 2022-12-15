package com.example;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@SpringBootApplication
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class Main {

    private final CustomerRepository customerRepository;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @GetMapping("/hello")
    public GreetResponse greeting() {
        return new GreetResponse(
                "Hello, World!",
                List.of("JavaScript", "Go", "Java"),
                new Person(
                        "Natan",
                        23,
                        110
                )
        );
    }

    record Person(
            String name,
            int age,
            double savings
    ) {
    }

    record GreetResponse(
            String greet,
            List<String> favProgrammingLanguages,
            Person person
    ) {
    }

    @GetMapping
    public List<Customer> getCustomer(){
    return customerRepository.findAll();
    }

    record NewCustomerRequest(
            String name,
            String email,
            Integer age
    ){

    }

    @PostMapping
    public void addCustomer(
            @RequestBody NewCustomerRequest newCustomerRequest
    ){
        Customer customer = new Customer();
        customer.setName(newCustomerRequest.name());
        customer.setAge(newCustomerRequest.age());
        customer.setEmail(newCustomerRequest.email());

        customerRepository.save(customer);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable("id") Integer id){
        customerRepository.deleteById(id);
    }

    @("/{id}")
    public NewCustomerRequest updateCustomer(
            @PathVariable("id") Integer id,
            @RequestBody NewCustomerRequest newCustomerRequest
    ) {
        Customer customer = customerRepository.findCustomerById(id);
        customer.setName(newCustomerRequest.name());
        customer.setAge(newCustomerRequest.age());
        customer.setEmail(newCustomerRequest.email());

        customerRepository.save(customer);
        return newCustomerRequest;

    }

}
