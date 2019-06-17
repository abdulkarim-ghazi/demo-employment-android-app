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

public class Add_Job extends Activity {

	JSONObject      jsonSend;
	 JSONArray  jsonArraySend;
	 String recievedJSONString;
	 Button submitt;
	 EditText Job_Title ;
	 EditText salary;
	 EditText requiredEducationLevel ;
	 EditText requiredExperienceYears;
	  Integer companyId=null;
	 String jsonActR;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add__job);
	
		 submitt=(Button)findViewById(R.id.addJobSubmitButtn);
		 Job_Title=(EditText)findViewById(R.id.addTitleTxt);
		 salary=(EditText)findViewById(R.id.addSalaryTxt);
		 requiredEducationLevel=(EditText)findViewById(R.id.addRequiredEducationLevelTxt);		 
		 requiredExperienceYears=(EditText)findViewById(R.id.addRequiredExperienceTxt);
		 jsonActR = getIntent().getStringExtra("Jsonaa");
		 //getting id from json
		 JSONArray jai;
		try {
			jai = new JSONArray(jsonActR);
			 JSONObject joi=jai.getJSONObject(0);
			 companyId=Integer.parseInt(joi.getString("id"));	
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			Toast tk =Toast.makeText(getApplicationContext(),"errror in recieving compantId ",Toast.LENGTH_LONG);
			 tk.setGravity(Gravity.BOTTOM, Gravity.BOTTOM,Gravity.BOTTOM);
			 tk.show();
		}
	    	 
		 
		 //
		 submitt.setOnClickListener(new View.OnClickListener() {
			 
			@Override
			public void onClick(View v) {
				Toast ts;
			
			
			{jsonSend=new JSONObject();
			jsonArraySend=new JSONArray();
				try {
					int aa=Integer.parseInt(salary.getText().toString());
					int bb=Integer.parseInt(requiredExperienceYears.getText().toString());
				try {
				
					
					
					jsonSend.put("title", Job_Title.getText().toString());
					jsonSend.put("salary", salary.getText().toString());
					jsonSend.put("requiredExperienceYears", requiredExperienceYears.getText().toString());
					jsonSend.put("requiredEducationLevel", requiredEducationLevel.getText().toString());
					jsonSend.put("companyId", companyId);
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
					 ts =Toast.makeText(getApplicationContext(),"some values must be numbers !!!!",Toast.LENGTH_LONG);
					 ts.setGravity(Gravity.BOTTOM, Gravity.BOTTOM,Gravity.BOTTOM);
		            	
					 ts.show();
				}
				
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
					url = new URL("http://node1054-env-8258411.adaptainer.io/addJob");
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
						 
						 Intent intent = new Intent(Add_Job.this,SuitableCandates.class);
						 intent.putExtra("Jsonaa", jsonActR.toString()) ;
							startActivity(intent);
						 
						 
					 }
			} catch (JSONException e) {
				// TODO Auto-generated catch block
			
				Toast tn=Toast.makeText(getApplicationContext(),"error"+recievedJSONString,Toast.LENGTH_LONG);
				tn.setGravity(Gravity.BOTTOM, Gravity.BOTTOM,Gravity.BOTTOM);
	            	
				 tn.show();
			}
			
		    
		        
		     }
			
	  }
	}
