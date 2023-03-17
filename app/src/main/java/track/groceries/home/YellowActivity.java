package track.groceries.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import track.groceries.home.R;

import java.util.ArrayList;
import java.util.List;

public class YellowActivity extends AppCompatActivity {
       private ExtraDb mFruitDatabase;
      RecyclerView recyclerView;
    ConstraintLayout framelayout;
    public static List<Show> mShowList;
    private ShowAdapter SA;
    private String data_fruit;
    private Integer counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_yellow);
        framelayout = findViewById(R.id.Yellow);
        mFruitDatabase = new ExtraDb(getApplicationContext());
        data_fruit = mFruitDatabase.getYellowData();
        String Heading = "Items in Use";
        mShowList = new ArrayList<>();
        counter =1;
        String data[] = data_fruit.split("\n");
        for (int i = 0; i < data.length;i++) {
            if(!TextUtils.isEmpty(data[i])) {
                mShowList.add(new Show(data[i], counter,"1"));
                counter = counter + 1;
            }
        }

        /*
        mSpiceDatabase = new SpiceDbAdapter(getApplicationContext());
        String spicedata = mSpiceDatabase.getYellowData();
        String sdata[] = spicedata.split("\n");
        for (int i = 0; i < sdata.length; i++) {
            if(!TextUtils.isEmpty(sdata[i])) {
                mShowList.add(new Show(sdata[i], counter,1));
                counter = counter + 1;
            }
        }
        mDiaryDatabase = new DiaryDbAdapter(getApplicationContext());
        String diarydata = mDiaryDatabase.getYellowData();
        String ddata[] = diarydata.split("\n");
        for (int i = 0; i < ddata.length; i++) {
            if(!TextUtils.isEmpty(ddata[i])) {
                mShowList.add(new Show(ddata[i], counter,1));
                counter = counter + 1;
            }
        }
        mToiletriesDatabase = new ToiletDbAdapter(getApplicationContext());
        String toiletdata = mToiletriesDatabase.getYellowData();
        String tdata[] = toiletdata.split("\n");
        for (int i = 0; i < tdata.length; i++) {
            if(!TextUtils.isEmpty(tdata[i])) {
                mShowList.add(new Show(tdata[i], counter,1));
                counter = counter + 1;
            }
        }

        mPulsesDatabase = new PulseDbAdapter(getApplicationContext());
        String pulsedata = mPulsesDatabase.getYellowData();
        String pdata[] = pulsedata.split("\n");
        for (int i = 0; i < pdata.length; i++) {
            if(!TextUtils.isEmpty(pdata[i])) {
                mShowList.add(new Show(pdata[i], counter,1));
                counter = counter + 1;
            }
        }

        mCerealsDatabase = new CerealsDbAdapter(getApplicationContext());
        String cedata = mCerealsDatabase.getYellowData();
        String cerdata[] = cedata.split("\n");
        for (int i=0; i<cerdata.length;i++)
        {
            if(!TextUtils.isEmpty(cerdata[i]))
            {
                mShowList.add(new Show(cerdata[i],counter,1));
                counter = counter +1;

            }
        }

        mOilsDatabase = new OilsDbAdapter(getApplicationContext());
        String oilsdata = mOilsDatabase.getYellowData();
        String oildata[] = oilsdata.split("\n");
        for (int i=0; i<oildata.length;i++)
        {
            if(!TextUtils.isEmpty(oildata[i]))
            {
                mShowList.add(new Show(oildata[i],counter,1));
                counter = counter +1;

            }
        }

        mStationDatabase = new StationDbAdapter(getApplicationContext());
        String stadata = mStationDatabase.getYellowData();
        String statdata[] = stadata.split("\n");
        for (int i=0; i<statdata.length;i++)
        {
            if(!TextUtils.isEmpty(statdata[i]))
            {
                mShowList.add(new Show(statdata[i],counter,1));
                counter = counter +1;

            }
        }

*/
        recyclerView = findViewById(R.id.RView);
        LinearLayoutManager showlayoutManager = new LinearLayoutManager(this);
        showlayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(showlayoutManager);
        SA =new ShowAdapter(getApplicationContext(),mShowList);
        recyclerView.setAdapter(SA);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_refresh:
                SA.display();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}