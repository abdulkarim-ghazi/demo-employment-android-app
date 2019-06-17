
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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

/* @author Abdul Karim Ghazi */
public class login extends HttpServlet {
   Integer id=0;
   String mobile=null;
   String Cname=null;
   String login=null;
   String passwordSend=null;
   String fullName=null;
   String experienceYears=null;
   String tel=null;
          @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
 String type=null;
       String userName=null;
       String password=null;
       
    try {
        
         int length = request.getContentLength();
            byte[] input = new byte[length];
            StringBuilder sb = new StringBuilder();
            BufferedReader  br =request.getReader();
            String str;
              while( (str = br.readLine()) != null ){
                sb.append(str);
              } 
            
            response.setStatus(HttpServletResponse.SC_OK);
            JSONArray jArrAayRecieved=new JSONArray(sb.toString());
            JSONObject jObjectRecieved=  jArrAayRecieved.getJSONObject(0);
            userName=jObjectRecieved.getString("username");
            password=jObjectRecieved.getString("password");
     // dddddddddddddddata    base search
        JSONObject jObjectSend=  new JSONObject();
     Integer res=checkingCompany(userName,password);  
         
      if(res==0)
      {
     res=checkingCandidate(userName,password);
     type="candidate";
   
  jObjectSend.put("login", login);
   jObjectSend.put("passwordSend", passwordSend);
   jObjectSend.put("fullName", tel);
   jObjectSend.put("mobile", mobile);
     jObjectSend.put("experienceYears", experienceYears);
   
      }
      else
      {
       
           type="company";
 
      jObjectSend.put("Cname", Cname);
   jObjectSend.put("login", login);
   jObjectSend.put("passwordSend", passwordSend);
   jObjectSend.put("tel", tel);
      }
           
      //   ssssssssssssending response   
           
            jObjectSend.put("isRegister",res);
             jObjectSend.put("id",id);
              jObjectSend.put("type",type.toString());
            
              JSONArray jArrAaySend=new  JSONArray();
              jArrAaySend.put(jObjectSend);
             
   OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());
            writer.write(jArrAaySend.toString());
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
      private Integer checkingCompany(String userName,String password){
      ResultSet rs;  
          try{ 
          PreparedStatement prp;
               
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://node1055-env-8258411.adaptainer.io/myjob","root","LIXcmb23716"); 
            Statement st= con.createStatement();
            String sq =("select * from company where login LIKE ?");
          prp = con.prepareStatement(sq);
          prp.setString(1,userName);
         rs= prp.executeQuery();
            if(rs.next())
                   {
                         if(rs.getString(4).equals(password))
                              { 
                               id=rs.getInt(1);
                               Cname=rs.getString(2);
                               login=rs.getString(3);
                               passwordSend=rs.getString(4);
                               tel=rs.getString(5);
                                con.close();   
                               return 1;
                               
                              }
                         else
                              {
                                   con.close();
                                  return 0;
                               }
                    }
            else {
            
                                     con.close();
                                  return 0;
            }
        }
        catch(SQLException e){            
                   return 0;
        } 
        catch(ClassNotFoundException e){} 
    return 0;
    }     
   
    
 private Integer checkingCandidate(String userName1,String password1){
   ResultSet rs;
     try{ 
         PreparedStatement prp;
                   
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://node1055-env-8258411.adaptainer.io/myjob","root","LIXcmb23716"); 
           
            String sq =("select * from candidate where login LIKE ?");
          prp = con.prepareStatement(sq);
          prp.setString(1,userName1);
         rs= prp.executeQuery();
          if(rs.next())
                   {
                         if(rs.getString(3).equalsIgnoreCase(password1))
                              { 
                               id=rs.getInt(1);
                               login=rs.getString(2);
                               passwordSend=rs.getString(3);
                               fullName=rs.getString(4);
                                mobile=rs.getString(5);
                                experienceYears=rs.getString(6);
                                     con.close();
                                  return 1;
                              
                               }
                         else
                              {
                                      con.close();
                                  return 0;
                               }
                    }
          else{
          
                                      con.close();
                                  return 0;
          }
        }
        catch(SQLException e){ 
              
           return 4;      
        } 
        catch(ClassNotFoundException e){} 
   return 3;
    }     
    }

   