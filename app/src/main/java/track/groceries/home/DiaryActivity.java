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


public class DiaryActivity extends AppCompatActivity {

    public static RecyclerView diaryrecyclerview;
    public static List<Model> diarymodelArrayList;
    private DiaryDbAdapter Diaryhelper;
    private String product;
    private DiaryDbAdapter mDiaryDatabase;
    private DiaryModelAdapter diarymodelAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_diary);
        diaryrecyclerview = findViewById(R.id.diaryrecycler_list);
        LinearLayoutManager diarylayoutManager = new LinearLayoutManager(this);
        diarylayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        diaryrecyclerview.setLayoutManager(diarylayoutManager);
        DividerItemDecoration diarydivider = new DividerItemDecoration(diaryrecyclerview.getContext(), DividerItemDecoration.VERTICAL);
        diarydivider.setDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.my_custom_divider));
        diaryrecyclerview.addItemDecoration(diarydivider);
        mDiaryDatabase = new DiaryDbAdapter(this);
        diarymodelArrayList = mDiaryDatabase.getdata();
        diarymodelAdapter = new DiaryModelAdapter(this, diarymodelArrayList, new DiaryModelListener());
        diaryrecyclerview.setAdapter(diarymodelAdapter);


    }


    private class DiaryModelListener implements DiaryModelAdapter.CustomAdapterListener {
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
                  final EditText input = new EditText(DiaryActivity.this);
                  LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                          LinearLayout.LayoutParams.MATCH_PARENT,
                          LinearLayout.LayoutParams.MATCH_PARENT);
                  input.setLayoutParams(lp);
                  builder.setView(input);
                  builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialogInterface, int i) {
                          Diaryhelper = new DiaryDbAdapter(getApplicationContext());
                          String value = input.getText().toString().trim();
                          if (!value.isEmpty()) {
                              String inputdata = value.substring(0, 1).toUpperCase() + value.substring(1).toLowerCase();
                              int dcheck = Diaryhelper.checkproduct(inputdata);
                              if (dcheck == 0) {

                                  long id = Diaryhelper.insertData(inputdata, 100, 0, 0);
                                  diarymodelArrayList = Diaryhelper.getdata();
                                  diarymodelAdapter = new DiaryModelAdapter(getApplicationContext(), diarymodelArrayList, new DiaryModelListener());
                                  diaryrecyclerview.setAdapter(diarymodelAdapter);


                              } else {
                                  Message.message(getApplicationContext(), "Product Already Exist");
                              }
                          } else
                              Message.message(getApplicationContext(), "Enter Valid String");
                      }
                  });
                  builder.show();
                  return true;
          }
        return super.onOptionsItemSelected(item);
    }
}
