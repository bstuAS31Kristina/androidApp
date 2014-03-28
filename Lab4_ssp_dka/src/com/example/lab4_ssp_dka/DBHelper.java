package com.example.lab4_ssp_dka;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper implements Constants {
	public String textQuerry;

	public DBHelper(Context context) {
		super(context, DKA_DATABASE_NAME, null, DKA_DATABASE_VERSION);
		Log.d("dbHelper", "Constructor dbHelper");
	}

	// Called when the database has been opened
	public void onOpen(SQLiteDatabase db) {
		Log.d("dbHelper", "Database has been opened");
	}

	// Called when the database is created for the first time
	public void onCreate(SQLiteDatabase db) {
		// create table Person
		db.execSQL("create table " + DKA_TABLE_PERSONS + " (" + DKA_PERSONS_ID
				+ " integer primary key autoincrement, " + DKA_PERSONS_NAME
				+ " text, " + DKA_PERSONS_COMPANY + " text)");

		// create table Product
		db.execSQL("create table " + DKA_TABLE_PRODUCTS + " ("
				+ DKA_PRODUCTS_ID + " integer primary key autoincrement, "
				+ DKA_PRODUCTS_NAME + " text, " + DKA_PRODUCTS_PRICE
				+ " real, " + DKA_PRODUCTS_COUNT + " integer, "
				+ DKA_PRODUCTS_DEALID + " integer, " + "foreign key("
				+ DKA_PRODUCTS_DEALID + ") references " + DKA_TABLE_DEALS + "("
				+ DKA_DEALS_ID + "))");

		// create table Deal
		db.execSQL("create table " + DKA_TABLE_DEALS + " (" + DKA_DEALS_ID
				+ " integer primary key autoincrement, " + DKA_DEALS_NAME
				+ " text, " + DKA_DEALS_BUYER + " integer, " + DKA_DEALS_SELLER
				+ " integer, " + DKA_DEALS_DATE + " text, " + "foreign key("
				+ DKA_DEALS_BUYER + ") references " + DKA_TABLE_PERSONS + "("
				+ DKA_PERSONS_ID + ")," + "foreign key(" + DKA_DEALS_SELLER
				+ ") references " + DKA_TABLE_PERSONS + "(" + DKA_PERSONS_ID
				+ "))");

	}

	public void addPersonToDB(Person person) {
		Log.d("dbHelper", "addPersonToDB");
		SQLiteDatabase dealsDB = getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put(DKA_PERSONS_NAME, person.getName());
		values.put(DKA_PERSONS_COMPANY, person.getCompany());

		if (dealsDB.insert(DKA_TABLE_PERSONS, null, values) == 0) {
			Log.e("ERROR", "Can not  write to DB");
		}
		values.clear();
		dealsDB.close();
	}

	public void addDealToDB(Deal deal) {
		SQLiteDatabase dealsDB = getWritableDatabase();
		ContentValues valuesDeal = new ContentValues();
		ContentValues valuesProduct = new ContentValues();

		valuesDeal.put(DKA_DEALS_NAME, deal.getName());
		valuesDeal.put(DKA_DEALS_BUYER, deal.getBuyerId());
		valuesDeal.put(DKA_DEALS_SELLER, deal.getSellerId());
		valuesDeal.put(DKA_DEALS_DATE, deal.getDate());

		long dealId = dealsDB.insert(DKA_TABLE_DEALS, null, valuesDeal);
		Log.d("ID D", dealId + " ");
		if (dealId == -1) {
			Log.e("ERROR", "Can not  write to DB");
		} else {
			for (Product prod : deal.getProduct()) {
				valuesProduct.put(DKA_PRODUCTS_NAME, prod.getName());
				valuesProduct.put(DKA_PRODUCTS_PRICE, prod.getPrice());
				valuesProduct.put(DKA_PRODUCTS_COUNT, prod.getCount());
				valuesProduct.put(DKA_PRODUCTS_DEALID, dealId);
				if (dealsDB.insert(DKA_TABLE_PRODUCTS, null, valuesProduct) == -1) {
					Log.e("ERROR", "Can not write to Products");
				}
				Log.d("DBHelper", "Product inserted");
				valuesProduct.clear();
			}
		}

		valuesDeal.clear();
		dealsDB.close();
	}

	// Called when the database needs to be upgraded
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d("dbHelper", "Database has been upgraded");
		// code...
	}

	public void getValidPersonFromDB(List<Person> list) {
//		Log.d("dbHelper", "DBHelper getValidDataFromDB");
		SQLiteDatabase dealsDB = getReadableDatabase();

		Cursor cPersons = dealsDB.query(DKA_TABLE_PERSONS, null, null, null,
				null, null, null);
		list.clear();

		if (!cPersons.isAfterLast()) {
			cPersons.moveToFirst();
			while (!cPersons.isAfterLast()) {
				Person temp = new Person();
				temp.setId(cPersons.getLong(cPersons
						.getColumnIndex(DKA_PERSONS_ID)));
				temp.setName(cPersons.getString(cPersons
						.getColumnIndex(DKA_PERSONS_NAME)));
				temp.setCompany(cPersons.getString(cPersons
						.getColumnIndex(DKA_PERSONS_COMPANY)));
				Log.d("dbHelper getValidPersonFromDB", temp.toString() + "id = " + temp.getId());
				list.add(temp);
				cPersons.moveToNext();
			}
		}

		cPersons.close();
		dealsDB.close();
	}

	public void getValidDealFromDB(List<Deal> list) {
		Log.d("dbHelper", "DBHelper getValidDealFromDB");
		SQLiteDatabase dealsDB = getReadableDatabase();

		String queryDeal = "SELECT " + DKA_DEALS_ID + ", " 
				+ DKA_DEALS_NAME + " FROM "
				+ DKA_TABLE_DEALS;

		Cursor cDeals = dealsDB.rawQuery(queryDeal, null);
		list.clear();

		if (!cDeals.isAfterLast()) {
			cDeals.moveToFirst();
			while (!cDeals.isAfterLast()) {
				Deal temp = new Deal();
				temp.setId(cDeals.getLong(cDeals.getColumnIndex(DKA_DEALS_ID)));
				temp.setName(cDeals.getString(cDeals.getColumnIndex(DKA_DEALS_NAME)));
				list.add(temp);
				cDeals.moveToNext();
			}
		}

		cDeals.close();
		dealsDB.close();
	}

	public void getValidOneDealFromDB(Deal temp, long idDeal) {
		Log.d("dbHelper", "DBHelper getValidDealFromDB");
		SQLiteDatabase dealsDB = getReadableDatabase();

		String queryDeal = "SELECT " + DKA_DEALS_NAME + ", " + DKA_DEALS_BUYER
				+ ", " + DKA_DEALS_SELLER + ", " + DKA_DEALS_DATE + " FROM "
				+ DKA_TABLE_DEALS + " WHERE " + DKA_DEALS_ID + "= " + idDeal;

		Cursor cDeals = dealsDB.rawQuery(queryDeal, null);

		if (!cDeals.isAfterLast()) {
			cDeals.moveToFirst();
			temp.setName(cDeals.getString(cDeals.getColumnIndex(DKA_DEALS_NAME)));
			temp.setBuyerId(cDeals.getLong(cDeals
					.getColumnIndex(DKA_DEALS_BUYER)));
			temp.setSellerId(cDeals.getLong(cDeals
					.getColumnIndex(DKA_DEALS_SELLER)));
			temp.setDate(cDeals.getString(cDeals.getColumnIndex(DKA_DEALS_DATE)));

			List<Product> productList = new ArrayList<Product>();
			getValidProductFromDB(productList, idDeal);
			temp.setProductList(productList);
			cDeals.moveToNext();
		}

		cDeals.close();
		dealsDB.close();
	}

	public void getValidProductFromDB(List<Product> list, long idDeal) {
		Log.d("dbHelper", "DBHelper getValidDataFromDB");
		SQLiteDatabase dealsDB = getReadableDatabase();

		String queryProduct = "SELECT " + DKA_PRODUCTS_NAME + ", "
				+ DKA_PRODUCTS_PRICE + ", " + DKA_PRODUCTS_COUNT + " FROM "
				+ DKA_TABLE_PRODUCTS + " WHERE " + DKA_PRODUCTS_DEALID + "= "
				+ idDeal;
		Cursor cProducts = dealsDB.rawQuery(queryProduct, null);
		list.clear();

		if (!cProducts.isAfterLast()) {
			cProducts.moveToFirst();
			while (!cProducts.isAfterLast()) {
				Product temp = new Product();
				temp.setName(cProducts.getString(cProducts
						.getColumnIndex(DKA_PRODUCTS_NAME)));
				temp.setPrice(cProducts.getDouble(cProducts
						.getColumnIndex(DKA_PRODUCTS_PRICE)));
				temp.setCount(cProducts.getInt(cProducts
						.getColumnIndex(DKA_PRODUCTS_PRICE)));
				list.add(temp);
				cProducts.moveToNext();
			}
		}

		cProducts.close();
		dealsDB.close();
	}
}
