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

public class PulseActivity extends AppCompatActivity {

    private RecyclerView pulserecyclerview;
    public static List<Model> pulsemodelArrayList;
    private PulseDbAdapter mPulsesDatabase;
    private PulseDbAdapter mPulsehelper;
    private PulseModelAdapter pulsemodelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_pulse);
        pulserecyclerview = findViewById(R.id.pulserecycler_list);
        LinearLayoutManager pulselayoutManager = new LinearLayoutManager(this);
        pulselayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        pulserecyclerview.setLayoutManager(pulselayoutManager);
        DividerItemDecoration pulsedivider = new DividerItemDecoration(pulserecyclerview.getContext(), DividerItemDecoration.VERTICAL);
        pulsedivider.setDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.my_custom_divider));
        pulserecyclerview.addItemDecoration(pulsedivider);
        mPulsesDatabase = new PulseDbAdapter(this);
        pulsemodelArrayList = mPulsesDatabase.getdata();
        pulsemodelAdapter = new PulseModelAdapter(this, pulsemodelArrayList, new PulseModelListener());
        pulserecyclerview.setAdapter(pulsemodelAdapter);

  }

   

    private class PulseModelListener implements PulseModelAdapter.CustomAdapterListener {
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
                  final EditText input = new EditText(PulseActivity.this);
                  LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                          LinearLayout.LayoutParams.MATCH_PARENT,
                          LinearLayout.LayoutParams.MATCH_PARENT);
                  input.setLayoutParams(lp);
                  builder.setView(input);
                  builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialogInterface, int i) {
                          mPulsehelper = new PulseDbAdapter(getApplicationContext());
                          String value = input.getText().toString().trim();
                          if (!value.isEmpty()) {
                              String inputdata = value.substring(0, 1).toUpperCase() + value.substring(1).toLowerCase();
                              int dcheck = mPulsehelper.checkproduct(inputdata);
                              if (dcheck == 0) {

                                  long id = mPulsehelper.insertData(inputdata, 100, 0, 0);
                                  pulsemodelArrayList = mPulsehelper.getdata();
                                  pulsemodelAdapter = new PulseModelAdapter(getApplicationContext(), pulsemodelArrayList, new PulseModelListener());
                                  pulserecyclerview.setAdapter(pulsemodelAdapter);
                              } else {
                                  Message.message(getApplicationContext(), "Product Already Exist");
                              }
                          }
                           else
                              {
                                  Message.message(getApplicationContext(), "Enter a valid String");
                              }

                      }


                  });
                  builder.show();
                  return true;
          }
        return super.onOptionsItemSelected(item);

    }

}
