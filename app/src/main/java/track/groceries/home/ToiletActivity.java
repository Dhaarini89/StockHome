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

public class ToiletActivity extends AppCompatActivity {

    private RecyclerView toiletrecyclerview;
    public static List<Model> toiletmodelArrayList;
    private ToiletDbAdapter mToiletriesDatabase,mToilethelper;
    private ToiletModelAdapter toiletmodelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_toilet);
        toiletrecyclerview = findViewById(R.id.toiletrecycler_list);
        LinearLayoutManager toiletlayoutManager = new LinearLayoutManager(this);
        toiletlayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        toiletrecyclerview.setLayoutManager(toiletlayoutManager);
        DividerItemDecoration toiletdivider = new DividerItemDecoration(toiletrecyclerview.getContext(), DividerItemDecoration.VERTICAL);
        toiletdivider.setDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.my_custom_divider));
        toiletrecyclerview.addItemDecoration(toiletdivider);
        mToiletriesDatabase = new ToiletDbAdapter(this);
        toiletmodelArrayList = mToiletriesDatabase.getdata();
        toiletmodelAdapter = new ToiletModelAdapter(this, toiletmodelArrayList, new ModelListener());
        toiletrecyclerview.setAdapter(toiletmodelAdapter);






    }

        



    private class ModelListener implements ToiletModelAdapter.CustomAdapterListener {

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
            final EditText input = new EditText(ToiletActivity.this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            input.setLayoutParams(lp);
            input.getText().toString().toLowerCase();
            builder.setView(input);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    mToilethelper = new ToiletDbAdapter(getApplicationContext());
                    String value = input.getText().toString().trim();
                    if ( ! value.isEmpty()) {
                        String inputdata = value.substring(0, 1).toUpperCase() + value.substring(1).toLowerCase();
                        int dcheck = mToilethelper.checkproduct(inputdata);
                        if (dcheck == 0) {
                            long id = mToilethelper.insertData(inputdata, 100, 0, 0);
                            toiletmodelArrayList = mToilethelper.getdata();
                            toiletmodelAdapter = new ToiletModelAdapter(getApplicationContext(), toiletmodelArrayList, new ModelListener());
                            toiletrecyclerview.setAdapter(toiletmodelAdapter);
                        } else {
                            Message.message(getApplicationContext(), "Product Already Exist");
                        }
                    }
                    else
                        Message.message(getApplicationContext(),"Enter a Valid String");
                }
            });
            builder.show();
            return true;

        }

        return super.onOptionsItemSelected(item);

    }



}
