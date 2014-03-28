package com.example.lab4_ssp_dka;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddProductActivity extends Activity {

	private EditText etName;
	private EditText etPrice;
	private EditText etCount;
	private Button btnAddProduct;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_product);

		etName = (EditText) findViewById(R.id.etNameProduct);
		etPrice = (EditText) findViewById(R.id.etPriceProduct);
		etCount = (EditText) findViewById(R.id.etCountProduct);

		btnAddProduct = (Button) findViewById(R.id.btnAddProduct);
		btnAddProduct.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!(etName.getText().toString().trim().equals(""))
						&& !(etPrice.getText().toString().trim().equals(""))
						&& !(etCount.getText().toString().trim().equals(""))) {
					
					Product product = new Product(etName.getText().toString(),
							Double.parseDouble(etPrice.getText().toString()),
							Integer.parseInt(etCount.getText().toString()));
					
					Intent data = new Intent();
					data.putExtra("product_extra", product);
					
					// передаем результат в AddDealActivity
					setResult(RESULT_OK, data);
					finish();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_product, menu);
		return true;
	}

}
