package track.groceries.home;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.MenuItem;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.List;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;

import track.groceries.home.R;

public class OilsActivity extends AppCompatActivity {

    private RecyclerView OilsrecyclerView;
    public static List<Model> OilsmodelArrayList;
    private ExtraDb mOilsDatabase,helper;
    private OilsModelAdapter OilsmodelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_oils);
        OilsrecyclerView = findViewById(R.id.oilrecycler_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        OilsrecyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration divider = new DividerItemDecoration(OilsrecyclerView.getContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.my_custom_divider));
        OilsrecyclerView.addItemDecoration(divider);
        mOilsDatabase = new ExtraDb(this);
        OilsmodelArrayList = mOilsDatabase.getdata("Oils");
        OilsmodelAdapter = new OilsModelAdapter(this, OilsmodelArrayList, new OilsModelListener());
        OilsrecyclerView.setAdapter(OilsmodelAdapter);




    }

    private class OilsModelListener implements OilsModelAdapter.CustomAdapterListener {

        @Override
        public void onModelSelected(Model model, View view) {

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_add:
                /*Customize the List of Products in each Category by adding anything missed or required */
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Enter a new Product");
                final EditText input = new EditText(OilsActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                builder.setView(input);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        helper = new ExtraDb(getApplicationContext());
                        String value = input.getText().toString().trim();
                        if (! value.isEmpty()) {
                            String inputdata = value.substring(0, 1).toUpperCase() + value.substring(1).toLowerCase();
                            int dcheck = helper.checkproduct(inputdata);
                            if (dcheck == 0) {
                                if (!inputdata.isEmpty()) {
                                    long id = helper.insertData(inputdata, 100, 0, 0,"Oils");
                                    OilsmodelArrayList = helper.getdata("Oils");
                                    OilsmodelAdapter = new OilsModelAdapter(getApplicationContext(), OilsmodelArrayList, new OilsModelListener());
                                    OilsrecyclerView.setAdapter(OilsmodelAdapter);
                                }


                            } else {
                                Message.message(getApplicationContext(), "Product Already Exist");
                            }
                        }
                        else
                        {
                            Message.message(getApplicationContext(),"Enter a Valid String");
                        }
                    }
                });
                builder.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
