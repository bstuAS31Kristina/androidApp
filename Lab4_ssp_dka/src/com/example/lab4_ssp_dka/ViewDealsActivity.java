package com.example.lab4_ssp_dka;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewDealsActivity extends Activity {
	
	DBHelper dbHelper;
	TextView tvViewDeal;
	Deal deal;
	List<Person> personList;
	Button btnReturn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_deals);
		
		deal = new Deal();
		personList = new ArrayList<Person>();
		dbHelper = new DBHelper(this);
		tvViewDeal = (TextView) findViewById(R.id.tvViewDeal);
		btnReturn = (Button) findViewById(R.id.btnReturn);
		btnReturn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(intent);
			}
		});
		
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		dbHelper.getValidOneDealFromDB(deal, getIntent().getLongExtra("dealId", 0));
		dbHelper.getValidPersonFromDB(personList);
		
		Person buyer = new Person();
		Person seller = new Person();
		
		for (Person person : personList) {
			if(person.getId() == deal.getBuyerId())
				buyer = person;
			if(person.getId() == deal.getSellerId())
				seller = person;
		}
		
		tvViewDeal.setText("DEAL:\n" + deal.toString() + "\nBUYER:\n"
				+ buyer.toString() + "\nSELLER:\n" + seller.toString());
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_deals, menu);
		return true;
	}

}
