package com.vinayak;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@SpringBootApplication
@RestController
@RequestMapping("api/v1/customers")
public class Main {
    @Autowired
    private final CustomerRepository customerRepository;

    public Main(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
    @GetMapping
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }
    record NewCustomerRequest(
            String name,
            String email,
            Integer age
    ){

    }
    @PostMapping
    public void addCustomer(@RequestBody NewCustomerRequest request){
        Customer customer = new Customer();
        customer.setName(request.name());
        customer.setEmail(request.email());
        customer.setAge(request.age());
        customerRepository.save(customer);
    }
    @DeleteMapping("{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Integer id){
        customerRepository.deleteById(id);
    }
    @PutMapping("{customerId}")
    public void updateCustomer(@PathVariable("customerId") Integer id,
                               @RequestBody NewCustomerRequest request){
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
        existingCustomer.setName(request.name());
        existingCustomer.setEmail(request.email());
        existingCustomer.setAge(request.age());
        customerRepository.save(existingCustomer);
    }
}

//    @GetMapping("/")
//    public GreetResponse greet(){
//        GreetResponse response = new GreetResponse(
//                "Hello",
//                List.of("Java", "Kotlin", "Golang", "Dart"),
//                new Person("Vinayak", 22, 30_000)
//        );
//        return response;
//    }
//    record Person(String name, int age, int money){}
//    record GreetResponse(
//            String greet,
//            List<String> favoriteProgrammingLanguages,
//            Person person
//    ){}

//all the code in the class GreetResponse is equivalent to record in the above line
//    class GreetResponse{
//        private final String greet;
//
//        GreetResponse(String greet) {
//            this.greet = greet;
//        }
//
//        public String getGreet() {
//            return greet;
//        }
//
//        @Override
//        public String toString() {
//            return "GreetResponse{" +
//                    "greet='" + greet + '\'' +
//                    '}';
//        }
//
//        @Override
//        public boolean equals(Object o) {
//            if (this == o) return true;
//            if (o == null || getClass() != o.getClass()) return false;
//            GreetResponse that = (GreetResponse) o;
//            return Objects.equals(greet, that.greet);
//        }
//
//        @Override
//        public int hashCode() {
//            return Objects.hashCode(greet);
//        }
//    }