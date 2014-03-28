package com.example.lab4_ssp_dka;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class ViewProductActivity extends Activity {

	private Button btnAddProd;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_product);
		
		btnAddProd = (Button) findViewById(R.id.button1);
		btnAddProd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// при нажатии на кнопку AddPerson открывается AddPersActivity
				Intent addPersActivity = new Intent(getApplicationContext(), AddProductActivity.class);
				startActivityForResult(addPersActivity, 6);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_product, menu);
		return true;
	}

}
