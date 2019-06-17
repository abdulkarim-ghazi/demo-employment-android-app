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

<card title="wml_addCertifiate">
<p>
<anchor>
<big color="blue">welcome</big><go  href="wml_login.jsp"/>
     </anchor>
    <br />
You can register  to get job offers
</p>
<p>
    
   diploma Title <input name="diplomaTitle" size="12"/>
   
 
</p>
<p>
<%!
   Integer candidateId=null;
       String diplomaTitle=null; 
    %>
    <%
          try{
        candidateId=Integer.parseInt(request.getAttribute("id").toString());
        }catch(Exception e){}
        
        try{
        candidateId=Integer.parseInt(request.getParameter("id"));
         diplomaTitle=request.getParameter("diplomaTitle");
          }catch(Exception e){}
       
 
             try{ 
              PreparedStatement prp;   
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://node1055-env-8258411.adaptainer.io/myjob","root","LIXcmb23716"); 
         
              String sq ="INSERT INTO `certificate` ( `candidateId`, `diplomaTitle`) VALUES (?,?);";
           prp = con.prepareStatement(sq);
            prp.setString(1, candidateId.toString());
             prp.setString(2, diplomaTitle);
              
              
        prp.executeUpdate();
              
           out.println("<h3 color='green'>your request is added successfully</h3>");
        }
        catch(SQLException | ClassNotFoundException e){  
        
     
        
        } 
  

   
   

        %>
</p>
<p><anchor>
      <go method="get" href="wml_addCertifiate.jsp">
          <postfield name="diplomaTitle" value="$(diplomaTitle)"/>
          <postfield name="id" value="<%= candidateId%>"/>
             
      
      </go>
      Submit Data
  </anchor></p>
</card>

</wml>