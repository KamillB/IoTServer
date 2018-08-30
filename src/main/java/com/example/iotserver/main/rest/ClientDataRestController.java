package com.example.iotserver.main.rest;


import com.example.iotserver.main.models.db.User;
import com.example.iotserver.main.models.dbModels.UserModel;
import com.example.iotserver.main.repository.UserRepository;
import com.example.iotserver.main.utils.Encrypter;
import com.example.iotserver.main.utils.UniqueKeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/client")
public class ClientDataRestController {
    @Autowired
    UserRepository userRepository;

    @PostMapping("register")
    public String registerUser(@RequestBody UserModel input){
        String password;
        Iterable<User> users = userRepository.findAll();
        for (User user : users){
            if (user.getMail().equals(input.getMail())){
                return "UsedMail";
            }
            if (user.getName().equals(input.getName())){
                return "UsedName";
            }
        }
        try {
            password = Encrypter.encrypt(input.getPassword());
        }
        catch (Exception e){
            return "Failed to encrypt the password";
        }
        User user = new User(
                input.getName(),
                input.getMail(),
                password
        );
        userRepository.save(user);
        return "Success";
    }

    @PostMapping("login")
    public String loginUser(@RequestBody UserModel input){
        try {
            User user = userRepository.findByMail(input.getMail());
            String dbPassword = user.getPassword();
            String inputPassword = Encrypter.encrypt(input.getPassword());
            if (dbPassword.equals(inputPassword)){
                return UniqueKeyGenerator.generate(user.getMail());
            }
            else{
                return "WrongPassword";
            }
        }
        catch (Exception e){
            return "MailNotFound";
        }
    }

    @GetMapping("test")
    public String test(HttpServletRequest request){
        String ip = request.getRemoteAddr();
        System.out.println("THE IP IS " + ip);
        System.out.println("no test wszedl");
        return "no wydaje sie ze dziala lol";
    }
}
