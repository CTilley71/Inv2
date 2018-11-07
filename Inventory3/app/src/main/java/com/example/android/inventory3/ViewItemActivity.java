package com.example.android.inventory3;

import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.inventory3.data.ItemContract;

public class ViewItemActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int EXISTING_PET_LOADER = 0;
    private Uri CurrentItemUri;
    public String productName;

    private EditText productNameView;
    private EditText quantityEditText;
    private EditText priceEditText;
    private EditText supplierNameEditText;
    private EditText supplierPhoneEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_view_item );

        Intent intent = getIntent();
        CurrentItemUri = intent.getData();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id,  Bundle bundle) {

        //define columns to display
        String[] projection = {
                ItemContract.ItemEntry._ID,
                ItemContract.ItemEntry.COLUMN_PRODUCT_NAME,
                ItemContract.ItemEntry.COLUMN_PRICE,
                ItemContract.ItemEntry.COLUMN_QUANTITY,
                ItemContract.ItemEntry.COLUMN_SUPPLIER_NAME,
                ItemContract.ItemEntry.COLUMN_SUPPLIER_PHONE
        };

        // Perform a query on the provider using the ContentResolver.
        // Use the {@link PItemEntry#CONTENT_URI} to access the inventory data.
        return new CursorLoader( this, CurrentItemUri,
                projection,             // The columns to return for each row
                null,                   // Selection criteria
                null,                   // Selection criteria
                null );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor){
            if (cursor.moveToFirst()) {
                // Find the columns of pet attributes that we're interested in
                int nameColumnIndex = cursor.getColumnIndex( ItemContract.ItemEntry.COLUMN_PRODUCT_NAME);
                int quantityColumnIndex = cursor.getColumnIndex( ItemContract.ItemEntry.COLUMN_QUANTITY );
                int PriceColumnIndex = cursor.getColumnIndex( ItemContract.ItemEntry.COLUMN_PRICE);
                int supplierNameColumnIndex = cursor.getColumnIndex( ItemContract.ItemEntry.COLUMN_SUPPLIER_NAME);
                int supplierPhoneColumnIndex = cursor.getColumnIndex( ItemContract.ItemEntry.COLUMN_SUPPLIER_PHONE);

                // Extract out the value from the Cursor for the given column index
                String name = cursor.getString( nameColumnIndex );
                String price = cursor.getString( quantityColumnIndex );
                int quantity = cursor.getInt( PriceColumnIndex );

                String supplierName = cursor.getString( supplierNameColumnIndex );
                String supplierPhone = cursor.getString(supplierPhoneColumnIndex);

                // Update the views on the screen with the values from the database
                productNameView.setText( name );
                quantityEditText.setText( quantity );
                priceEditText.setText( Integer.toString( quantity ) );
                supplierNameEditText.setText (supplierName);
                supplierPhoneEditText.setText (supplierPhone);

            }
        }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}

