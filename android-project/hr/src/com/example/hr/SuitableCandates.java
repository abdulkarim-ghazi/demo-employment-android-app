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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SuitableCandates extends Activity {

	 JSONObject      jsonSend;
	 JSONArray  jsonArraySend;
	 String recievedJSONString;
	 ListView listView;
	 Button addJob;
	Button search;
	 String jsonActR;
		@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.suitable_candates);
		
		     jsonActR = getIntent().getStringExtra("Jsonaa");


		listView =(ListView)findViewById(R.id.candidatesSuitablesList);
		Button addJob=(Button)findViewById(R.id.addJobOppBttn);
	
		        jsonSend =new JSONObject();
            jsonArraySend=new JSONArray();
        
         try
         {
		JsonTask JsonTask=	new JsonTask();
		JsonTask.execute();
         }
		catch ( Exception e){}
	
		addJob.setOnClickListener( new View.OnClickListener() {public void onClick(View v) {
		
		
		Intent aa=new Intent(SuitableCandates.this,Add_Job.class);
		aa.putExtra("Jsonaa", jsonActR.toString()) ;
		startActivity(aa);
		
		} });
		
		}
	
	 
	private  class JsonTask extends AsyncTask<String,String,String>{
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			HttpURLConnection	connection=null;

				URL url=null;
				try {
					url = new URL("http://node1054-env-8258411.adaptainer.io/candidatesSuitable");
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
	           
	          
	            writer.write(jsonActR.toString());
	           
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
			// TODO Auto-generated method stub
//  

       		           try {
	        	
	              JSONArray jArray=new JSONArray(recievedJSONString);
	              JSONObject json_data;

	              List<String> items  = new ArrayList<String>();
	              for(int i=0; i < jArray.length() ; i++) {
	                  json_data = jArray.getJSONObject(i);
	                 
	                  String name=json_data.getString("candidate");
	                  items.add(name);
	                  Log.d(name,"Output");
	                  json_data=null;
	              }

	               
	          
	           
	              
	              ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(SuitableCandates.this,android.R.layout.simple_list_item_1,items );

	              listView.setAdapter(arrayAdapter); 
	                 
	           } catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			                            
	      	 Toast koast=Toast.makeText(getApplicationContext(),"e ="+e.toString(),Toast.LENGTH_LONG);
	           }
	     }
		
	  }
	}
