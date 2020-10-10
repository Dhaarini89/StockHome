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
    private SpiceDbAdapter mSpiceDatabase;
    private DiaryDbAdapter mDiaryDatabase;
    private ToiletDbAdapter mToiletriesDatabase;
    private PulseDbAdapter mPulsesDatabase;
    private FruitDbAdapter mFruitDatabase;
    private CerealsDbAdapter mCerealsDatabase;
    private OilsDbAdapter mOilsDatabase;
    private StationDbAdapter mStationDatabase;
    private ExtraDb mExtraDb;
    RecyclerView recyclerView;
    public static List<Show> mShowList;
    private ShowAdapter SA,SAadd;
    private String data_fruit;
    private Integer counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_delete);
        counter =1;
        mShowList = new ArrayList<>();

        /* To ListDown the OUT OF STOCK ITEMS FROM ALL CATEGORIES */

        mExtraDb = new ExtraDb(getApplicationContext());
        String data_extra = mExtraDb.getRedData();
        String data_extraa[] = data_extra.split("\n");
        for (int i = 0; i < data_extraa.length;i++) {
              if(!TextUtils.isEmpty(data_extraa[i])) {
                mShowList.add(new Show(data_extraa[i], counter));
                counter = counter + 1;
            }
        }

        mFruitDatabase = new FruitDbAdapter(getApplicationContext());
        data_fruit = mFruitDatabase.getRedData();
        String data[] = data_fruit.split("\n");
        for (int i = 0; i < data.length;i++) {
            if(!TextUtils.isEmpty(data[i])) {
                mShowList.add(new Show(data[i], counter));
                counter = counter + 1;
            }
        }
        mSpiceDatabase = new SpiceDbAdapter(getApplicationContext());
        String spicedata = mSpiceDatabase.getRedData();
        String sdata[] = spicedata.split("\n");
        for (int i = 0; i < sdata.length; i++) {
            if(!TextUtils.isEmpty(sdata[i])) {
                mShowList.add(new Show(sdata[i], counter));
                counter = counter + 1;
            }
        }
        mDiaryDatabase = new DiaryDbAdapter(getApplicationContext());
        String diarydata = mDiaryDatabase.getRedData();
        String ddata[] = diarydata.split("\n");
        for (int i = 0; i < ddata.length; i++) {
            if(!TextUtils.isEmpty(ddata[i])) {
                mShowList.add(new Show(ddata[i], counter));
                counter = counter + 1;
            }
        }
        mToiletriesDatabase = new ToiletDbAdapter(getApplicationContext());
        String toiletdata = mToiletriesDatabase.getRedData();
          String tdata[] = toiletdata.split("\n");
        for (int i = 0; i < tdata.length; i++) {
            if(!TextUtils.isEmpty(tdata[i])) {
                mShowList.add(new Show(tdata[i], counter));
                counter = counter + 1;
            }
        }

        mPulsesDatabase = new PulseDbAdapter(getApplicationContext());
        String pulsedata = mPulsesDatabase.getRedData();
        String pdata[] = pulsedata.split("\n");
        for (int i = 0; i < pdata.length; i++) {
            if(!TextUtils.isEmpty(pdata[i])) {
                mShowList.add(new Show(pdata[i], counter));
                counter = counter + 1;
            }
        }

        mCerealsDatabase = new CerealsDbAdapter(getApplicationContext());
        String cerealsdata = mCerealsDatabase.getRedData();
        String cdata[] = cerealsdata.split("\n");
        for (int i = 0; i < cdata.length; i++) {
            if(!TextUtils.isEmpty(cdata[i])) {
                mShowList.add(new Show(cdata[i], counter));
                counter = counter + 1;
            }
        }

        mOilsDatabase = new OilsDbAdapter(getApplicationContext());
        String oilsdata = mOilsDatabase.getRedData();
        String oildata[] = oilsdata.split("\n");
        for (int i = 0; i < oildata.length; i++) {
            if(!TextUtils.isEmpty(oildata[i])) {
                mShowList.add(new Show(oildata[i], counter));
                counter = counter + 1;
            }
        }

        mStationDatabase = new StationDbAdapter(getApplicationContext());
        String stationdata = mStationDatabase.getRedData();
        String stdata[] = stationdata.split("\n");
        for (int i = 0; i < stdata.length; i++) {
            if(!TextUtils.isEmpty(stdata[i])) {
                mShowList.add(new Show(stdata[i], counter));
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
                                          long id = mExtraDb.insertData(input.getText().toString().toLowerCase(), 100, 0, 0);

                                      }    mShowList.add(new Show(input.getText().toString(), counter));
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
