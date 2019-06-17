/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author abdul
 */
@WebServlet(name = "jobsAvailable", urlPatterns = {"/jobsAvailable"})
public class jobsAvailable extends HttpServlet {
JSONObject jobOB;
    JSONArray jobArA;
String[] Certifications ;
 Integer id=0;
   String mobile=null;
   String Cname=null;
   String login=null;
   String passwordSend=null;
   String fullName=null;
   Integer experienceYears=null;
   String tel=null;
          @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        jobOB=new  JSONObject();
     jobArA=new JSONArray()  ;
 String type=null;
        Certifications=new String[5];
      
               
    try {
        
         int length = request.getContentLength();
            byte[] input = new byte[length];
            StringBuilder sb = new StringBuilder();
            BufferedReader  br =request.getReader();
            String str;
              while( (str = br.readLine()) != null ){
                sb.append(str);
              } 
            //getting parameters from json string
            response.setStatus(HttpServletResponse.SC_OK);
            
            String resault=sb.toString();
            JSONArray jArrAayRecieved=new JSONArray(sb.toString());
            JSONObject jObjectRecieved=  jArrAayRecieved.getJSONObject(0);
            id=jObjectRecieved.getInt("id");
            experienceYears=jObjectRecieved.getInt("experienceYears");
     // dddddddddddddddata    base search
     
    Certifications=checkingExper(id);  
       String aa=checkingJob(id,experienceYears,Certifications);  
  
           
      //   ssssssssssssending response   
    
           
            
              
             
   OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());
            writer.write(jobArA.toString());
            writer.flush();
            writer.close();
            
    } catch (IOException e) {      
            try{
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().print(e.getMessage());
                response.getWriter().close();
            } catch (IOException ioe) {
                
            }
    }   
    //retrieving data from database
     
    }
   
      private String[] checkingExper(Integer id){
      ResultSet rs1;  
  
      try{ 
          PreparedStatement prp1;
               int j=0;
            Class.forName("com.mysql.jdbc.Driver");
            Connection con1 = DriverManager.getConnection("jdbc:mysql://node1055-env-8258411.adaptainer.io/myjob","root","LIXcmb23716"); 
            Statement st= con1.createStatement();
            String sq =("select * from certificate where candidateId = ?");
          prp1 = con1.prepareStatement(sq);
          prp1.setString(1,id.toString());
         rs1= prp1.executeQuery();
            while (rs1.next())
                   {
                     Certifications[j]= rs1.getString(3);; 
                     j++;
                         
                    }
            return Certifications;
        }
     
        catch(SQLException e){            
                   
        } 
        catch(ClassNotFoundException e){} 
    Certifications[0]="error";
      return Certifications;
    }     
   
    
 private String checkingJob(Integer id,Integer experienceYears,String[] Certifications){
   ResultSet rs1;
     try{ 
         PreparedStatement prp1;
               int i=1;    
            Class.forName("com.mysql.jdbc.Driver");
            Connection con2 = DriverManager.getConnection("jdbc:mysql://node1055-env-8258411.adaptainer.io/myjob","root","LIXcmb23716"); 
           
            String sq =("select * from job WHERE  requiredExperienceYears <= ? ");
          prp1 = con2.prepareStatement(sq);
        
            prp1.setString(1,experienceYears.toString());
         rs1= prp1.executeQuery();
         while (rs1.next())
                   {
                      for(int j=0;j<Certifications.length;j++){
                      try{
                          if(rs1.getString(5).equalsIgnoreCase(Certifications[j]))
                              { 
                                  
                       jobOB=new JSONObject();
                      
                                  jobOB.put("job", rs1.getString(3));
                             
                                  i++; 
                            
                                jobArA.put(jobOB);
                                  
                              }
                         
                    
                      }  catch (Exception e)
                                  {
                                 
                                  }
                        
                               }
                     
                    }
        
        }
        catch(SQLException e){ 
              
              return "sqlnbnb";    
        } 
        catch(ClassNotFoundException e){} 
      return "no class";
    }     
    }

   