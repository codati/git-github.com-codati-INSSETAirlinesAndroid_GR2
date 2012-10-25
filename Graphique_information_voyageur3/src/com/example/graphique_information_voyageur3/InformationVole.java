package com.example.graphique_information_voyageur3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class InformationVole extends Activity {
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	Button bouttonAfficherEscale=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_information_vole);
		bouttonAfficherEscale = (Button) findViewById(R.id.button1);
		
		bouttonAfficherEscale.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
		    	Intent intent = new Intent(getApplication() , ListeDesEscale.class);
		    	startActivity(intent);
				
			}
		});
	}
}
