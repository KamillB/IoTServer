package com.example.iotserver.main.rest;


import com.example.iotserver.main.models.*;
import com.example.iotserver.main.repository.UserRepository;
import com.example.iotserver.main.utils.UniqueKeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
public class ClientDataRestController {
    @Autowired
    UserRepository userRepository;

    @PostMapping("register")
    public String registerUser(@RequestBody UserModel input){

        Iterable<User> users = userRepository.findAll();
        for (User user : users){
            if (user.getMail().equals(input.getMail())){
                return "This mail is already in use.";
            }
            if (user.getName().equals(input.getName())){
                return "This name is already in use.";
            }
        }

        User user = new User(
                input.getName(),
                input.getMail(),
                input.getPassword()
        );
        userRepository.save(user);
        return "Successfully created user with name " + user.getName();
    }

    @PostMapping("login")
    public String loginUser(@RequestBody UserModel input){
        try {
            User user = userRepository.findByMail(input.getMail());
            if (user.getPassword().equals(input.getPassword())){
                return UniqueKeyGenerator.generate(user.getMail());
            }
            else{
                return "Wrong password.";
            }
        }
        catch (Exception e){
            return "User with this mail not found.";
        }
    }
}
