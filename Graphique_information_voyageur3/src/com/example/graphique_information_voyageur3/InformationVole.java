package com.example.graphique_information_voyageur3;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class InformationVole extends Activity {
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	Button bouttonAfficherEscale=null;
	GridView gridView=null;
	TextView remarqueVole =null;
	private Handler mHandler = new Handler();
	private ProgressDialog progressDialog;
	ArrayAdapter<String> adapter;
	int display_mode ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_information_vole);
		display_mode =  getResources().getConfiguration().orientation;
		gridView = (GridView) findViewById(R.id.gridViewList);
		remarqueVole = (TextView) findViewById(R.id.textViewRemarqueVole);
		if(Configuration.ORIENTATION_LANDSCAPE == display_mode)
		{
			gridView.setNumColumns(4);
		}
		else
		{
			gridView.setNumColumns(3);
		}
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Chargement en cours");
		progressDialog.show();

		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
		gridView.setAdapter(adapter);
        new Thread(new Runnable() {
            public void run() {
		        try {
		            HttpClient httpclient = new DefaultHttpClient();
		            HttpGet httpGet = new HttpGet("http://airline.bulton.fr/api/infosresajson?idReservation=" + getIntent().getStringExtra("idReservation") );
			        HttpResponse response = httpclient.execute(httpGet);
				    HttpEntity entity = response.getEntity();
				    InputStream is = entity.getContent();
			        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
			        
			        JSONObject JObject = new JSONObject(reader.readLine());
			        if(JObject.getInt("erreur") != 0)
			        {
			        	final String data = JObject.getString("data");
			        	
			        	mHandler.post(new Runnable() {
							
			        		@Override
							public void run() {
			        			Toast.makeText(getApplicationContext(),data, Toast.LENGTH_SHORT).show();
			        			progressDialog.dismiss();
			        			finish();
							}
						});
			        	
			        	
			        }
			        else
			        {
						final JSONObject data = JObject.getJSONObject("data");
						mHandler.post(new Runnable() {
							
							@Override
							public void run() {
								try {
									remarqueVole.setText(data.getString("remarqueVol"));
	
									data.getString("remarqueVol");
							        
									adapter.add("Ville");
									if(Configuration.ORIENTATION_LANDSCAPE == display_mode)
									{
										adapter.add("Date");
									}
							        adapter.add("Heure prévue");
							        adapter.add("Heure effective");
							        
							        adapter.add("Départ "+data.getString("villeDepart"));

							        Date date= new Date(data.getLong("dateHeureDepartPrevueVol") * 1000);
							        Log.d("date test", date.toGMTString());
									if(Configuration.ORIENTATION_LANDSCAPE == display_mode)
									{
										 adapter.add(String.valueOf(date.getDate()) + "/"+ String.valueOf(date.getMonth()) + "/"+ String.valueOf(date.getYear()+ 1900));
									}
							        adapter.add(String.valueOf(date.getHours()) + "H"+ String.valueOf(date.getMinutes()));

							        if(data.getInt("dateHeureDepartEffectiveVol")==0)
							        {
								        adapter.add("-H-");
	
							        }
							        else
							        {
							        	date.setTime(data.getLong("dateHeureDepartEffectiveVol")  * 1000);
								        adapter.add(String.valueOf(date.getHours()) + "H"+ String.valueOf(date.getMinutes()));
	
							        }
							        
							        //Pour chaque escales
							        
							        if(data.getInt("nbEscale") >0)
							        {
							        	JSONArray escales= data.getJSONArray("escales");
								        for(int i = 0 ; i < escales.length() ; i++) {
								            //… on crée un élément pour la liste…
								            JSONObject escale = escales.getJSONObject(i);
								            adapter.add("----");
											if(Configuration.ORIENTATION_LANDSCAPE == display_mode)
											{
												adapter.add("escale a ");
												adapter.add(escale.getString("nomVille"));
											}
											else
											{
												adapter.add("escale a " + escale.getString("nomVille"));
											}
								            
								            adapter.add("----");
								            adapter.add("Arriver ");
								            date.setTime(escale.getLong("datehArriveePrevueEscale") *1000);
											if(Configuration.ORIENTATION_LANDSCAPE == display_mode)
											{
												 adapter.add(String.valueOf(date.getDate()) + "/"+ String.valueOf(date.getMonth()) + "/"+ String.valueOf(date.getYear()+ 1900));
											}
									        adapter.add(String.valueOf(date.getHours()) + "H"+ String.valueOf(date.getMinutes()));
									        
									        
									        if(escale.getInt("datehArriveeEffectiveEscale")==0)
									        {
										        adapter.add("-H-");
			
									        }
									        else
									        {
									        	date.setTime(escale.getLong("datehArriveeEffectiveEscale") *1000);
									        	adapter.add(String.valueOf(date.getHours()) + "H"+ String.valueOf(date.getMinutes()));
									        }
									        
									        
								            adapter.add("Départ ");
								            date.setTime(escale.getLong("datehDepartPrevueEscale") *1000);
											if(Configuration.ORIENTATION_LANDSCAPE == display_mode)
											{
												 adapter.add(String.valueOf(date.getDate()) + "/"+ String.valueOf(date.getMonth()) + "/"+ String.valueOf(date.getYear()+ 1900));
											}
											
									        adapter.add(String.valueOf(date.getHours()) + "H"+ String.valueOf(date.getMinutes()));
									        
									        if(escale.getInt("datehDepartEffectiveEscale")==0)
									        {
										        adapter.add("-H-");
			
									        }
									        else
									        {
										        date.setTime(escale.getLong("datehDepartEffectiveEscale") *1000);
										        adapter.add(String.valueOf(date.getHours()) + "H"+ String.valueOf(date.getMinutes()));
									        }
									        
								        }
									}
							        adapter.add("Arrivee "+data.getString("villeArrivee"));
							        date.setTime(data.getLong("dateHeureArriveePrevueVol") * 1000);
									if(Configuration.ORIENTATION_LANDSCAPE == display_mode)
									{
										 adapter.add(String.valueOf(date.getDate()) + "/"+ String.valueOf(date.getMonth()) + "/"+ String.valueOf(date.getYear()+ 1900));
									}
									
							        adapter.add(String.valueOf(date.getHours()) + "H"+ String.valueOf(date.getMinutes()));

							        if(data.getInt("dateHeureArriveeEffectiveVol")==0)
							        {
								        adapter.add("-H-");
	
							        }
							        else
							        {
							        	date.setTime(data.getLong("dateHeureArriveeEffectiveVol") *1000);
								        adapter.add(String.valueOf(date.getHours()) + "H"+ String.valueOf(date.getMinutes()));
	
							        }
							        progressDialog.dismiss();
						        } catch (JSONException e) {
									// TODO Auto-generated catch block
						        	Toast.makeText(getApplicationContext(),e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
						        	progressDialog.dismiss();
								}

								

							}
						});
			        }
						
			        }
			        catch(final Exception e)
			        {
			        	mHandler.post(new Runnable() {
						
						@Override
						public void run() {
							Toast.makeText(getApplicationContext(),e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
							progressDialog.dismiss();

						}
					});
			        }
            }
        }).start();
	}
}
