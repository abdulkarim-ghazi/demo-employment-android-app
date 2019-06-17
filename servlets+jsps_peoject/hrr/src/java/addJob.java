/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
@WebServlet(name = "addJob", urlPatterns = {"/addJob"})
public class addJob extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      
       JSONArray jsar=null;
       JSONObject jsor=null;
       
       JSONArray jsas=null;
       JSONObject jsos=null;
       Integer companyId=null;
       String requiredEducationLevel=null; 
       String requiredExperienceYears=null;
       String salary=null;
       String title=null;
    try {
            StringBuilder sb = new StringBuilder();
            BufferedReader  br =request.getReader();
            String str;
              while( (str = br.readLine()) != null ){
                sb.append(str);
              }     
            response.setStatus(HttpServletResponse.SC_OK);
         
             jsar=new JSONArray(sb.toString());
             jsor=  jsar.getJSONObject(0);
            companyId=jsor.getInt("companyId");
            requiredEducationLevel=jsor.getString("requiredEducationLevel");
            requiredExperienceYears=jsor.getString("requiredExperienceYears");
            salary=jsor.getString("salary");
            title=jsor.getString("title");
           
                
            //sending
            jsas=new JSONArray();
             jsos=new JSONObject(); 
        try{ 
              PreparedStatement prp;   
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://node1055-env-8258411.adaptainer.io/myjob","root","LIXcmb23716"); 
         
           String sq ="INSERT INTO `job` ( `companyId`, `requiredEducationLevel`, `requiredExperienceYears`, `salary`,`title`) VALUES (?,?,?,?,?);";
           prp = con.prepareStatement(sq);
            prp.setString(1, companyId.toString());
             prp.setString(2, requiredEducationLevel);
              prp.setString(3, requiredExperienceYears);
               prp.setString(4, salary);
                prp.setString(5, title);
              
        prp.executeUpdate();
              
             jsos.put("state", "success");
        }
        catch(SQLException | ClassNotFoundException e){  jsos.put("state", "sql_error");} 
  
    jsas.put(jsos);
            
            OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());

            
            writer.write(jsas.toString());
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
}

   