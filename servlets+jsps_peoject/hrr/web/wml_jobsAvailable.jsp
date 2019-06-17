<%-- 
    Document   : newjsp
    Created on : Jun 26, 2017, 11:14:59 AM
    Author     : abdul
--%>

<%@page import="java.sql.Statement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.sql.SQLException"%>
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%>
<%!    
    private String[] checkingCertification(Integer id){
      ResultSet rs1;  
  String[] Certifications=new String[10];
      try{ 
          PreparedStatement prp1;
               int j=0;
            Class.forName("com.mysql.jdbc.Driver");
            Connection con1 = DriverManager.getConnection("jdbc:mysql://node1055-env-8258411.adaptainer.io/myjob","root","LIXcmb23716"); 
            Statement st= con1.createStatement();
            String sq =("select * from certificate where candidateId = ?");
          prp1 = con1.prepareStatement(sq);
          prp1.setString(1,id.toString());
         rs1= prp1.executeQuery();
            while (rs1.next())
                   {
                     Certifications[j]= rs1.getString(3);; 
                     j++;
                         
                    }
            return Certifications;
        }
     
        catch(SQLException e){            
                   
        } 
        catch(ClassNotFoundException e){} 
    Certifications[0]="error";
      return Certifications;
    }


%>
<?xml version="1.0"?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.2//EN"
"http://www.wapforum.org/DTD/wml12.dtd">
<%! 
   Integer experienceYears=null;
   String[] Certifications =new String[10];
    Integer id=null;
   String target=null; 
    RequestDispatcher rd ; 
 ResultSet rs;
  String nextPage=null;
   %>
<wml>

<card title="jobsAvailable">

    
     <anchor>
<big color="blue">welcome</big><go  href="wml_login.jsp"/>
     </anchor> 
   
    <p>
        <small color="red"> candidate account</small>
</p>

   <% 
   try{
    experienceYears=Integer.parseInt(request.getAttribute("exper").toString());
     id=Integer.parseInt(request.getAttribute("id").toString());
   }  
     
       catch(Exception e){
            }
  
   try{
   target=request.getParameter("target").toString();
    experienceYears=Integer.parseInt(request.getParameter("exper"));
     id=Integer.parseInt(request.getParameter("id"));
  
  if(target.equalsIgnoreCase("wml_addCertifiate")){
      nextPage = "wml_addCertifiate.jsp";
    
     
     rd = request.getRequestDispatcher(nextPage);
    rd.forward(request, response); 
 }
   }
    catch(Exception e){
            }
 
   %>
<p>
   <anchor>
      <go method="get" href="wml_jobsAvailable.jsp">
       add certification <postfield name="target" value="wml_addCertifiate"/>
          <postfield name="id" value="<%= id %>"/>
            <postfield name="exper" value="<%= experienceYears %>"/>
      </go>
   </anchor>

</p>
<br />
   
<table border="1">
        <thead>
            <tr>
                <th>jobs are suitable for you</th>
            </tr>
        </thead>
       
         

   
   <%    
       
       ////////////////////////////////////////////////////////
  Certifications=checkingCertification(id);
         ResultSet rs;
     try{ 
         PreparedStatement prp;
               int i=1;    
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://node1055-env-8258411.adaptainer.io/myjob","root","LIXcmb23716"); 
           
            String sq =("select * from job WHERE  requiredExperienceYears <= ? ");
          prp = con.prepareStatement(sq);
        
            prp.setString(1,experienceYears.toString());
         rs= prp.executeQuery();
         while (rs.next())
                   {
                      for(int j=0;j<Certifications.length;j++){
                      try{
                          if(rs.getString(5).equalsIgnoreCase(Certifications[j]))
                              { 
                                  
                   out.println("<tr><td>"+rs.getString(3)+"</td></tr>");
                     
                              }
                         
                    
                      }  catch (Exception e)
                                  {
                                 
                                  }
                        
                               }
                     
                    }
        
        }
        catch(SQLException e){ 
              
                
        } 
        catch(ClassNotFoundException e){} 
     
   
   %>
    </td>
            </tr>
   
    </table>

    

</card>

</wml>