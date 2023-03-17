package track.groceries.home;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class QuantityActivity extends AppCompatActivity {
    public String[] ArraySeconds = new String[6];
    public Spinner spinner;
    public EditText edittext;
    private ExtraDb ExtraDb;
    private String Product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quantity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ArraySeconds[0]="Kg";
        ArraySeconds[1]="l";
        ArraySeconds[2]="Gms";
        ArraySeconds[3]="ml";
        ArraySeconds[4]="ounce";
        ArraySeconds[5]="packet";
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
         Product = prefs.getString("Product", "1 seconds");
        this.setTitle(Product);
         spinner = (Spinner) findViewById(R.id.spinner);
         edittext = (EditText) findViewById(R.id.Product);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,ArraySeconds);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        

    }


    public void Submit(View V)
    {
     //   SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
       // prefs.edit().putString("QuantityValue", spinner.getSelectedItem().toString()).commit();
        ExtraDb = new ExtraDb(V.getContext());
        int eid = ExtraDb.checkproduct(Product);
        if (eid != 0 )

        {   
            long eeid = ExtraDb.updatequantity(Product,edittext.getText().toString()+spinner.getSelectedItem().toString());

        }
        Intent intent = new Intent(QuantityActivity.this, DeleteActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public void Cancel(View V)
    {
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_simple, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(QuantityActivity.this, DeleteActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}