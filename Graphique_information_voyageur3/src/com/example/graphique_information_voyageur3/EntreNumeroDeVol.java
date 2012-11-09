package com.example.graphique_information_voyageur3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EntreNumeroDeVol extends Activity {
    Button boutonValider=null;
    Button boutonScanne= null;
    EditText textEditerCodeBillet = null ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entre_numero_de_vol);
        
        boutonValider= (Button) findViewById(R.id.buttonValierCodeBillet);
        boutonScanne= (Button) findViewById(R.id.buttonScanne);
        textEditerCodeBillet = (EditText) findViewById(R.id.editTextCodeBillet);
        
        boutonScanne.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent("com.google.zxing.client.android.SCAN");
				intent.addCategory(Intent.CATEGORY_DEFAULT);
				intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
				
				startActivityForResult(intent, 0);
				
			}
		});
        
        boutonValider.setOnClickListener(boutonValiderClick);
    }
    

    
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    	
    	   if (requestCode == 0) {
    	
    	      if (resultCode == RESULT_OK) {
    	    	  
    	         String contents = intent.getStringExtra("SCAN_RESULT");
    	   //      String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
    	         /*
    	         Toast.makeText(this, format, Toast.LENGTH_SHORT).show();
    	         Toast.makeText(this, contents, Toast.LENGTH_SHORT).show();
    	         */
    	         this.textEditerCodeBillet.setText(contents);
    	         
    	         // Handle successful scan
    	      } else if (resultCode == RESULT_CANCELED) {
    	    	  Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
    	      }
    	   }
    	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_entre_numero_de_vol, menu);
        return true;
    }
    
    private final Button.OnClickListener boutonValiderClick = new Button.OnClickListener() {

			@Override
			public void onClick(View arg0) {

		    	Intent intent = new Intent(getApplication() , InformationVole.class);
		    	intent.putExtra("idReservation", textEditerCodeBillet.getText().toString());
		    	startActivity(intent);
			}
	};
}
