package track.groceries.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.ActionMode;
import java.util.ArrayList;
import java.util.List;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.view.MenuInflater;

import track.groceries.home.R;

public class ShowStockActivity extends AppCompatActivity  implements ActionMode.Callback{
    private RecyclerView showrecyclerview;
    public static List<Show> mShowList;
    private ActionMode actionMode;
    private boolean isMultiSelect = false;
    private List<Integer> selectedIds = new ArrayList<>();
    private Integer[] selectedPositions;
    private ShowAdapter showAdapter;
    private SpiceDbAdapter mSpiceDatabase;
    private DiaryDbAdapter mDiaryDatabase;
    private ToiletDbAdapter mToiletriesDatabase;
    private PulseDbAdapter mPulsesDatabase;
      private FruitDbAdapter mFruitDatabase;
    private OilsDbAdapter mOilsDatabase;
    private StationDbAdapter mStationDatabase;
    private CerealsDbAdapter mCerealsDatabase;
    private String data_fruit;
    private static Integer a=0;
    private Integer index=0;
    private Integer counter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_show_stock);

        mFruitDatabase = new FruitDbAdapter(getApplicationContext());
        data_fruit = mFruitDatabase.getRedData();
        mShowList = new ArrayList<>();
        counter =1;
        String data[] = data_fruit.split("\n");
        for (int i = 1; i < data.length; i++) {
            mShowList.add(new Show(data[i],counter));
            counter = counter+1;
        }
        mSpiceDatabase = new SpiceDbAdapter(getApplicationContext());
        String spicedata = mSpiceDatabase.getRedData();
        String sdata[] = spicedata.split("\n");
        for (int i = 1; i < sdata.length; i++) {
            mShowList.add(new Show(sdata[i],counter));
            counter = counter +1;
        }

        mDiaryDatabase = new DiaryDbAdapter(getApplicationContext());
        String diarydata = mDiaryDatabase.getRedData();
        String ddata[] = diarydata.split("\n");
        for (int i = 1; i < ddata.length; i++) {
            mShowList.add(new Show(ddata[i],counter));
            counter = counter + 1;
        }

        mToiletriesDatabase = new ToiletDbAdapter(getApplicationContext());
        String toiletdata = mToiletriesDatabase.getRedData();
        String tdata[] = toiletdata.split("\n");
        for (int i = 1; i < tdata.length; i++) {
            mShowList.add(new Show(tdata[i],counter));
            counter = counter + 1;
        }


        mPulsesDatabase = new PulseDbAdapter(getApplicationContext());
        String pulsedata = mPulsesDatabase.getRedData();
        String pdata[] = pulsedata.split("\n");
        for (int i = 1; i < pdata.length; i++) {
            mShowList.add(new Show(pdata[i],counter));
            counter = counter + 1;
        }

        mOilsDatabase = new OilsDbAdapter(getApplicationContext());
        String oilsdata = mOilsDatabase.getRedData();
        String oidata[] = oilsdata.split("\n");
        for (int i = 1; i < oidata.length; i++) {
            mShowList.add(new Show(oidata[i],counter));
            counter = counter + 1;
        }

        mStationDatabase = new StationDbAdapter(getApplicationContext());
        String stadata = mStationDatabase.getRedData();
        String stdata[] = stadata.split("\n");
        for (int i = 1; i < stdata.length; i++) {
            mShowList.add(new Show(stdata[i],counter));
            counter = counter + 1;
        }

        mCerealsDatabase = new CerealsDbAdapter(getApplicationContext());
        String cerdata = mCerealsDatabase.getRedData();
        String cedata[] = cerdata.split("\n");
        for (int i = 1; i < cedata.length; i++) {
            mShowList.add(new Show(cedata[i],counter));
            counter = counter + 1;
        }

        showrecyclerview = findViewById(R.id.show_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        showrecyclerview.setLayoutManager(layoutManager);
        DividerItemDecoration divider = new DividerItemDecoration(showrecyclerview.getContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.my_custom_divider));
        showrecyclerview.addItemDecoration(divider);
        showAdapter = new ShowAdapter(getApplicationContext(), mShowList);
        showrecyclerview.setAdapter(showAdapter);
        showrecyclerview.addOnItemTouchListener(new RecyclerItemClickListener(this, showrecyclerview, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                if (isMultiSelect) {
                     multiSelect(position);
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {
                if (!isMultiSelect) {
                    selectedIds = new ArrayList<>();
                    isMultiSelect = true;

                    if (actionMode == null) {
                      actionMode = startActionMode(ShowStockActivity.this); //show ActionMode.
                    }
                }
                multiSelect(position);
            }
        }));
    }
    private void multiSelect(int position) {
        Show showdata = showAdapter.getItem(position);
        if (showdata != null){
            if (actionMode != null) {

                if (selectedIds.contains(showdata.getId())) {
                    selectedIds.remove(Integer.valueOf(showdata.getId()));
                }
                else
                    selectedIds.add(showdata.getId());
                    selectedPositions[a]=position;
                    a=a+1;

                if (selectedIds.size() > 0) {
                    actionMode.setTitle(String.valueOf(selectedIds.size()));
                  }
                else{
                    actionMode.setTitle("");
                    actionMode.finish();
                }
            }
        }
    }
    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.action_delete:
                StringBuilder stringBuilder = new StringBuilder();
                for (Show showdata : mShowList) {
                    if (selectedIds.contains(showdata.getId())) {
                        stringBuilder.append("\n").append(showdata.getdataList());
                    }
                }
                    for (int i=0;i<selectedPositions.length;i++) {
                        mShowList.remove(selectedPositions[i]);
                    }

                Toast.makeText(this, "Selected items are :" + stringBuilder.toString(), Toast.LENGTH_SHORT).show();

                return true;
        }
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        actionMode = null;
        isMultiSelect = false;
        selectedIds = new ArrayList<>();

    }
}