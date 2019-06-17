<%-- 
    Document   : wmlLogin
    Created on : Jun 26, 2017, 9:41:55 AM
    Author     : abdul
--%>





<%@page import="java.sql.Connection" %>
<%@ page import=" java.sql.DriverManager" %>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.SQLException"%>
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%>
<%!
   private Integer checkingCompany(String user,String pass){
 try{ 
      ResultSet rs;
 
         PreparedStatement prp;
                   
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://node1055-env-8258411.adaptainer.io/myjob","root","LIXcmb23716"); 
           
            String sq =("select * from company where login LIKE ?");
          prp = con.prepareStatement(sq);
          prp.setString(1,user);
         rs= prp.executeQuery();
          if(rs.next())
                   {
                         if(rs.getString(4).equalsIgnoreCase(pass))
                              {
                                  
       
                               id=rs.getInt(1);
                             
                             
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
              
      
       return 0 ;
          } 
        catch(ClassNotFoundException e){ 
   
return 0;      
}}
  
%>
<%!  
 private Integer checkingCandidate(String user,String pass){
   ResultSet rs;
     try{ 
         PreparedStatement prp;
                   
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://node1055-env-8258411.adaptainer.io/myjob","root","LIXcmb23716"); 
           
            String sq =("select * from candidate where login LIKE ?");
          prp = con.prepareStatement(sq);
          prp.setString(1,user);
         rs= prp.executeQuery();
          if(rs.next())
                   {
                         if(rs.getString(3).equalsIgnoreCase(pass))
                              { 
                               id=rs.getInt(1);
                               exper=rs.getString(6);
                           
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
              
           return 0;      
        } 
        catch(ClassNotFoundException e){  return 0;} 
 
    }     
   
   
   
   %>
<?xml version="1.0"?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.2//EN"
"http://www.wapforum.org/DTD/wml12.dtd">
<wml>

<card id="card1" title="WML Form">
<p>
   user name: <input name="un" size="12"/>
   password : <input name="pw" size="12"/>
    
   <%!   HttpSession sess ;
    private int id  ;
    private String login=null;
    private String password=null ;
    private String exper=null;
   
    String nextPage;
    RequestDispatcher rd ;
   %>
   
                    
   <%
           sess = request.getSession(true);
    
             try{
        login=request.getParameter("un");
            password= request.getParameter("pw"); 
             }
             catch(Exception e){}
     nextPage ="";
try{
    
   int res=checkingCompany(login,password);
   if(res==0)
   {
     int  comp=checkingCandidate(login,password);
    if(comp==1){   nextPage = "wml_jobsAvailable.jsp";
    sess.setAttribute("isLoggedIn", "true");
         request.setAttribute("id", id);
         request.setAttribute("exper", exper);
         rd = request.getRequestDispatcher(nextPage);
         rd.forward(request, response);
   }}
   else{
   if(res==1){
   
      nextPage = "wml_candidatesSuitable.jsp";
    sess.setAttribute("isLoggedIn", "true");
      request.setAttribute("id", id);
     rd = request.getRequestDispatcher(nextPage);
    rd.forward(request, response);
    }
  
   }}
catch(Exception e){
out.println(e.toString());


}
   %>
</p>
<p>
    
    
    
   <anchor>
      <go method="get" href="wml_login.jsp">
          <postfield name="un" value="$(un)"/>
          <postfield name="pw" value="$(pw)"/>
          <postfield name="id" value="<%= id %>"/>
          
          <postfield name="exper" value="<%= id %>"/>
      </go>
      Submit Data
  </anchor>
<br />
</p>
<p>
 <anchor>
      <small color="blue">sign up Candidate</small><go href="wml_registerCandidate.jsp"/>
   </anchor>
<br />
</p><p>
<anchor>
     <small color="blue">sign up company</small><go href="wml_registerCompany.jsp"/>
   </anchor>
<br />
</p><p>
<anchor>
    <small color="blue">Show Job Offers</small><go href="wml_searchAllJobOffers.jsp"/>
   </anchor>
</p>
</card>
</wml>

