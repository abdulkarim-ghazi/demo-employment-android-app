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

<card title="Add Job">
<p>
<anchor>
<big color="blue">welcome</big><go  href="wml_login.jsp"/>
     </anchor>
    <br />
You can add  a job here
</p>

<p>
<%!
    String requiredEducationLevel=null; 
       String requiredExperienceYears=null;
       String salary=null;
       String title=null;
        Integer companyId=null;
    %>
    <%
        try{
         companyId=Integer.parseInt(request.getAttribute("id").toString());
        }catch(Exception e){}
        try{
  companyId=Integer.parseInt(request.getParameter("id"));
            requiredEducationLevel=request.getParameter("requiredEducationLevel");
            requiredExperienceYears=request.getParameter("requiredExperienceYears");
            salary=request.getParameter("salary");
            title=request.getParameter("title");
        }catch(Exception e){}
        
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
              
            out.println("<h3 color='green'>your request is added successfully</h3>");
        }
        catch(SQLException | ClassNotFoundException e){  
        

        
        } 
  

         
   

        %>
</p>
<p>
    
   Job title: <input name="title" size="12"/>
   salary : <input name="salary" size="12"/>
    requiredEducationLevel : <input name="requiredEducationLevel" size="12"/>
     requiredExperienceYears : <input name="requiredExperienceYears" size="12"/>
 
</p>
<p>
<anchor>
      <go method="get" href="wml_addJob.jsp">
          <postfield name="title" value="$(title)"/>
          <postfield name="salary" value="$(salary)"/>
             <postfield name="requiredEducationLevel" value="$(requiredEducationLevel)"/>
                <postfield name="requiredExperienceYears" value="$(requiredExperienceYears)"/>
                <postfield name="id" value="<%=companyId %>"/>
      
      </go>
      Submit Data
  </anchor>   

</p>
</card>

</wml>