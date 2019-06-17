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
@WebServlet(name = "searchAllJobOffers", urlPatterns = {"/searchAllJobOffers"})
public class searchAllJobOffers extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
     JSONObject jsonSendO;
     JSONArray jsonSendA =new JSONArray();
       String choose=null;
    try {
            StringBuilder sb = new StringBuilder();
            BufferedReader  br =request.getReader();
            String str;
              while( (str = br.readLine()) != null ){
                sb.append(str);
              }     
           String result =sb.toString();
            response.setStatus(HttpServletResponse.SC_OK);
            OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());
            JSONArray jsar=new JSONArray(result);
            JSONObject jj=  jsar.getJSONObject(0);
            choose=jj.getString("choose");
           
              ResultSet rs;  
 
      try{ 
         
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://node1055-env-8258411.adaptainer.io/myjob","root","LIXcmb23716"); 
            Statement st= con.createStatement();
            String sq =("SELECT * FROM job ORDER BY %s");
         sq=String.format(sq, choose);
         rs= st.executeQuery(sq);
            while (rs.next())
{
    
           jsonSendO=new JSONObject();
                      
                                  jsonSendO.put("job", rs.getString(3));
                             
                              
                            
                                jsonSendA.put(jsonSendO);
         
}
     
             }catch(SQLException | ClassNotFoundException e){} 
      //   ssssssssssssending response   
      writer.write(jsonSendA.toString());
      writer.flush();
       writer.close();
            
 }  catch (IOException e) {      
            try{
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().print(e.getMessage());
                response.getWriter().close();
            } catch (IOException ioe) {
                
            }
    }}}