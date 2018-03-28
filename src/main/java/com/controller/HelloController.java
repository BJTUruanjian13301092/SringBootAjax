package com.controller;

import com.entity.User;
import com.entity.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 2017/6/6.
 */
@Controller
@RequestMapping("/hello")
public class HelloController {

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(ModelMap modelMap) {

        modelMap.put("msg", "SpringBoot Ajax 示例");

        ModelAndView mav = new ModelAndView("index");
        System.out.println(mav.getViewName());
        System.out.println(modelMap.toString());

        return "index";
    }

    @RequestMapping(value = "/guess", method = RequestMethod.POST)
    @ResponseBody
    public String home(@RequestParam("firstnumber") int first,
                       @RequestParam("secondnumber") int second) {

        int sum = first + second;
        if(sum == 2){
            return "Success";
        }
        else{
            return "Fail";
        }
    }

    @RequestMapping(value = "/data", method = RequestMethod.POST)
    @ResponseBody
    public List<User> data() {
        List<User> list = new ArrayList<User>();

        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setId(i + 1);
            user.setName("springboot" + i);
            user.setSex("male");
            user.setAge(i + 1);
            user.setRole("developer");

            list.add(user);
        }

        return list;
    }

    @RequestMapping(value = "/success")
    public String jumpToSuccess(){

        return "Success";
    }

    @RequestMapping(value = "/json", method = RequestMethod.POST)
    @ResponseBody
    public String showJson(@RequestBody Person person) {

        System.out.println("AAAAA: " + person.getUserName() + person.getPwd());

        if(person.getUserName().equals("Larry")){
            return "Success";
        }
        else{
            return "Fail";
        }
    }
}