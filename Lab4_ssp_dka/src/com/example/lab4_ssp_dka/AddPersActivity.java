package com.example.lab4_ssp_dka;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddPersActivity extends Activity {
	
	private EditText etName;
	private EditText etCompany;
	private Button btnAddPers;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_pers);
		
		etName = (EditText) findViewById(R.id.editText1);
		etCompany = (EditText) findViewById(R.id.editText2);
		
		btnAddPers = (Button) findViewById(R.id.button1);
		btnAddPers.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!etName.getText().toString().trim().equals("") && 
						!etCompany.getText().toString().trim().equals("")) {
					Person person = new Person(etName.getText().toString(), etCompany.getText().toString());
					Intent data = new Intent();
					data.putExtra("person_extra", person);
					// передаем результат в ViewPersActivity
					setResult(RESULT_OK, data);
					finish();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_pers, menu);
		return true;
	}

}
