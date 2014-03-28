package com.example.lab4_ssp_dka;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class AddDealActivity extends Activity {
	Button btnChooseBuyer;
	Button btnChooseSeller;
	Button btnChooseProd;
	Button btnAddDeal;
	EditText etName;
	EditText etDate;
	DBHelper dbHelper;
	List<Person> persList;
	Deal tempDeal;
	List<Product> product;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_deal);
		dbHelper = new DBHelper(this);
		persList = new ArrayList<Person>();
		tempDeal = new Deal();
		product = new ArrayList<Product>();
		
		etName = (EditText) findViewById(R.id.etNameDeal);
		etDate = (EditText) findViewById(R.id.etData);

		btnChooseBuyer = (Button) findViewById(R.id.btnChooseB);
		btnChooseBuyer.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// при нажатии на кнопку ChooseBuyer открывается ViewPersActivity
				Intent addDealIntent = new Intent(getApplicationContext(), ViewPersActivity.class);
				startActivityForResult(addDealIntent, 1);
			}
		});
		
		btnChooseSeller = (Button) findViewById(R.id.button2);
		btnChooseSeller.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// при нажатии на кнопку ChooseSeller открывается ViewPersActivity
				Intent addDealIntent = new Intent(getApplicationContext(), ViewPersActivity.class);
				startActivityForResult(addDealIntent, 2);
			}
		});
		
		btnChooseProd = (Button) findViewById(R.id.button3);
		btnChooseProd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent addDealIntent = new Intent(getApplicationContext(), AddProductActivity.class);
				startActivityForResult(addDealIntent, 3);
			}
		});
		
		btnAddDeal = (Button) findViewById(R.id.btnAddDeal);
		btnAddDeal.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				tempDeal.setName(etName.getText().toString());
				tempDeal.setDate(etDate.getText().toString());
				tempDeal.setProductList(product);
				
				if(!(tempDeal.getName().length() == 0) && !(tempDeal.getDate().length() == 0)
						&& !tempDeal.getProduct().isEmpty() && !(tempDeal.getBuyerId() == 0)
						&& !(tempDeal.getSellerId() == 0)) {
					Intent data = new Intent();
										
					data.putExtra("deal_extra", tempDeal);
					// передаем результат в MainActivity
					setResult(RESULT_OK, data);
					finish();
				}
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 1:
			if(resultCode == RESULT_OK) {
				tempDeal.setBuyerId(data.getLongExtra("buyer_seller_id_extra", 0));
				btnChooseBuyer.setText(data.getStringExtra("buyer_seller_name_extra"));
			} else if(resultCode == RESULT_CANCELED) {
//				return;
			}
			break;
		case 2:
			if(resultCode == RESULT_OK) {
				tempDeal.setSellerId(data.getLongExtra("buyer_seller_id_extra", 0));
				btnChooseSeller.setText(data.getStringExtra("buyer_seller_name_extra"));
			} else if(resultCode == RESULT_CANCELED) {
//				return;
			}
			break;
		case 3:
			if(resultCode == RESULT_OK) {
				product.add(((Product) data.getSerializableExtra("product_extra")));
				updateList();
			} else if(resultCode == RESULT_CANCELED) {
//				return;
			}
			break;
		default:
			break;
		}
	}

	public void updateList() {
		ListView list = (ListView) findViewById(R.id.lvAddProduct);
		
		List<String> pList = new ArrayList<String>();
		for (Product prod : product) {
			pList.add(prod.toString());
		}
		
		// создаем адаптер
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	        android.R.layout.simple_list_item_1, pList);

	    // присваиваем адаптер списку
	    list.setAdapter(adapter);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_deal, menu);
		return true;
	}
}
