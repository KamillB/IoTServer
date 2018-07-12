package com.example.iotserver.main.rest;

import com.example.iotserver.main.models.TempData;
import com.example.iotserver.main.repository.TempRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/rpi_data")
public class RpiDataController {

    @Autowired
    private TempRepository tempRepository;

//////// just for tests
    @GetMapping("/testtempadd")
    public String testtempadd(){
        TempData temp = new TempData();
        temp.setOwner("rpi unique id");
        temp.setData(new Date());
        temp.setTemp(25.54);

        tempRepository.save(temp);
        System.out.println(temp.getId());
        return "added succesfully";
    }
    
    @RequestMapping(method=RequestMethod.GET)
    public String getPage(){
        Date date = new Date();
        String response = "<li>"+date+"</li>";
        return response;
    }
//////////
    @RequestMapping(method=RequestMethod.POST, value="temp")
    @ResponseBody
    public TempData saveTemperature(@RequestBody TempData input){
        TempData temp = new TempData();
        temp.setData(new Date());
        temp.setOwner(input.getOwner());
        temp.setTemp(input.getTemp());

        tempRepository.save(temp);
        return input;
    }

    ///TODO add handlers for image
}
