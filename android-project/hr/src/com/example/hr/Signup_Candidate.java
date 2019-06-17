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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
 
public class Signup_Candidate extends Activity {
	JSONObject      jsonSend;
	 JSONArray  jsonArraySend;
	 String recievedJSONString;
	 Button submitt;
	 EditText userFullNameTxt ;
	 EditText mobile;
	 EditText loginName ;
	 EditText experienceYears;
	 EditText password;
	 EditText repeatPassword;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup_candidate);
	
		 submitt=(Button)findViewById(R.id.signupaddCandiSubmitButton);
		 userFullNameTxt=(EditText)findViewById(R.id.addCandidateNameTxt);
		 mobile=(EditText)findViewById(R.id.addCandidatephonetxt);
		 loginName=(EditText)findViewById(R.id.addCandidatelogintxt);		 
		 experienceYears=(EditText)findViewById(R.id.addCandidateexperirnceTxt);
		 password=(EditText)findViewById(R.id.addCandidatePasswordtxt);
		 repeatPassword=(EditText)findViewById(R.id.addCandidaterepeatPasswordTxt);
	
		 submitt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast ts;
			
			if(	password.getText().toString().equals(repeatPassword.getText().toString()))
			{jsonSend=new JSONObject();
			jsonArraySend=new JSONArray();
				try {
					int aa=Integer.parseInt(experienceYears.getText().toString());
				
				try {
				
					
					
					jsonSend.put("fullName", userFullNameTxt.getText().toString());
					jsonSend.put("mobile", mobile.getText().toString());
					jsonSend.put("login", loginName.getText().toString());
					jsonSend.put("experienceYears", experienceYears.getText().toString());
					jsonSend.put("password", password.getText().toString());
					jsonArraySend.put(jsonSend);
					JsonTask JsonTask=	new JsonTask();
					JsonTask.execute();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					 ts =Toast.makeText(getApplicationContext(),"JSON_Put_error",Toast.LENGTH_LONG);
					 ts.setGravity(Gravity.BOTTOM, Gravity.BOTTOM,Gravity.BOTTOM);
		            	
					 ts.show();
				}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					 ts =Toast.makeText(getApplicationContext(),"put Number in experienceYears",Toast.LENGTH_LONG);
					 ts.setGravity(Gravity.BOTTOM, Gravity.BOTTOM,Gravity.BOTTOM);
		            	
					 ts.show();
				}
				
			}
			else {
				
				 ts=Toast.makeText(getApplicationContext(),"password not entred correctly",Toast.LENGTH_LONG);
				 ts.setGravity(Gravity.BOTTOM, Gravity.BOTTOM,Gravity.BOTTOM);
            	
				 ts.show();
			}
				
			}
		});
	}

     private  class JsonTask extends AsyncTask<String,String,String>{
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			HttpURLConnection	connection=null;

				URL url=null;
				try {
					url = new URL("http://node1054-env-8258411.adaptainer.io/registerCandidate");
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					Log.d("abdo",e1.toString());
					
					
				}
					
				
				
					//send
				try {
					connection=(HttpURLConnection)url.openConnection();
					connection.setDoInput(true);
			        connection.setDoOutput(true);
					connection.connect();
					
				
	            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
	            BufferedWriter writer = new BufferedWriter(out);
	           
	          
	            writer.write(jsonArraySend.toString());
	           
	            writer.flush();
	            
				} catch (IOException  e) {
					// TODO Auto-generated catch block
					Log.d("abdo",e.toString());
					return "exception";
					
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
	            recievedJSONString =buffer.toString();
	         
	            return recievedJSONString ;
				}
		 
				   else {
		            	return("no");
		            }
				   
		
				} catch (IOException e) {
					// TODO Auto-generated catch block
					Log.d("abdo",e.toString());
					return "zxaa";
				}
		   
		   finally {
              connection.disconnect();
              
          }
			
			
		}
		
		

		@Override
		protected void onPostExecute(String recievedJSONString) {
			JSONObject	jsonrec ;
			JSONArray jsonArrayrec;
			
			try {
				jsonArrayrec = new JSONArray(recievedJSONString.toString());
					jsonrec=jsonArrayrec.getJSONObject(0);
					String state =jsonrec.getString("state");
			
					
					Toast tn=Toast.makeText(getApplicationContext(),"your registeration ="+state,Toast.LENGTH_LONG);
					tn.setGravity(Gravity.BOTTOM, Gravity.BOTTOM,Gravity.BOTTOM);
		            	
					 tn.show();
					 if(state.equalsIgnoreCase("success")){
						 
						 Intent intent = new Intent(Signup_Candidate.this,MainActivity.class);
		            	 startActivity(intent);	 
						 
						 
					 }
			} catch (JSONException e) {
				// TODO Auto-generated catch block
			
				Toast tn=Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_LONG);
				tn.setGravity(Gravity.BOTTOM, Gravity.BOTTOM,Gravity.BOTTOM);
	            	
				 tn.show();
			}
			
		    
		        
		     }
			
	  }
	}
