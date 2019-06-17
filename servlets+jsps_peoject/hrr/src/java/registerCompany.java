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
@WebServlet(name = "registerCompany", urlPatterns = {"/registerCompany"})
public class registerCompany extends HttpServlet {

   @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      
    }
    
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      
       JSONArray jsar;
       JSONObject jsor;
       
       JSONArray jsas;
       JSONObject jsos;
       String cName=null;
       String login=null; 
       String password=null;
       String tel=null;
    
    try {
            StringBuilder sb = new StringBuilder();
            BufferedReader  br =request.getReader();
            String str;
              while( (str = br.readLine()) != null ){
                sb.append(str);
              }     
            response.setStatus(HttpServletResponse.SC_OK);
           jsor=new JSONObject();
             jsar=new JSONArray(sb.toString());
             jsor=  jsar.getJSONObject(0);
            cName=jsor.getString("cName");
            login=jsor.getString("login");
            password=jsor.getString("password");
            tel=jsor.getString("tel");
           
        
                
            //sending
            jsas=new JSONArray();
             jsos=new JSONObject(); 
        try{ 
              PreparedStatement prp;   
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://node1055-env-8258411.adaptainer.io/myjob","root","LIXcmb23716"); 
         
           String sq ="INSERT INTO `company` ( `cName`, `login`, `password`, `tel`) VALUES (?,?,?,?);";
           prp = con.prepareStatement(sq);
            prp.setString(1, cName);
             prp.setString(2, login);
              prp.setString(3, password);
               prp.setString(4, tel);
            
              
        prp.executeUpdate();
              
             jsos.put("state","success");
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

   