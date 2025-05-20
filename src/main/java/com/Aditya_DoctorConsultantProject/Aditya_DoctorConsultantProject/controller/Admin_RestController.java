package com.Aditya_DoctorConsultantProject.Aditya_DoctorConsultantProject.controller;

import com.Aditya_DoctorConsultantProject.Aditya_DoctorConsultantProject.vmm.DBLoader;
import com.Aditya_DoctorConsultantProject.Aditya_DoctorConsultantProject.vmm.RDBMS_TO_JSON;
import jakarta.servlet.http.HttpSession;
import java.io.FileOutputStream;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;
@RestController
public class Admin_RestController 
{
    @PostMapping("/alogin")
    public String alogin(@RequestParam String email2,@RequestParam String pass2, HttpSession session)
    {
        try {
            ResultSet rs=DBLoader.executeSQL("select * from admin where email='"+email2+"' and password='"+pass2+"'");
            if(rs.next())
            {
                session.setAttribute("email",email2 );
                return "Login Successfull";
            }
            else
            {
                return "Login Fail";
            }
        } 
        catch (Exception ex) 
        {
            ex.printStackTrace();
            return "exception";
        }
    }
    @PostMapping("/acity")
    public String acity(@RequestParam String cityName,@RequestParam  MultipartFile cityPhoto,@RequestParam String cityDesc)
    {
        String projectPath= System.getProperty("user.dir");
        String internalPath= "/src/main/resources/static";
        String folderName= "/myUploads";
        String orgName= "/" + cityPhoto.getOriginalFilename(); 
        try {
            ResultSet rs=DBLoader.executeSQL("select * from cities where cityname='"+cityName+"'" );
            if(rs.next())
            {
                return "Failed";
            }
            else
            {
              FileOutputStream fos= new FileOutputStream(projectPath + internalPath + folderName + orgName);
            
                byte[] b1= cityPhoto.getBytes();

                fos.write(b1);
                fos.close();
                rs.moveToInsertRow();
                rs.updateString("cityname", cityName);
                rs.updateString("cityphoto", orgName);
                rs.updateString("citydesc", cityDesc);
                rs.insertRow();
                return"Added Successfully";
            }
        } 
        catch (Exception ex) 
        {
            ex.printStackTrace();
            return "exception";
        }
        
    }
    
    @PostMapping("/showcity")
    public String showcity()
    {
        String ans=new RDBMS_TO_JSON().generateJSON("select * from cities");
        return ans;
    }
    @GetMapping("/deletecity")
    public String deletecity(@RequestParam String cityid)
    {
        try
        {
        ResultSet rs=DBLoader.executeSQL("select * from cities where cityid ='"+cityid+"'");
        if(rs.next())
        {
            rs.deleteRow();
            return"Success";
        }
        else
        {
            return "Not Found";
        }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            
        }
        return "exception";
    }
     @PostMapping("/aspeciality")
    public String aspeciality(@RequestParam String sname,@RequestParam  MultipartFile sphoto,@RequestParam String sdesc)
    {
        String projectPath= System.getProperty("user.dir");
        String internalPath= "/src/main/resources/static";
        String folderName= "/myUploads";
        String orgName= "/" + sphoto.getOriginalFilename(); 
        try {
            ResultSet rs=DBLoader.executeSQL("select * from speciality where sname='"+sname+"'" );
            if(rs.next())
            {
                return "Failed";
            }
            else
            {
              FileOutputStream fos= new FileOutputStream(projectPath + internalPath + folderName + orgName);
            
                byte[] b1= sphoto.getBytes();

                fos.write(b1);
                fos.close();
                rs.moveToInsertRow();
                rs.updateString("sname", sname);
                rs.updateString("sphoto", orgName);
                rs.updateString("sdesc", sdesc);
                rs.insertRow();
                return"Added Successfully";
            }
        } 
        catch (Exception ex) 
        {
            ex.printStackTrace();
            return "exception";
        }
        
    }
      @PostMapping("/showspeciality")
    public String showspeciality()
    {
        String ans=new RDBMS_TO_JSON().generateJSON("select * from speciality");
        return ans;
    }
     @GetMapping("/deletespeciality")
    public String deletespeciality(@RequestParam String id)
    {
        try
        {
        ResultSet rs=DBLoader.executeSQL("select * from speciality where id ='"+id+"'");
        if(rs.next())
        {
            rs.deleteRow();
            return"Success";
        }
        else
        {
            return "Not Found";
        }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            
        }
        return "exception";
    }
     @PostMapping("/showdoctor")
    public String showdoctor() {
            String ans= new RDBMS_TO_JSON().generateJSON("select * from doctor");
            return ans;
    }
    @PostMapping("/accept")
    public String accept(@RequestParam int id) {
        try {
            ResultSet rs= DBLoader.executeSQL("select * from doctor where did= '"+ id +"'");
            if(rs.next()) {
                rs.updateString("dstatus", "accepted");
                rs.updateRow();
                return "success";
            }
            else {
                return "failed";
            }
        }
        catch(Exception ex) {
            return ex.toString();
        }
    }
    @PostMapping("/remove")
    public String remove(@RequestParam int id) {
        try {
            ResultSet rs= DBLoader.executeSQL("select * from doctor where did= '"+ id +"'");
            if(rs.next()) {
                rs.updateString("dstatus", "rejected");
                rs.updateRow();
                return "success";
            }
            else {
                return "failed";
            }
        }
        catch(Exception ex) {
            return ex.toString();
        }
    }
     @PostMapping("/achangepass")
    public String achangepass(@RequestParam String pass1,@RequestParam String pass2,@RequestParam String pass3,HttpSession session)
    {
           String email=(String) session.getAttribute("email");
        try {
            ResultSet rs=DBLoader.executeSQL("select * from admin where email='"+email+"' and password='"+pass1+"'");
            if(rs.next())
            {
                rs.moveToCurrentRow();
                rs.updateString("password", pass2);
                rs.updateRow();
                session.removeAttribute("email");
                return "Password Change Successfull";
            }
            else
            {
                return "Fail";
            }
        } 
        catch (Exception ex) 
        {
            ex.printStackTrace();
            return "exception";
        }
    }
}