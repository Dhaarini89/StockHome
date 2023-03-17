package track.groceries.home;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.RadioButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Dialog;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Spinner;
import android.net.Uri;
import java.util.Calendar;
import java.util.List;
import android.text.TextUtils;

import track.groceries.home.R;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button btnnext,btn_option;

    private List<Show> mShowList;
    private    Spinner spin,spinproduct;
    private RecyclerView mRecyclerView,mShowRecyclerview;
    public static List<Model> modelArrayList;
    private String[] navlist = new String[]{"Once in a week","Once in 2 weeks"};
    private static int count;

    private ExtraDb AllDb,mFruitDatabase,Fruithelper;
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Integer lastVersion = prefs.getInt("version_code", 1);
        if (lastVersion == 1) {
            /* Insert the available list of products into each of the category table */
               startActivity(new Intent(MainActivity.this, ListLoadActivity.class));
        }
        prefs.edit().putInt("version_code", 0).commit();

        Button B = findViewById(R.id.Show_List);
        B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                /* Show the Out OF Stock Items as a List */
                startActivity(new Intent(MainActivity.this, DeleteActivity.class));
            }

        });
        Button B1 = findViewById(R.id.IN_Progress);
        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                /* Show the Out OF Stock Items as a List */
                startActivity(new Intent(MainActivity.this, YellowActivity.class));
            }

        });

    }

    public  void Fruits(View V) {

              Intent I = new Intent(MainActivity.this, NextActivity.class);
              startActivity(I);

    }
    public  void Spices(View V) {

        Intent I = new Intent(MainActivity.this, SpiceActivity.class);
        startActivity(I);

    }
    public  void Toiletries(View V) {

        Intent I = new Intent(MainActivity.this, ToiletActivity.class);
        startActivity(I);

    }
    public  void Pulses(View V) {

        Intent I = new Intent(MainActivity.this, PulseActivity.class);
        startActivity(I);

    }
    public  void Dairy(View V) {

        Intent I = new Intent(MainActivity.this, DiaryActivity.class);
        startActivity(I);

    }
    public  void Oils(View V) {

        Intent I = new Intent(MainActivity.this, OilsActivity.class);
        startActivity(I);

    }
    public  void Cereals(View V) {

        Intent I = new Intent(MainActivity.this, CerealsActivity.class);
        startActivity(I);

    }
    public  void Station(View V) {

        Intent I = new Intent(MainActivity.this, StationActivity.class);
        startActivity(I);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_reminder:
                Intent I = new Intent(MainActivity.this, Notify.class);
                startActivity(I);
                return true;

            case R.id.action_contact:

                /* Contact email id is mentioned over here */
                AlertDialog.Builder contact_builder = new AlertDialog.Builder(MainActivity.this);
                contact_builder.setTitle(R.string.app_name);
                contact_builder.setIcon(R.drawable.ic_email_black_24dp);
                contact_builder.setMessage("Please send Email to tryandlearn2020@gmail.com")
                                .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });
                AlertDialog contact_alert = contact_builder.create();
                contact_alert.show();
                return true;
            case R.id.action_exit:
                /* Exit dialog is opened and we can exit the app from here */
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.app_name);
                builder.setIcon(R.drawable.home);
                builder.setMessage("Do you want to exit?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
                return true;
            case R.id.action_help:
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.youtube.com/watch?v=lU3_4zLiddc")));
                return true;
            case R.id.action_share:
                              /* Share either Out of Stock Items or Items in Use */
                final Dialog share_dialog = new Dialog(this);
                share_dialog.setContentView(R.layout.share_option);
                ImageButton shareRedB = (ImageButton) share_dialog.findViewById(R.id.BRED);
                shareRedB.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        String data;
                                                        AllDb = new ExtraDb(getApplicationContext());
                                                        String Heading = "Out of Stock List" + "\n";
                                                        data = Heading;
                                                        String fdata = AllDb.getRedData();
                                                        if (!TextUtils.isEmpty(fdata)) {
                                                            data = data + fdata;
                                                        }
                                                   /*     //mSpiceDatabase = new SpiceDbAdapter(getApplicationContext());
                                                        String spicedata = mSpiceDatabase.getRedData();
                                                        if (!TextUtils.isEmpty(spicedata)) {
                                                            data = data + spicedata;
                                                        }
                                                        mDiaryDatabase = new DiaryDbAdapter(getApplicationContext());
                                                        String diarydata = mDiaryDatabase.getRedData();
                                                        if (!TextUtils.isEmpty(diarydata)) {
                                                            data = data + diarydata;
                                                        }
                                                        mToiletriesDatabase = new ToiletDbAdapter(getApplicationContext());
                                                        String toiletdata = mToiletriesDatabase.getRedData();
                                                        if (!TextUtils.isEmpty(toiletdata)) {
                                                            data = data + toiletdata;
                                                        }
                                                        mOilsDatabase = new OilsDbAdapter(getApplicationContext());
                                                        String oilsdata = mOilsDatabase.getRedData();
                                                        if (!TextUtils.isEmpty(oilsdata)) {
                                                            data = data + oilsdata;
                                                        }
                                                        mCerealsDatabase = new CerealsDbAdapter(getApplicationContext());
                                                        String cerealsdata = mCerealsDatabase.getRedData();
                                                        if (!TextUtils.isEmpty(cerealsdata)) {
                                                            data = data + cerealsdata;
                                                        }
                                                        mStationDatabase = new StationDbAdapter(getApplicationContext());
                                                        String stationdata = mStationDatabase.getRedData();
                                                        if (!TextUtils.isEmpty(stationdata)) {
                                                            data = data + stationdata;
                                                        }
                                                        mPulsesDatabase = new PulseDbAdapter(getApplicationContext());
                                                        String pulsedata = mPulsesDatabase.getRedData();
                                                        if (!TextUtils.isEmpty(pulsedata)) {
                                                            data = data + pulsedata;
                                                        }
                                                        */

                                                        Intent share_intent = new Intent();
                                                        share_intent.setAction(Intent.ACTION_SEND);
                                                        share_intent.setType("text/plain");
                                                        share_intent.putExtra(Intent.EXTRA_TEXT,
                                                                data);

                                                        try {
                                                            startActivity(Intent.createChooser(share_intent,
                                                                    "ShareThroughChooser Test"));
                                                        } catch (android.content.ActivityNotFoundException ex) {
                                                            (new android.app.AlertDialog.Builder(getApplicationContext())
                                                                    .setMessage("Share failed")
                                                                    .setPositiveButton("OK",
                                                                            new DialogInterface.OnClickListener() {
                                                                                public void onClick(DialogInterface dialog,
                                                                                                    int whichButton) {
                                                                                }
                                                                            }).create()).show();

                                                        }

                                                    }
                });
                    ImageButton shareYellowB = (ImageButton) share_dialog.findViewById(R.id.BYellow);
                    shareYellowB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String data;
                            AllDb = new ExtraDb(getApplicationContext());
                            String Heading = "Items in Use" + "\n";
                            data = Heading;
                            String fdata = AllDb.getYellowData();
                            if (!TextUtils.isEmpty(fdata)) {
                                data = data + fdata;
                            }

                            Intent share_intent = new Intent();
                            share_intent.setAction(Intent.ACTION_SEND);
                            share_intent.setType("text/plain");
                            share_intent.putExtra(Intent.EXTRA_TEXT,
                                    data);

                            try {
                                startActivity(Intent.createChooser(share_intent,
                                        "ShareThroughChooser Test"));
                            } catch (android.content.ActivityNotFoundException ex) {
                                (new android.app.AlertDialog.Builder(getApplicationContext())
                                        .setMessage("Share failed")
                                        .setPositiveButton("OK",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog,
                                                                        int whichButton) {
                                                    }
                                                }).create()).show();

                            }

                        }
                    });
                share_dialog.show();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }



}
