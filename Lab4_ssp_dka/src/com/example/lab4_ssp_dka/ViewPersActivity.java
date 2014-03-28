package com.example.lab4_ssp_dka;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class ViewPersActivity extends Activity {
	
	private Button btnAddPers;
	private DBHelper dbHelper;
	private List<Person> persList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_pers);
		dbHelper = new DBHelper(this);
		persList = new ArrayList<Person>();
		
		btnAddPers = (Button) findViewById(R.id.btnAddPers);
		btnAddPers.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// при нажатии на кнопку AddPerson открывается AddPersActivity
				Intent addPersActivity = new Intent(getApplicationContext(), AddPersActivity.class);
				startActivityForResult(addPersActivity, 3);
			}
		});
		
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		updateList();
	}	
	
	// получаем результат из AddPersActivity
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(resultCode == RESULT_OK) {
			Person person = new Person();
			person = (Person) data.getSerializableExtra("person_extra");
			dbHelper.addPersonToDB(person);
		} else if(resultCode == RESULT_CANCELED) {
			
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_pers, menu);
		return true;
	}
	
	public void updateList() {

		dbHelper.getValidPersonFromDB(persList);
		
		ListView list = (ListView) findViewById(R.id.lvPers);
		
		List<String> pList = new ArrayList<String>();
		for (Person pers : persList) {
			pList.add(pers.toString());
		}
		
		// создаем адаптер
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	        android.R.layout.simple_list_item_1, pList);

	    // присваиваем адаптер списку
	    list.setAdapter(adapter);
	    
	    list.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View view,
	            int position, long id) {
	        	
				Intent data = new Intent();
				data.putExtra("buyer_seller_id_extra", persList.get(position).getId());
				data.putExtra("buyer_seller_name_extra", persList.get(position).getName());
				// передаем результат в AddDeal
				setResult(RESULT_OK, data);
				finish();
	        }
	      });
	}

}
