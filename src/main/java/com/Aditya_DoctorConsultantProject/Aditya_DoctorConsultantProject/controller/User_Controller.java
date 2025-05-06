
package com.Aditya_DoctorConsultantProject.Aditya_DoctorConsultantProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class User_Controller
{
    @GetMapping("/UserSignup")
    public String Usersignup()
    {
        return "UserSignup";
    }
    @GetMapping("/UserLogin")
    public String Userlogin()
    {
        return "UserLogin";
    }
    @GetMapping("/")
    public String index()
    {
        return "index";
    }
    @GetMapping("/UserShowSpeciality")
    public String usershowspeciality()
    {
        return "UserShowSpeciality";
    }
    @GetMapping("/UserShowDoctor")
    public String usershowdoctor()
    {
        return "UserShowDoctor";
    }
    @GetMapping("/UserDoctorDetail")
    public String userdoctordetail()
    {
        return "UserDoctorDetail";
    }
       @GetMapping("/UserBookAppointment")
    public String userbookappointment()
    {
        return "UserBookAppointment";
    }
}
