
package com.Aditya_DoctorConsultantProject.Aditya_DoctorConsultantProject.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Admin_Controller 
{
     @GetMapping("/AdminLogin")
     public String go()
     {
         return"AdminLogin";
     }
    @GetMapping("/AdminHome")
     public String adminhome(HttpSession session    )
     {
         String email=(String) session.getAttribute("email");
         if(email==null)
         {
             return "redirect:/AdminLogin";
         }
         else
         {
         return "AdminHome";
     }
     }
     @GetMapping("/AdminManageCities")
     public String managecities(HttpSession session)
     {
         String email=(String) session.getAttribute("email");
         if(email==null)
         {
             return "redirect:/AdminLogin";
         }
         else
         {
         return "AdminManageCities";
     }
     }
      @GetMapping("/AdminManageSpecialities")
     public String managespecialities(HttpSession session)
     {
         String email=(String) session.getAttribute("email");
         if(email==null)
         {
             return "redirect:/AdminLogin";
         }
         else
         {
         return "AdminManageSpecialities";
     }
     }
    @GetMapping("/AdminManageDoctor")
    public String managaedoctor(HttpSession session)
    {
       String email=(String) session.getAttribute("email");
         if(email==null)
         {
             return "redirect:/AdminLogin";
         }
         else
         {
         return "AdminManageDoctor";
     }
    }
}
