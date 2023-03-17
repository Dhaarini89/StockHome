package track.groceries.home;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class Notify extends AppCompatActivity {
    ArrayList<NotifyOption> NotifyOptions;
    ListView listView;
    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);
        Button submit;

            listView = (ListView) findViewById(R.id.listView);
            NotifyOptions = new ArrayList();
            NotifyOptions.add(new NotifyOption("Sunday", false,"1 "));
            NotifyOptions.add(new NotifyOption("Monday", false,"2 "));
            NotifyOptions.add(new NotifyOption("Tuesday", false,"3 "));
            NotifyOptions.add(new NotifyOption("Wednesday", false,"4 "));
            NotifyOptions.add(new NotifyOption("Thursday", false,"5 "));
            NotifyOptions.add(new NotifyOption("Friday", false,"6 "));
            NotifyOptions.add(new NotifyOption("Saturday", false,"7 "));
            adapter = new CustomAdapter(NotifyOptions, getApplicationContext());
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView parent, View view, int position, long id) {
                    NotifyOption NotifyOption= NotifyOptions.get(position);
                    NotifyOption.checked = !NotifyOption.checked;
                    adapter.notifyDataSetChanged();


                }
            });
             submit = (Button) findViewById(R.id.submitButton);
             submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb;
                ListView mainListView = (ListView) findViewById(R.id.listView);
                for (int x = 0; x<mainListView.getChildCount();x++){
                    cb = (CheckBox)mainListView.getChildAt(x).findViewById(R.id.checkBox);
                    TextView T = (TextView) mainListView.getChildAt(x).findViewById(R.id.txtName);
                    TextView Tcount = (TextView) mainListView.getChildAt(x).findViewById(R.id.txtCount);
                    String notifyvalue = Tcount.getText().toString() ;
                    Integer notifycheck = Integer.parseInt(notifyvalue);
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY,16);
                    calendar.set(Calendar.MINUTE,30);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    ComponentName reciever = new ComponentName(getApplicationContext(),Notification_Reciever.class);
                    PackageManager pm = getApplicationContext().getPackageManager();
                    pm.setComponentEnabledSetting(reciever,PackageManager.COMPONENT_ENABLED_STATE_ENABLED,PackageManager.DONT_KILL_APP);
                    Intent intent = new Intent(getApplicationContext(), Notification_Reciever.class);
                    intent.putExtra("code",notifycheck);

                    if(cb.isChecked()){
                        if (notifycheck == 1)
                        {
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),1,intent,0);
                            Calendar a = Calendar.getInstance();
                            a.set(Calendar.DAY_OF_WEEK,1);
                            a.set(Calendar.HOUR_OF_DAY,22);
                            a.set(Calendar.MINUTE,00);
                            alarmManager.setInexactRepeating(alarmManager.RTC_WAKEUP,a.getTimeInMillis(),alarmManager.INTERVAL_DAY*7,pendingIntent);

                        }
                        if(notifycheck == 2)
                        {
                            Calendar a = Calendar.getInstance();
                            a.set(Calendar.DAY_OF_WEEK,2);
                            a.set(Calendar.HOUR_OF_DAY,22);
                            a.set(Calendar.MINUTE,00);
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),2,intent,0);
                            alarmManager.setInexactRepeating(alarmManager.RTC_WAKEUP,a.getTimeInMillis(),alarmManager.INTERVAL_DAY*7,pendingIntent);

                        }

                        if (notifycheck == 3)
                        {
                            Calendar a = Calendar.getInstance();
                            a.set(Calendar.DAY_OF_WEEK,3);
                            a.set(Calendar.HOUR_OF_DAY,22);
                            a.set(Calendar.MINUTE,00);
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),3,intent,0);
                            alarmManager.setInexactRepeating(alarmManager.RTC_WAKEUP,a.getTimeInMillis(),alarmManager.INTERVAL_DAY*7,pendingIntent);

                        }
                        if(notifycheck == 4)
                        {
                            Calendar a = Calendar.getInstance();
                            a.set(Calendar.DAY_OF_WEEK,4);
                            a.set(Calendar.HOUR_OF_DAY,22);
                            a.set(Calendar.MINUTE,00);
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),4,intent,0);
                            alarmManager.setInexactRepeating(alarmManager.RTC_WAKEUP,a.getTimeInMillis(),alarmManager.INTERVAL_DAY*7,pendingIntent);

                        }
                        if(notifycheck == 5)
                        {
                            Calendar a = Calendar.getInstance();
                            a.set(Calendar.DAY_OF_WEEK,5);
                            a.set(Calendar.HOUR_OF_DAY,22);
                            a.set(Calendar.MINUTE,00);
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),5,intent,0);
                            alarmManager.setInexactRepeating(alarmManager.RTC_WAKEUP,a.getTimeInMillis(),alarmManager.INTERVAL_DAY*7,pendingIntent);

                        }
                        if(notifycheck == 6)
                        {
                            Calendar a = Calendar.getInstance();
                            a.set(Calendar.DAY_OF_WEEK,6);
                            a.set(Calendar.HOUR_OF_DAY,22);
                            a.set(Calendar.MINUTE,00);
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),6,intent,0);
                            alarmManager.setInexactRepeating(alarmManager.RTC_WAKEUP,a.getTimeInMillis(),alarmManager.INTERVAL_DAY*7,pendingIntent);

                        }

                        if (notifycheck == 7)
                        {
                            Calendar a = Calendar.getInstance();
                            a.set(Calendar.DAY_OF_WEEK,7);
                            a.set(Calendar.HOUR_OF_DAY,22);
                            a.set(Calendar.MINUTE,00);
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),7,intent,0);
                            alarmManager.setInexactRepeating(alarmManager.RTC_WAKEUP,a.getTimeInMillis(),alarmManager.INTERVAL_DAY*7,pendingIntent);

                        }

                        finish();
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor =sharedPreferences.edit();
                        editor.putInt("code",notifycheck);
                        editor.commit();
                    }
                    finish();


                }

            } });


    }
}