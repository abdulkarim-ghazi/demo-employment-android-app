<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.io.OutputStreamWriter"%>
<%@page import="java.io.OutputStreamWriter"%>
<%@page import="java.io.OutputStreamWriter"%>
<%@page import="java.io.IOException"%>
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%>

<?xml version="1.0"?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.2//EN"
"http://www.wapforum.org/DTD/wml12.dtd">

<wml>

<card title="wml_registerCompany">
<p>
     <anchor>
<big color="blue">welcome</big><go  href="wml_login.jsp"/>
     </anchor>
    <br />
    <small>
        You can register  your business here</small>
</p>
  <br />

<p>
<%!
    String cName=null;
       String login=null; 
       String password=null;
       String tel=null;
    %>
    <%
        try{
 
            cName=request.getParameter("cName");
            login=request.getParameter("login");
            tel=request.getParameter("tel");
            password=request.getParameter("password");
           
        }catch(Exception e){}
   
             try{ 
              PreparedStatement prp;   
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/myjob","root",""); 
         
            String sq ="INSERT INTO `company` ( `cName`, `login`, `password`, `tel`) VALUES (?,?,?,?);";
           prp = con.prepareStatement(sq);
            prp.setString(1, cName);
             prp.setString(2, login);
              prp.setString(3, password);
               prp.setString(4, tel);
            
              
        prp.executeUpdate();
              
           out.println("<h3 color='green'>your request is added successfully</h3>");
        }
        catch(SQLException | ClassNotFoundException e){  
        
       
        
        } 
  

         
   

        %>
</p>
<br />
<p>
    
   Company Name: <input name="cName" size="12"/>
   login Name : <input name="login" size="12"/>
    password : <input name="password" size="12"/>
     tel : <input name="tel" size="12"/>
 <anchor>
      <go method="get" href="wml_registerCompany.jsp">
          <postfield name="cName" value="$(cName)"/>
          <postfield name="login" value="$(login)"/>
             <postfield name="password" value="$(password)"/>
                <postfield name="tel" value="$(tel)"/>
             
      
      </go>
      Submit Data
  </anchor>
</p>


<p>
   </p>
</card>

</wml>