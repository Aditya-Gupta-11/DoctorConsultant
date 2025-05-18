
package com.Aditya_DoctorConsultantProject.Aditya_DoctorConsultantProject.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Doctor_Controller 
{
 @GetMapping("/DoctorSignup")
 public String doctorSignup()
 {
     return"DoctorSignup";
 }
 @GetMapping("/DoctorLogin")
 public String doctorlogin()
 {
     return "DoctorLogin";
 }
 @GetMapping("/DoctorHome")
 public String doctorhome(HttpSession session)
 {
      Integer did=(Integer) session.getAttribute("did");
     if(did==null)
     {
         return "redirect:/DoctorLogin";
     }
     else
     {
     return "DoctorHome";
 }
 }
  @GetMapping("/DoctorManagePhotos")
 public String doctormanagephotos(HttpSession session)
 {
     Integer did=(Integer) session.getAttribute("did");
     if(did==null)
     {
         return "redirect:/DoctorLogin";
     }
     else
     {
     return "DoctorManagePhotos";
 }
 }
 @GetMapping("/DoctorEditDetail")
 public String doctoreditdetail(HttpSession session)
 {
     Integer did=(Integer) session.getAttribute("did");
     if(did==null)
     {
         return "redirect:/DoctorLogin";
     }
     else
     {
     return "DoctorEditDetail";
 }
 }
 @GetMapping("/DoctorManageBookingApp")
 public String doctorbookingl(HttpSession session)
 {
    Integer did=(Integer) session.getAttribute("did");
     if(did==null)
     {
         return "redirect:/DoctorLogin";
     }
     else
     {
     return "DoctorManageBookingApp";
 }
 }    
  @GetMapping("/DoctorChangePassword")
 public String doctorchangepassword(HttpSession session)
 {
    Integer did=(Integer) session.getAttribute("did");
     if(did==null)
     {
         return "redirect:/DoctorLogin";
     }
     else
     {
     return "DoctorChangePassword";
 }
 }    
 @GetMapping("/DoctorLogout")
 public String logout(HttpSession session)
 {
     session.invalidate();
        return "redirect:/DoctorHome";
 }
}
