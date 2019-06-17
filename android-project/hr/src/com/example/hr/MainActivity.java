package com.example.hr;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {
	EditText userText;
	EditText passwordTxt;
	String userName;
	String password;
	Button submit;
	TextView newCompany;
	TextView newCandidate;
	Button showjob;
	String finalJson;
    JSONObject jsonSend ;
    JSONArray jsonArraySend;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		submit =(Button)findViewById(R.id.loginSubmittButton);
		newCompany =(TextView)findViewById(R.id.loginNewCompanTxt);
		newCandidate =(TextView)findViewById(R.id.loginNewCandidatetxt);
		showjob =(Button)findViewById(R.id.loginShowJobsBttn);
		userText=(EditText)findViewById(R.id.loginUserNameTxt);
		 passwordTxt=(EditText)findViewById(R.id.loginPasswordTxt);

          userName=userText.getText().toString();
         password =passwordTxt.getText().toString();
		newCandidate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				 Intent intent = new Intent(MainActivity.this,Signup_Candidate.class);
            	 startActivity(intent);
			}
		});
		
	
		newCompany.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				 Intent intent = new Intent(MainActivity.this,Add_Company.class);
            	 startActivity(intent);
			}
		});
		showjob.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				 Intent intent = new Intent(MainActivity.this,ShowJobOffers.class);
            	 startActivity(intent);
			}
		});
	submit.setOnClickListener( new View.OnClickListener() {public void onClick(View v) {
		 userName=userText.getText().toString();
         password =passwordTxt.getText().toString();
		new JsonTask().execute(); } });
	}
	
	
	
	
	
	private  class JsonTask extends AsyncTask<String,String,String>{
		
	
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			HttpURLConnection	connection=null;
			
        	
		
				URL url=null;
				try {
					url = new URL("http://node1054-env-8258411.adaptainer.io/login");
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					Log.d("abdo",e1.toString());
					return "exception";
					
				}
					
			
				//send
				try {
					connection=(HttpURLConnection)url.openConnection();
					connection.setDoInput(true);
			        connection.setDoOutput(true);
					connection.connect();
					
				
	            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
	            BufferedWriter writer = new BufferedWriter(out);
	           
	            jsonSend =new JSONObject();
	            jsonArraySend=new JSONArray();
	            jsonSend.put("username",userName); 
	            jsonSend.put("password",password); 
	            jsonArraySend.put(jsonSend);
	            writer.write(jsonArraySend.toString());
	           
	            writer.flush();
	            
				} catch (IOException e) {
					// TODO Auto-generated catch block
					Log.d("abdo",e.toString());
					return "exception";
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Log.d("abdo",e.toString());
				}
	            
	            
				//recieve
				try {
				 int response_code;
				
					response_code = connection.getResponseCode();
				
				if (response_code == HttpURLConnection.HTTP_OK) {

				InputStream stream =connection.getInputStream();
				BufferedReader reader =new BufferedReader(new InputStreamReader(stream));
	            StringBuffer buffer= new StringBuffer();
	            String line="";
	           
	            
	            while((line=reader.readLine()) != null)
	            {
	            	buffer.append(line);  	
	            }
				  finalJson =buffer.toString();
	         
	            return finalJson ;
				}
		 
				   else {
		            	return("unsuccessful");
		            }
				   
		
				} catch (IOException e) {
					// TODO Auto-generated catch block
					Log.d("abdo",e.toString());
					return "exception";
				}
		   
		   finally {
              connection.disconnect();
              
          }
			
			
		}
		
		@Override
		protected void onPostExecute(String finalJson) {
			// TODO Auto-generated method stub

	           try {
	        	
	              JSONArray jsonArray=new JSONArray(finalJson);
	        	   JSONObject   finalJsonOb = jsonArray.getJSONObject(0);
	                 Integer State= finalJsonOb.getInt("isRegister");
	                 Integer id= finalJsonOb.getInt("id");
	                 String type= finalJsonOb.getString("type");
	                 if((State==1) && type.equals("candidate"))
	                 {
	                	 Intent intent = new Intent(MainActivity.this, Jops_Suitable_For_You.class);
	                	 intent.putExtra("Jsonaa", finalJson.toString());
	                	 startActivity(intent);
	                 }
	                 else {if((State==1) && type.equals("company"))
	                 {
	                	 Intent intent = new Intent(MainActivity.this,SuitableCandates.class);
	                	 intent.putExtra("Jsonaa", finalJson.toString());
	                	 startActivity(intent);
	                 }
	                 
	                 else{
	                	 Toast toast=Toast.makeText(getApplicationContext(),"Wrong User Name OR Password"+State.toString()+type,Toast.LENGTH_LONG);
	                	toast.setGravity(Gravity.BOTTOM, Gravity.BOTTOM,Gravity.BOTTOM);
	                	
	                	 toast.show();
	                 
	                 }}
	                 
					
	                 
	                 
	           } catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			     
	           
					Toast toast=Toast.makeText(getApplicationContext(),"Wrong wrong",Toast.LENGTH_LONG);
                	toast.setGravity(Gravity.BOTTOM, Gravity.BOTTOM,Gravity.BOTTOM);
                	
                	 toast.show();
	           }
	          
	     }
		
	  }
	}
