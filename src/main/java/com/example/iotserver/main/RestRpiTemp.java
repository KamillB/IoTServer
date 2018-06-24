package com.example.iotserver.main;

import com.example.iotserver.main.models.TempData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.*;

@RestController
@RequestMapping("/rpi_data")
public class RestRpiTemp {

    @Autowired
    private TempRepository tempRepository;

    @RequestMapping(method=RequestMethod.GET)
    public String getPage(){
        TempData t = new TempData(1,"rpi", 32.123, new Date());
        tempRepository.save(t);

        Date date = new Date();
        String response = "<li>"+date+"</li>";
//        Iterable<TempData> lista = tempRepository.findAll();
////        if (!lista.isEmpty()){
////            for (int i = 0; i< lista.size(); i++){
//        lista.forEach(tempData -> {
//            response = response + "<li>" + lista.get(i).getId() + " "
//                    + lista.get(i).getName() + " "
//                    + lista.get(i).getTemp() + "</li>";
//        });
//
////            }
////        }

        return response;
    }

    @RequestMapping(method=RequestMethod.POST, value="temp")
    @ResponseBody
    public TempData saveTemperature(@RequestBody TempData input){
        tempRepository.save(input);
        return input;
    }
}
