package com.Aditya_DoctorConsultantProject.Aditya_DoctorConsultantProject.controller;

import com.Aditya_DoctorConsultantProject.Aditya_DoctorConsultantProject.vmm.DBLoader;
import com.Aditya_DoctorConsultantProject.Aditya_DoctorConsultantProject.vmm.RDBMS_TO_JSON;
import com.mysql.cj.protocol.ServerSessionStateController;
import jakarta.servlet.http.HttpSession;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class Doctor_RestController 
{
 @PostMapping("specialitylist")
 public String specialitylist()
 {
     String ans=new RDBMS_TO_JSON().generateJSON("select * from speciality");
     return ans;
 }
 @PostMapping("adddoctor")
 public String adddoctor(@RequestParam String name,@RequestParam String contact,@RequestParam String email,@RequestParam String address,@RequestParam String password,@RequestParam String confpass,@RequestParam String speciality,@RequestParam String city,@RequestParam String latitude,@RequestParam String longitude,@RequestParam String start_time,@RequestParam String end_time,@RequestParam String slot_amount,@RequestParam String desc,@RequestParam String experience,@RequestParam String educ,@RequestParam  MultipartFile photo )
    {
        System.out.println("In Rest Controller");
        String projectPath= System.getProperty("user.dir");
        String internalPath= "/src/main/resources/static";
        String folderName= "/myUploads";
        String orgName= "/" + photo.getOriginalFilename(); 
        
        int sp = Integer.parseInt(speciality);
        int sa = Integer.parseInt(slot_amount);
        System.out.println("---------------------------");
        System.out.println(sp);
        System.out.println(slot_amount);
        String sname="";
        try
        {
            ResultSet rs2=DBLoader.executeSQL("select * from speciality where id='"+speciality+"' ");
            if(rs2.next())
            {
                sname=rs2.getString("sname");
            }
        
        
        try {
            ResultSet rs=DBLoader.executeSQL("select * from doctor where dname='"+name+"'" );
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
                rs.updateString("dname", name);
                rs.updateString("demail", email);
                rs.updateString("dpass", password);
                rs.updateInt("dspeciality", sp);
                rs.updateString("dcity", city);
                rs.updateString("dlatitude", latitude);
                rs.updateString("dlongitude", longitude);
                rs.updateString("dphoto", orgName);
                rs.updateString("dstart_time", start_time);
                rs.updateString("dend_time", end_time);
                rs.updateInt("dslot_amount", sa);
                rs.updateString("dcontact", contact);
                rs.updateString("ddesc", desc);
                rs.updateString("dexperience", experience);
                rs.updateString("deducation", educ);
                rs.updateString("dspecialityname", sname);
                rs.updateString("daddress", address);
                
                
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
        catch(Exception ex)
        {
            ex.printStackTrace();
            return ex.toString();
        }
    }
  @PostMapping("/dlogin")
    public String dlogin(@RequestParam String email2,@RequestParam String pass2,HttpSession session)
    {
        try {
            ResultSet rs=DBLoader.executeSQL("select * from doctor where demail='"+email2+"' and dpass='"+pass2+"'");
            if(rs.next())
            {
                int id=rs.getInt("did");
                session.setAttribute("did", id);
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
     @PostMapping("/addphoto")
    public String addphoto(@RequestParam  MultipartFile Photo,@RequestParam String Desc,HttpSession session)
    {
        String projectPath= System.getProperty("user.dir");
        String internalPath= "/src/main/resources/static";
        String folderName= "/myUploads";
        String orgName= "/" + Photo.getOriginalFilename(); 
        try {
            ResultSet rs=DBLoader.executeSQL("select * from photo");
            if(rs.next())
            {
               
            
              FileOutputStream fos= new FileOutputStream(projectPath + internalPath + folderName + orgName);
            
                byte[] b1= Photo.getBytes();

                fos.write(b1);
                fos.close();
                Integer id=(Integer)session.getAttribute("did");
                rs.moveToInsertRow();
                rs.updateString("photo", orgName);
                rs.updateString("pdesc", Desc);
                rs.updateInt("did", id);
                rs.insertRow();
                return"Added Successfully";
            }
            else{
                return "Failed";
            }
        }
        catch (Exception ex) 
        {
            ex.printStackTrace();
            return "exception";
        }
        
    }
    @PostMapping("/showphoto")
    public String showphoto(HttpSession session)
    {
        Integer id=(Integer)session.getAttribute("did");
        String ans=new RDBMS_TO_JSON().generateJSON("select * from photo where did='"+id+"'");
        return ans;
    }
    @GetMapping("/deletephoto")
    public String deletephoto(@RequestParam String pid)
    {
        try
        {
        ResultSet rs=DBLoader.executeSQL("select * from photo where pid ='"+pid+"'");
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
    @PostMapping("/showdetail")
    public String showdetail(HttpSession session)
    {
        Integer id=(Integer)session.getAttribute("did");
        String ans=new RDBMS_TO_JSON().generateJSON("select * from doctor where did='"+id+"'");
        return ans;
    }
    @PostMapping("editdetail")
 public String editdetail(@RequestParam String name,@RequestParam String contact,@RequestParam String speciality,@RequestParam String city,@RequestParam String latitude,@RequestParam String longitude,@RequestParam String start_time,@RequestParam String end_time,@RequestParam String slot_amount,@RequestParam String desc,@RequestParam String experience,@RequestParam String educ,HttpSession session)
    {
        
        int sa = Integer.parseInt(slot_amount);
        System.out.println("---------------------------");
        
        System.out.println(slot_amount);
        
        try {
             Integer id=(Integer)session.getAttribute("did");
            ResultSet rs=DBLoader.executeSQL("select * from doctor where did='"+id+"'" );
            if(rs.next())
            {
                rs.moveToCurrentRow();
                rs.updateString("dname", name);
               
                rs.updateString("dcity", city);
                rs.updateString("dlatitude", latitude);
                rs.updateString("dlongitude", longitude);
                
                rs.updateString("dstart_time", start_time);
                rs.updateString("dend_time", end_time);
                rs.updateInt("dslot_amount", sa);
                rs.updateString("dcontact", contact);
                rs.updateString("ddesc", desc);
                rs.updateString("dexperience", experience);
                rs.updateString("deducation", educ);
                
                
                rs.updateRow();
                return"Success";
            }
            else
            {
             
               return "failed";
            }
        } 
        catch (Exception ex) 
        {
            ex.printStackTrace();
            return ex.toString();
        }
        
    }
 @PostMapping("/showbooking")
 public String showbooking(HttpSession session)
 {
    Integer id = (Integer) session.getAttribute("did");
    String ans= new RDBMS_TO_JSON().generateJSON("SELECT  * FROM booking JOIN booking_detail ON booking.booking_id = booking_detail.booking_id JOIN user ON booking.uemail = user.uemail where booking.did="+id+";");
    return ans;
 }
 @PostMapping("/showbooking2")
public String showbooking2(HttpSession session) {
    Integer id = (Integer) session.getAttribute("did");
    try {
        ResultSet rs = DBLoader.executeSQL("SELECT * FROM booking JOIN booking_detail ON booking.booking_id = booking_detail.booking_id JOIN user ON booking.uemail = user.uemail WHERE booking.did=" + id + ";");

        StringBuilder json = new StringBuilder();
        json.append("[");

        boolean firstBooking = true;
        int bid = -1;

        while (rs.next()) {
            int booking_id = rs.getInt("booking_id");
            String uname = rs.getString("uname");
            String date = rs.getString("date");
            String total_price = rs.getString("total_price");
            String ugender = rs.getString("ugender");
            String status = rs.getString("status");
            String payment_type = rs.getString("payment_type");

            if (bid != booking_id) {
                bid = booking_id;

                if (!firstBooking) {
                    json.append(",");
                }
                firstBooking = false;

                json.append("{");
                json.append("\"uname\":\"").append(uname).append("\",");
                json.append("\"date\":\"").append(date).append("\",");
                json.append("\"total_price\":\"").append(total_price).append("\",");
                json.append("\"ugender\":\"").append(ugender).append("\",");
                json.append("\"status\":\"").append(status).append("\",");
                json.append("\"booking_id\":").append(booking_id).append(",");

                // Handle slots
                StringBuilder json2 = new StringBuilder();
                try {
                    json2.append("[");
                    ResultSet rs2 = DBLoader.executeSQL("SELECT * FROM booking_detail WHERE booking_id=" + booking_id);

                    boolean firstSlot = true;
                    while (rs2.next()) {
                        String slot = rs2.getString("start_slot") + "-" + rs2.getString("end_slot");

                        if (!firstSlot) {
                            json2.append(",");
                        }
                        firstSlot = false;

                        json2.append("{");
                        json2.append("\"slot\":\"").append(slot).append("\"");
                        json2.append("}");
                    }
                    json2.append("]");
                } catch (Exception ex) {
                    return ex.toString();
                }

                json.append("\"slot\":").append(json2.toString()).append(",");
                json.append("\"payment_type\":\"").append(payment_type).append("\"");
                json.append("}");
            }
        }

        json.append("]"); // 👈 Important to close the JSON array

        String ans = json.toString();
        System.out.println(ans);
        return ans;

    } catch (Exception ex) {
        return ex.toString();
    }
}
@GetMapping("/showSlots")
public String showSlots(@RequestParam int bid)
{
    String ans=new RDBMS_TO_JSON().generateJSON("select * from booking_detail where booking_id="+bid+"");
    return ans;
}
 @PostMapping("/acceptbooking")
    public String acceptbooking(@RequestParam int id) {
        try {
            ResultSet rs= DBLoader.executeSQL("select * from booking where booking_id= "+ id+"");
            if(rs.next()) {
                rs.updateString("status", "accepted");
                rs.updateRow();
                return "Success";
            }
            else {
                return "Failed";
            }
        }
        catch(Exception ex) {
            return ex.toString();
        }
    }
    @PostMapping("/dchangepass")
    public String dchangepass(@RequestParam String pass1,@RequestParam String pass2,@RequestParam String pass3,HttpSession session)
    {
        Integer id=(Integer) session.getAttribute("did");
        try {
            ResultSet rs=DBLoader.executeSQL("select * from doctor where did='"+id+"' and dpass='"+pass1+"'");
            if(rs.next())
            {
                rs.moveToCurrentRow();
                rs.updateString("dpass", pass2);
                rs.updateRow();
                session.removeAttribute("did");
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


