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

import track.groceries.home.*;



public class CerealsActivity extends AppCompatActivity {

    public static RecyclerView Cerealsrecyclerview;
    public static List<Model> CerealsmodelArrayList;
    private ExtraDb Cerealshelper,mCerealsDatabase;
    private String product;

    private CerealsModelAdapter CerealsmodelAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_cereals);
        Cerealsrecyclerview = findViewById(R.id.cerealsrecycler_list);
        LinearLayoutManager CerealslayoutManager = new LinearLayoutManager(this);
        CerealslayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        Cerealsrecyclerview.setLayoutManager(CerealslayoutManager);
        DividerItemDecoration Cerealsdivider = new DividerItemDecoration(Cerealsrecyclerview.getContext(), DividerItemDecoration.VERTICAL);
        Cerealsdivider.setDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.my_custom_divider));
        Cerealsrecyclerview.addItemDecoration(Cerealsdivider);
        mCerealsDatabase = new ExtraDb(this);
        CerealsmodelArrayList = mCerealsDatabase.getdata("Cereals");
        CerealsmodelAdapter = new CerealsModelAdapter(this, CerealsmodelArrayList, new CerealsModelListener());
        Cerealsrecyclerview.setAdapter(CerealsmodelAdapter);

    }



    private class CerealsModelListener implements CerealsModelAdapter.CustomAdapterListener {
        @Override
        public void onModelSelected(Model model, View view) {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
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
                final EditText input = new EditText(CerealsActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                builder.setView(input);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Cerealshelper = new ExtraDb(getApplicationContext());
                        String value = input.getText().toString().trim();

                        if (!value.isEmpty()) {
                            String inputdata = value.substring(0, 1).toUpperCase() + value.substring(1).toLowerCase();
                            int dcheck = Cerealshelper.checkproduct(inputdata);
                            if (dcheck == 0) {

                                long id = Cerealshelper.insertData(inputdata, 100, 0, 0,"Cereals");
                                CerealsmodelArrayList = Cerealshelper.getdata("Cereals");
                                CerealsmodelAdapter = new CerealsModelAdapter(getApplicationContext(), CerealsmodelArrayList, new CerealsModelListener());
                                Cerealsrecyclerview.setAdapter(CerealsmodelAdapter);
                            } else {
                                Message.message(getApplicationContext(), "Product Already Exist");
                            }
                        } else {
                            Message.message(getApplicationContext(), "Enter a Valid String");
                        }
                    }

                });
                builder.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
