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

public class MainActivity extends Activity {
	
	Button bAddDeal;
	ListView viewDeals;
	DBHelper dbHelper;
	List<Deal> deals;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		dbHelper = new DBHelper(this);
		deals = new ArrayList<Deal>();
		
		bAddDeal = (Button) findViewById(R.id.bAddDeal);
		bAddDeal.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// при нажатии на кнопку Add открывается AddDealActivity
				Intent startIntent = new Intent(getApplicationContext(), AddDealActivity.class);
				startActivityForResult(startIntent, 1);
			}
		});
		
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		updateList();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
			if(resultCode == RESULT_OK) {
				dbHelper.addDealToDB(((Deal)data.getSerializableExtra("deal_extra")));
			}
			if(resultCode == RESULT_CANCELED) {
				return;
			}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void updateList() {
		dbHelper.getValidDealFromDB(deals);
		
		ListView list = (ListView) findViewById(R.id.lvViewDeal);
		
		List<String> nameList = new ArrayList<String>();
		
		for (Deal deal : deals) {
			nameList.add(deal.getName());
		}
		
		// создаем адаптер
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	        android.R.layout.simple_list_item_1, nameList);

	    // присваиваем адаптер списку
	    list.setAdapter(adapter);
	    
	    list.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View view,
	            int position, long id) {
	        	// при нажатии на ListView открывается ViewDealsActivity
				Intent startIntent = new Intent(getApplicationContext(), ViewDealsActivity.class);
				startIntent.putExtra("dealId", deals.get(position).getId());
				startActivity(startIntent);
	        }
	      });
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.runFinalizersOnExit(true);
		System.exit(0);
	}

}
