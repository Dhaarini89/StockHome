package track.groceries.home;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;

import track.groceries.home.R;

public class DeleteActivity extends AppCompatActivity {
    private ExtraDb mExtraDb;
    RecyclerView recyclerView;
    public static List<Show> mShowList;
    public static List<CheckedItems> mCheckeditems;
    private ShowAdapter SA,SAadd;
    private CheckedAdapter CA;
    private String data_fruit;
    private Integer counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_delete);
        counter =1;
        mShowList = new ArrayList<>();
        mCheckeditems = new ArrayList<>();
          /* To ListDown the OUT OF STOCK ITEMS FROM ALL CATEGORIES */
        mExtraDb = new ExtraDb(getApplicationContext());
        String data_extra = mExtraDb.getRedData();
        String data_extraa[] = data_extra.split("\n");
        for (int i = 0; i < data_extraa.length;i++) {
              if(!TextUtils.isEmpty(data_extraa[i])) { 
                  String Quantity=mExtraDb.getQuantity(data_extraa[i]);
                  mShowList.add(new Show(data_extraa[i], counter,Quantity));
                  counter = counter + 1;
            }
        }
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager showlayoutManager = new LinearLayoutManager(this);
        showlayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(showlayoutManager);
        SA =new ShowAdapter(getApplicationContext(),mShowList);
        recyclerView.setAdapter(SA);
        
    }
 @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_show, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
          switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
              case R.id.action_refresh:
                  /* To Save the deleted entries */
                  SA.display();
                  return true;
              case R.id.action_add:
                  /*Customize the List of Products in each Category by adding anything missed or required */
                  AlertDialog.Builder builder = new AlertDialog.Builder(this);
                  builder.setTitle("Enter a new Product");
                  final EditText input = new EditText(DeleteActivity.this);
                  LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                          LinearLayout.LayoutParams.MATCH_PARENT,
                          LinearLayout.LayoutParams.MATCH_PARENT);
                  input.setLayoutParams(lp);
                  builder.setView(input);
                  builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialogInterface, int i) {
                              if (! input.getText().toString().isEmpty()) {
                                  mExtraDb = new ExtraDb(getApplicationContext());
                                  int dcheck = mExtraDb.checkproduct(input.getText().toString().toLowerCase());
                                  if (dcheck == 0) {
                                          long id = mExtraDb.insertData(input.getText().toString().toLowerCase(), 0, 0, 0,"Extra");

                                      }    mShowList.add(new Show(input.getText().toString(), counter,"1"));
                                   counter = counter + 1;
                                   SAadd = new ShowAdapter(getApplicationContext(), mShowList);
                                          recyclerView.setAdapter(SAadd);
                              }
                              else
                                  Message.message(getApplicationContext(), "Enter valid string");
                          }


                  });
                  builder.show();
                  
                  return true;

        }
        return super.onOptionsItemSelected(item);
    }

}
