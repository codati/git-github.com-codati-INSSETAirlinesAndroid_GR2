package com.example.graphique_information_voyageur3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class EntreNumeroDeVol extends Activity {
    Button boutonValider=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entre_numero_de_vol);
        
        boutonValider= (Button) findViewById(R.id.buttonValierCodeBillet);
        boutonValider.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
		    	Intent intent = new Intent(getApplication() , InformationVole.class);
		    	startActivity(intent);
				
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_entre_numero_de_vol, menu);
        return true;
    }
}
