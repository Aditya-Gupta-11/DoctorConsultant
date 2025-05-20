
package com.Aditya_DoctorConsultantProject.Aditya_DoctorConsultantProject.controller;

import com.Aditya_DoctorConsultantProject.Aditya_DoctorConsultantProject.vmm.DBLoader;
import com.Aditya_DoctorConsultantProject.Aditya_DoctorConsultantProject.vmm.RDBMS_TO_JSON;
import jakarta.servlet.http.HttpSession;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.util.StringTokenizer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class User_RestController 
{
    @Autowired
    public EmailSenderService email;
    
    
    
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
    public String ulogin(@RequestParam String email2,@RequestParam String pass2,HttpSession session)
    {
        try {
            ResultSet rs=DBLoader.executeSQL("select * from user where uemail='"+email2+"' and upass='"+pass2+"'");
            if(rs.next())
                
            {
                session.setAttribute("uemail", email2);
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
        String ans=new RDBMS_TO_JSON().generateJSON("SELECT DISTINCT * FROM speciality s JOIN doctor d ON s.sname = d.dspecialityname WHERE d.dcity ='"+city+"' ");
        return ans;
    }
    @PostMapping("/getdoctor")
    public String getdoctor(@RequestParam String city,@RequestParam int sid)
    {
        String ans=new RDBMS_TO_JSON().generateJSON("SELECT DISTINCT * FROM speciality s JOIN doctor d ON s.sname = d.dspecialityname WHERE d.dcity ='"+city+"' and s.id='"+sid+"' ");
        return ans;
    }
     @PostMapping("/getdoctor1")
    public String getdoctor1()
    {
        String ans=new RDBMS_TO_JSON().generateJSON("SELECT DISTINCT * FROM speciality s JOIN doctor d ON s.sname = d.dspecialityname    ");
        return ans;
    }
    @PostMapping("/doctordetail")
    public String doctordetail(@RequestParam int did)
    {
        String ans=new RDBMS_TO_JSON().generateJSON("select * from doctor where did='"+did+"' ");
        return ans;
    }
    @PostMapping("/showgallery")
    public String showgallery(@RequestParam String did)
    {
        String ans=new RDBMS_TO_JSON().generateJSON("select * from photo where did='"+did+"'");
        return ans;
    }
    
    
    @GetMapping("/view_slots")
    String view_slots(@RequestParam String did, @RequestParam String date) {

        System.out.println(date);
        System.out.println(did);
        try {
            ResultSet rs = DBLoader.executeSQL("select * from doctor where did='" + did + "'");

            String start;
            String end;
            String slot;
            if (rs.next()) {
                start = rs.getString("dstart_time");
                end = rs.getString("dend_time");
                slot = rs.getString("dslot_amount");

            } else {
                String err = "failed";
                return err;
            }
            int Start = Integer.parseInt(start);
            int End = Integer.parseInt(end);
            int Slot = Integer.parseInt(slot);
            JSONObject ans = new JSONObject();

            //Define JSONArray
            JSONArray arr = new JSONArray();
            for (int i = Start; i <= End; i++) {
                JSONObject row = new JSONObject();
                row.put("start_slot", Start);
                row.put("end_slot", ++Start);
                row.put("slot_amount", slot);

                ResultSet rs2 = DBLoader.executeSQL("select * from booking_detail where start_slot ='" + i + "' and booking_id in (select booking_id from booking where date=\'" + date + "\' and did =\'" + did + "\' ) ");
                if (rs2.next()) {
                    row.put("status", "Booked");
                } else {
                    row.put("status", "Available");
                }

                arr.add(row);
            }
            ans.put("ans", arr);
            System.out.println(ans.toString());
            return (ans.toJSONString());

        } catch (Exception e) {
            return e.toString();
        }

    }
    
    
    
    @GetMapping("/pay")
    public String payment(@RequestParam String date,
            @RequestParam String did,
            @RequestParam String amount,
            @RequestParam String slots,
            HttpSession session,
            @RequestParam String type,
            @RequestParam String status) {
        String ans = "";
        String user_email = (String) session.getAttribute("uemail");

        try {
            // 1. Insert into booking table
            ResultSet rs = DBLoader.executeSQL("SELECT * FROM booking");
            rs.moveToInsertRow();
            rs.updateString("date", date);
            rs.updateString("did", did);
            rs.updateString("uemail", user_email);
            rs.updateString("total_price", amount);
            rs.updateString("payment_type", type);
            rs.updateString("status", status);
            rs.insertRow();

            // 2. Get inserted booking_id
            int booking_id = 0;
            ResultSet rs2 = DBLoader.executeSQL("SELECT MAX(booking_id) AS maxid FROM booking");
            if (rs2.next()) {
                booking_id = rs2.getInt("maxid");
            }

            // 3. Insert slots into booking_detail table
            StringTokenizer st = new StringTokenizer(slots, ",");
            while (st.hasMoreTokens()) {
                int start_slot = Integer.parseInt(st.nextToken());
                int end_slot = start_slot + 1;

                ResultSet rs3 = DBLoader.executeSQL("SELECT * FROM booking_detail");
                rs3.moveToInsertRow();
                rs3.updateInt("booking_id", booking_id); // fk to booking_id
                rs3.updateString("start_slot", String.valueOf(start_slot));
                rs3.updateString("end_slot", String.valueOf(end_slot));
                rs3.insertRow();
            }

            ans = "success";
            return ans;
        } catch (Exception ex) {
            return ex.toString();
        }
    }
    @PostMapping("/showuserbooking")
    public String showuserbooking(HttpSession session)
    {
        String id =  (String) session.getAttribute("uemail");
        String ans= new RDBMS_TO_JSON().generateJSON("SELECT  booking.*, doctor.dname, doctor.dspecialityname, doctor.dcontact,user.uid, user.uname FROM booking JOIN booking_detail ON booking.booking_id = booking_detail.booking_id JOIN doctor ON booking.did = doctor.did JOIN user ON booking.uemail = user.uemail where booking.uemail='"+id+"'");
        return ans;
  
    }
     @PostMapping("/changepass")
    public String changepass(@RequestParam String pass1,@RequestParam String pass2,@RequestParam String pass3,HttpSession session)
    {
        String email=(String) session.getAttribute("uemail");
        try {
            ResultSet rs=DBLoader.executeSQL("select * from user where uemail='"+email+"' and upass='"+pass1+"'");
            if(rs.next())
                
            {
                rs.moveToCurrentRow();
                rs.updateString("upass", pass2);
                rs.updateRow();
                session.removeAttribute("uemail");
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
    @GetMapping("/userAddReview")
    public String userAddReview(@RequestParam int did, @RequestParam int rating, @RequestParam String comment, HttpSession session) {
        String uemail = (String) session.getAttribute("uemail");
        System.out.println(uemail);
//        System.out.println(rating);
        String ans = "";
        try {
            ResultSet rs = DBLoader.executeSQL("Select * from review_table");

            rs.moveToInsertRow();
            rs.updateInt("did", did);
            rs.updateString("uemail", uemail);
            rs.updateString("comment", comment);
            rs.updateInt("rating", rating);
            rs.insertRow();
            ans = "success";

        } catch (Exception e) {
            ans = e.toString();
        }

        return ans;
    }


    @GetMapping("/userShowAverageRatings")
    public String userShowAverageRatings(@RequestParam int did) {

        // Assuming RDBMS_TO_JSON is available as a service or component
        String ans = new RDBMS_TO_JSON().generateJSON("select avg(rating) as r1 from review_table where did='" + did + "' ");
        System.out.println(ans);
        return ans;

    }

    @GetMapping("/userShowRatings")
    public String userShowRatings(@RequestParam int did) {

        // Assuming RDBMS_TO_JSON is available as a service or component
        String ans = new RDBMS_TO_JSON().generateJSON("select * from review_table where did='" + did + "' ");
        System.out.println(ans);
        return ans;

    } 
    
    
    @GetMapping("/sendemail")   
    public String sendemail()
    {
        this.email.sendSimpleEmail("adityagupta0852@gmail.com", "Hello Everyone this is email testing mode", "Email Testing");
        return "success";
    }

}
