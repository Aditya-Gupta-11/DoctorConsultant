
package com.Aditya_DoctorConsultantProject.Aditya_DoctorConsultantProject.controller;

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
 public String doctorhome()
 {
     return "DoctorHome";
 }
  @GetMapping("/DoctorManagePhotos")
 public String doctormanagephotos()
 {
     return "DoctorManagePhotos";
 }
 @GetMapping("/DoctorEditDetail")
 public String doctoreditdetail()
 {
     return "DoctorEditDetail";
 }
 @GetMapping("/DoctorManageBookingApp")
 public String doctorbookingl()
 {
     return "DoctorManageBookingApp";
 }       
}
