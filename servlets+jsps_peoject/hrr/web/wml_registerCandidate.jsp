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

<card title="wml_registerCandidate">
<p>
 <anchor>
<big color="blue">welcome</big><go  href="wml_login.jsp"/>
     </anchor>
    <br />
You can register  to get job offers
</p>

<p>
<%!
    String fullName=null;
       String password=null; 
       String experienceYears=null;
       String mobile=null;
       String login=null;
    %>
    <%
        try{
 
            fullName=request.getParameter("fullName");
            password=request.getParameter("password");
            experienceYears=request.getParameter("experienceYears");
            mobile=request.getParameter("mobile");
            login=request.getParameter("login");
        }catch(Exception e){}
   
             try{ 
              PreparedStatement prp;   
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://node1055-env-8258411.adaptainer.io/myjob","root","LIXcmb23716"); 
         
           String sq ="INSERT INTO `candidate` ( `fullName`, `password`, `experienceYears`, `mobile`,`login`) VALUES (?,?,?,?,?);";
           prp = con.prepareStatement(sq);
            prp.setString(1, fullName);
             prp.setString(2, password);
              prp.setString(3, experienceYears);
               prp.setString(4, mobile);
                prp.setString(5, login);
              
        prp.executeUpdate();
              
             out.println("<h3 color='green'>your request is added successfully</h3>");
        }
        catch(SQLException | ClassNotFoundException e){  
        
  
        
        } 
  

         
   

        %>
</p>

<p>
    
   full Name: <input name="fullName" size="12"/>
   password : <input name="password" size="12"/>
    mobile : <input name="mobile" size="12"/>
     experience Years : <input name="experienceYears" size="12"/>
      login name : <input name="login" size="12"/>
 <anchor>
      <go method="get" href="wml_registerCandidate.jsp">
          <postfield name="fullName" value="$(fullName)"/>
          <postfield name="password" value="$(password)"/>
             <postfield name="mobile" value="$(mobile)"/>
                <postfield name="experienceYears" value="$(experienceYears)"/>
                <postfield name="login" value="$(login)"/>
      
      </go>
      Submit Data
  </anchor>
</p>

<p>
   </p>
</card>

</wml>