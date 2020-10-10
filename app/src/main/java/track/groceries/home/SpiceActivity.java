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

public class SpiceActivity extends AppCompatActivity {

    private RecyclerView spicerecyclerview;
    public static List<Model> spicemodelArrayList;

    private SpiceDbAdapter mSpiceDatabase,mSpicehelper;
    private SpiceModelAdapter spicemodelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_spice);
        spicerecyclerview = findViewById(R.id.spicerecycler_list);
        LinearLayoutManager spicelayoutManager = new LinearLayoutManager(this);
        spicelayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        spicerecyclerview.setLayoutManager(spicelayoutManager);
        DividerItemDecoration spicedivider = new DividerItemDecoration(spicerecyclerview.getContext(), DividerItemDecoration.VERTICAL);
        spicedivider.setDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.my_custom_divider));
        spicerecyclerview.addItemDecoration(spicedivider);
        mSpiceDatabase = new SpiceDbAdapter(this);
        spicemodelArrayList = mSpiceDatabase.getdata();
        spicemodelAdapter = new SpiceModelAdapter(this, spicemodelArrayList, new SpiceModelListener());
        spicerecyclerview.setAdapter(spicemodelAdapter);


    }

    private class SpiceModelListener implements SpiceModelAdapter.CustomAdapterListener {

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
                 final EditText input = new EditText(SpiceActivity.this);
                 LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                         LinearLayout.LayoutParams.MATCH_PARENT,
                         LinearLayout.LayoutParams.MATCH_PARENT);
                 input.setLayoutParams(lp);
                 final String product = input.getText().toString().toLowerCase();
                 builder.setView(input);
                 builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialogInterface, int i) {
                         mSpicehelper = new SpiceDbAdapter(getApplicationContext());
                         String value = input.getText().toString();
                         if (! value.isEmpty()) {
                             String inputdata = value.substring(0, 1).toUpperCase() + value.substring(1).toLowerCase();
                             int dcheck = mSpicehelper.checkproduct(inputdata);
                             if (dcheck == 0) {

                                 long id = mSpicehelper.insertData(inputdata, 100, 0, 0);
                                 spicemodelArrayList = mSpicehelper.getdata();
                                 spicemodelAdapter = new SpiceModelAdapter(getApplicationContext(), spicemodelArrayList, new SpiceModelListener());
                                 spicerecyclerview.setAdapter(spicemodelAdapter);
                             } else {
                                 Message.message(getApplicationContext(), "Product Already Exist");
                             }
                         }
                         else
                             Message.message(getApplicationContext(),"This is not a valid String");
                     }
                 });
                 builder.show();
                 return true;

    }

        return super.onOptionsItemSelected(item);
    }



}
