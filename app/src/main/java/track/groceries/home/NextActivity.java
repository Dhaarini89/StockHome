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

public class NextActivity extends AppCompatActivity {

    private RecyclerView fruitrecyclerView;
    public static List<Model> fruitmodelArrayList;
    private FruitDbAdapter mFruitDatabase,helper;
    private FruitModelAdapter fruitmodelAdapter;
    private FruitDbAdapter Fruithelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_fruitnext);
        fruitrecyclerView = findViewById(R.id.fruitrecycler_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        fruitrecyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration divider = new DividerItemDecoration(fruitrecyclerView.getContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.my_custom_divider));
        fruitrecyclerView.addItemDecoration(divider);
        mFruitDatabase = new FruitDbAdapter(this);
        fruitmodelArrayList = mFruitDatabase.getdata();
        fruitmodelAdapter = new FruitModelAdapter(this, fruitmodelArrayList, new FruitModelListener());
        fruitrecyclerView.setAdapter(fruitmodelAdapter);




    }

        private class FruitModelListener implements FruitModelAdapter.CustomAdapterListener {

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
                 final EditText input = new EditText(NextActivity.this);
                 LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                         LinearLayout.LayoutParams.MATCH_PARENT,
                         LinearLayout.LayoutParams.MATCH_PARENT);
                 input.setLayoutParams(lp);
                 builder.setView(input);
                 builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialogInterface, int i) {
                         helper = new FruitDbAdapter(getApplicationContext());
                         String value = input.getText().toString().trim();
                         if (! value.isEmpty()) {
                             String inputdata = value.substring(0, 1).toUpperCase() + value.substring(1).toLowerCase();
                             int dcheck = helper.checkproduct(inputdata);
                             if (dcheck == 0) {
                                 long id = helper.insertData(inputdata, 100, 0, 0);
                                 fruitmodelArrayList = helper.getdata();
                                 fruitmodelAdapter = new FruitModelAdapter(getApplicationContext(), fruitmodelArrayList, new FruitModelListener());
                                 fruitrecyclerView.setAdapter(fruitmodelAdapter);
                             } else {
                                 Message.message(getApplicationContext(), "Product Already Exist");
                             }
                         }
                         else
                             Message.message(getApplicationContext(),"Enter Valid String");
                     }
                 });
                 builder.show();
                 return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
