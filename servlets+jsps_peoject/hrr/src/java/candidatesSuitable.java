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
@WebServlet(name = "candidatesSuitable", urlPatterns = {"/candidatesSuitable"})
public class candidatesSuitable extends HttpServlet {
JSONObject jobOB;
JSONArray jobArA;
Integer id=0;
Integer experienceYears;
String requiredcertifate;   
Integer jobId;
    String[] Certifications;
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
           Certifications=new String[10]; 
    try {
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
         
     // dddddddddddddddata    base search
     
   jobId= pickFirstJob(id);  
//      
      ResultSet rs;
     try{ 
         PreparedStatement prp;
                  
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://node1055-env-8258411.adaptainer.io/myjob","root","LIXcmb23716"); 
           
            String sq =("select * from candidate WHERE  experienceYears > ? ");
          prp = con.prepareStatement(sq);
        int j=0;
            prp.setString(1,experienceYears.toString());
         rs= prp.executeQuery();
         while (rs.next())
                   {
                    Certifications= checkingCertification(rs.getInt(1));
                try
                {
                    for(int i=0;i<Certifications.length;i++)
                    {
                       
                       if(requiredcertifate.equalsIgnoreCase(Certifications[i]))
                       {
                jobOB=new JSONObject();
                      
                                  jobOB.put("candidate", rs.getString(4));
                             
                                  j++; 
                            
                                jobArA.put(jobOB);
                     }
                    
                    }
                }
                    catch(Exception e){}       
                    }
          }
        catch(SQLException e){} 
        catch(ClassNotFoundException e){} 
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
    }  } 
    //retrieving data from database
     
      
       private Integer pickFirstJob(Integer id){
      ResultSet rs;  
 
      try{ 
          PreparedStatement prp;
              
            Class.forName("com.mysql.jdbc.Driver");
            Connection con1 = DriverManager.getConnection("jdbc:mysql://node1055-env-8258411.adaptainer.io/myjob","root","LIXcmb23716"); 
            Statement st= con1.createStatement();
            String sq =("select * from job where companyId = ? ");
          prp = con1.prepareStatement(sq);
          prp.setString(1,id.toString());
         rs= prp.executeQuery();
            if (rs.next())
{
       requiredcertifate=rs.getString(5);
         experienceYears= rs.getInt(6);
        
            return rs.getInt(1);
}    
}
     
        catch(SQLException e){            
              return null;     
        } 
        catch(ClassNotFoundException e){return null;} 
   
      return null;
    }

   
    private String[] checkingCertification(Integer cid){
      ResultSet rs1;  
  String[] Certifications=new String[10];
      try{ 
          PreparedStatement prp1;
               int j=0;
            Class.forName("com.mysql.jdbc.Driver");
            Connection con2 = DriverManager.getConnection("jdbc:mysql://node1055-env-8258411.adaptainer.io/myjob","root","LIXcmb23716"); 
            Statement st= con2.createStatement();
            String sq =("select * from certificate where candidateId = ?");
          prp1 = con2.prepareStatement(sq);
          prp1.setString(1,cid.toString());
         rs1= prp1.executeQuery();
            while (rs1.next())
                   {
                     Certifications[j]= rs1.getString(3);; 
                     j++;
                         
                    }
               
            return Certifications;
        }
     
        catch(SQLException e){            
               return null;     
        } 
        catch(ClassNotFoundException e){ 
    
      return null;
    }
 
       
   }



}

   