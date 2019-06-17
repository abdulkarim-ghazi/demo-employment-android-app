<%-- 
    Document   : newjsp
    Created on : Jun 26, 2017, 11:14:59 AM
    Author     : abdul
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.SQLException"%>
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%>
 <%! 
      PreparedStatement prp;
   Integer experienceYears=null;
   String requiredcertifate=null;
    String target;
  Integer jobId=null;
    Integer id=null;
   String[] Certifications =new String[10];
   String nextPage=null;
    RequestDispatcher rd ; 
 ResultSet rs;
 %>
<%!    


%>
<%!    
    private String[] checkingCertification(Integer cid){
     
 Certifications=new String[10];
      try{ 
         
               int j=0;
            Class.forName("com.mysql.jdbc.Driver");
            Connection con2 = DriverManager.getConnection("jdbc:mysql://node1055-env-8258411.adaptainer.io/myjob","root","LIXcmb23716"); 
            Statement st= con2.createStatement();
            String sq =("select * from certificate where candidateId = ?");
        PreparedStatement  prp1 = con2.prepareStatement(sq);
          prp1.setString(1,cid.toString());
        ResultSet  rs1= prp1.executeQuery();
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

%>
<?xml version="1.0"?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.2//EN"
"http://www.wapforum.org/DTD/wml12.dtd">

<wml>

<card title="candidatesSuitable">
 <anchor>
<big color="blue">welcome</big><go  href="wml_login.jsp"/>
     </anchor>
   
    <p>
        <small color="red"> company account</small>
</p>
<% 
   
   try{
        id=Integer.parseInt(request.getAttribute("id").toString());
    }catch(Exception e){}
   
     try{
        id=Integer.parseInt(request.getParameter("id"));
    }catch(Exception e){}
   
%>
 <p>
   <anchor>
      <go method="get" href="wml_candidatesSuitable.jsp">
         add job <postfield name="target" value="wmladdJob"/>
      <postfield name="id" value="<%= id %>"/>
      </go>
   </anchor>
</p>
<p>
<br />
<table border="1">
<thead>
            <tr>
                <th><b>candidate for first job</b></th>
            </tr>
        </thead>
       
           

  
   <%    
      
   try{
  
  
    target=request.getParameter("target").toString();
  if(target.equalsIgnoreCase("wmladdJob")){
      nextPage = "wml_addJob.jsp";
  
     
     rd = request.getRequestDispatcher(nextPage);
    rd.forward(request, response); 
 }
   }
    catch(Exception e){
            }
 ////gertting expierince and required certificate
   
    try{ 
         
            Class.forName("com.mysql.jdbc.Driver");
            Connection con3 = DriverManager.getConnection("jdbc:mysql://node1055-env-8258411.adaptainer.io/myjob","root","LIXcmb23716"); 
            Statement st= con3.createStatement();
            String sq =("select * from job where companyId = ? ");
      PreparedStatement  prp3 = con3.prepareStatement(sq);
          prp3.setString(1,id.toString());
       ResultSet  rs3= prp3.executeQuery();
            if (rs3.next())
{
       requiredcertifate=rs3.getString(5);
         experienceYears= rs3.getInt(6);
        con3.close();
           
}    
}
     
        catch(SQLException e){            
             
       
        
        } 
        catch(ClassNotFoundException e){
        
        }
   
   
   
   
   
     try{ 
        
        
                  
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://node1055-env-8258411.adaptainer.io/myjob","root","LIXcmb23716"); 
           
            String sq =("select * from candidate WHERE  experienceYears > ? ");
          prp = con.prepareStatement(sq);
        
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
                      out.println(" <tr><td>"+rs.getString(4)+"</td></tr>");
                       }
                    
                    }
                }
                    
                    catch(Exception e)
                    {}
                    
                        
                              
                     
                    }
        
        
        }
        catch(SQLException e){ 
              
                
        } 
        catch(ClassNotFoundException e){} 
     
   
   %>
  
   
    </table>
  
</p>
   
   </card>

</wml>