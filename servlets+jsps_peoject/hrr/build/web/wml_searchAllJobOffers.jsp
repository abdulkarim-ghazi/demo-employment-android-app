<%@page import="javax.management.Query"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.io.IOException"%>
<%@page import="java.io.IOException"%>
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%>

<?xml version="1.0"?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.2//EN"
"http://www.wapforum.org/DTD/wml12.dtd">

<wml>

<card title="search All Job Offers">
 <anchor>
<big color="blue">welcome</big><go  href="wml_login.jsp"/>
     </anchor>

<p>
    
  
  <select  title="choose your filter" name="choose">
<option value="salary">salary</option>
<option value="requiredEducationLevel">requiredEducationLevel</option>
<option value="requiredExperienceYears">requiredExperienceYears</option>
</select>
</p>
<p><anchor>
      <go method="get" href="wml_searchAllJobOffers.jsp">
          <postfield name="choose" value="$(choose)"/>

      
      </go>
      Sort Data
  </anchor></p>

<p>
   <table border="1">
        <thead>
            <tr>
                <th>All Jobs Offers</th>
            </tr>
        </thead>
    
    <%!
        String choose="salary";
           ResultSet rs;  
String sq;
        %>
    <%
        
        
        try{
         choose=request.getParameter("choose").toString();
        } catch(Exception e){} 
           
 
      try{ 
         
             
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://node1055-env-8258411.adaptainer.io/myjob","root","LIXcmb23716"); 
            Statement st= con.createStatement();
             sq =("SELECT * FROM job ORDER BY %s");
         sq=String.format(sq, choose);
         rs= st.executeQuery(sq);
            while (rs.next())
{
    
     
                      
                               out.println("<tr><td>"+rs.getString(3)+"</td></tr>");
                             
                              
                            
                               
         
}
     
             }catch(SQLException | ClassNotFoundException e){} 
      //   ssssssssssssending response   
      
    
        %>
    
   
   
    </table>

    
    
    
</p>

</card>

</wml>