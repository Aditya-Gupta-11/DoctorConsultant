
package com.Aditya_DoctorConsultantProject.Aditya_DoctorConsultantProject.controller;

import com.Aditya_DoctorConsultantProject.Aditya_DoctorConsultantProject.vmm.DBLoader;
import com.Aditya_DoctorConsultantProject.Aditya_DoctorConsultantProject.vmm.RDBMS_TO_JSON;
import jakarta.servlet.http.HttpSession;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class User_RestController 
{
    @PostMapping("adduser")
 public String adduser(@RequestParam String name,@RequestParam String contact,@RequestParam String email,@RequestParam String password,@RequestParam String confpass,@RequestParam String address,@RequestParam String gender,@RequestParam String dob,@RequestParam String bloodgroup,@RequestParam  MultipartFile photo )
    {
        System.out.println("In Rest Controller");
        String projectPath= System.getProperty("user.dir");
        String internalPath= "/src/main/resources/static";
        String folderName= "/myUploads";
        String orgName= "/" + photo.getOriginalFilename(); 
      
        
        try {
            ResultSet rs=DBLoader.executeSQL("select * from user where uname='"+name+"'" );
            if(rs.next())
            {
                return "Failed";
            }
            else
            {
              FileOutputStream fos= new FileOutputStream(projectPath + internalPath + folderName + orgName);
            
                byte[] b1= photo.getBytes();

                fos.write(b1);
                fos.close();
                rs.moveToInsertRow();
                rs.updateString("uname", name);
                rs.updateString("uemail", email);
                rs.updateString("upass", password);
                rs.updateString("ucontact", contact);
                rs.updateString("uaddress", address);
                rs.updateString("ugender", gender);
                rs.updateString("udob", dob);
                rs.updateString("ubloodgroup", bloodgroup);
                rs.updateString("uphoto", orgName);
              
                
                
                rs.insertRow();
                return"Success";
            }
        } 
        catch (Exception ex) 
        {
            ex.printStackTrace();
            return ex.toString();
        }
        
    }
  @PostMapping("/ulogin")
    public String ulogin(@RequestParam String email2,@RequestParam String pass2)
    {
        try {
            ResultSet rs=DBLoader.executeSQL("select * from user where uemail='"+email2+"' and upass='"+pass2+"'");
            if(rs.next())
            {
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
    @PostMapping("/getcity")
    public String getcity(@RequestParam String cityname, HttpSession session2)
    {
        String cn= cityname;
        session2.setAttribute(cn, cityname);
        return "success";
    }
    @PostMapping("/getspeciality")
    public String getspeciality(@RequestParam String city)
    {
        String ans=new RDBMS_TO_JSON().generateJSON("SELECT DISTINCT s.* FROM speciality s JOIN doctor d ON s.sname = d.dspecialityname WHERE d.dcity ='"+city+"' ");
        return ans;
    }
}
