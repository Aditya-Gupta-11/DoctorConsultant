
package com.Aditya_DoctorConsultantProject.Aditya_DoctorConsultantProject.controller;

import jakarta.servlet.http.HttpSession;
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
    
    @GetMapping("/UserShowCities")
    public String usershowcities(HttpSession session)
    {
        String uemail=(String) session.getAttribute("uemail");
        if(uemail==null)
        {
            
            return "redirect:/UserLogin";
        }
        else
        {
        return "UserShowCities";
    }
    }
    
    @GetMapping("/UserShowSpeciality")
    public String usershowspeciality(HttpSession session)
    {
        String uemail=(String) session.getAttribute("uemail");
        if(uemail==null)
        {
            
            return "redirect:/UserLogin";
        }
        else
        {
        return "UserShowSpeciality";
    }
    }
    @GetMapping("/UserShowDoctor")
    public String usershowdoctor(HttpSession session)
    {
        String uemail=(String) session.getAttribute("uemail");
        if(uemail==null)
        {
            
            return "redirect:/UserLogin";
        }
        else
        {
        return "UserShowDoctor";
    }
        
    }
    @GetMapping("/UserDoctorDetail")
    public String userdoctordetail(HttpSession session)
    {
        String uemail=(String) session.getAttribute("uemail");
        if(uemail==null)
        {
            
            return "redirect:/UserLogin";
        }
        else
        {
        return "UserDoctorDetail";
    }
    }
       @GetMapping("/UserBookAppointment")
    public String userbookappointment(HttpSession session)
    {
        String uemail=(String) session.getAttribute("uemail");
        if(uemail==null)
        {
            
            return "redirect:/UserLogin";
        }
        else
        {
        return "UserBookAppointment";
    }
    }
    @GetMapping("/payment")
    public String payment(HttpSession session)
    {
        String uemail=(String) session.getAttribute("uemail");
        if(uemail==null)
        {
            
            return "redirect:/UserLogin";
        }
        else
        {
        return "payment";
    }
    }
    @GetMapping("/UserManageAppointments")
    public String usermanageappointment(HttpSession session)
    {
        String uemail=(String) session.getAttribute("uemail");
        if(uemail==null)
        {
            
            return "redirect:/UserLogin";
        }
        else
        {
        return "UserManageAppointments";
    }
    }
    @GetMapping("/UserChangePassword")
    public String userchangepassword(HttpSession session)
    {
        String uemail=(String) session.getAttribute("uemail");
        if(uemail==null)
        {
            
            return "redirect:/UserLogin";
        }
        else
        {
        return "UserChangePassword";
    }
    }
    @GetMapping("/Logout")
    public String logout(HttpSession session)
    {
        session.invalidate();
        return "redirect:/";
    }
    @GetMapping("/AboutUs")
    public String about()
    {
        return "AboutUs";
    }
      @GetMapping("/FAQ")
    public String FAQ()
    {
        return "FAQ";
    }
}
