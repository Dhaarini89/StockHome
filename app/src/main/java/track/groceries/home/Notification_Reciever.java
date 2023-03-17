package track.groceries.home;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import java.io.Serializable;

import track.groceries.home.R;


public class Notification_Reciever extends BroadcastReceiver implements Serializable {
    private final String CHANNEL_ID="Personal Notifications";
    Integer rcode;

    @Override
    public void onReceive(Context context,Intent intent)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        rcode = sharedPreferences.getInt("code",0);

        //if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
        createNotificationChannel(context);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent main = new Intent(context, MainActivity.class);
        //main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingMain = PendingIntent.getActivity(context, rcode, main, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentIntent(pendingMain)
                .setSmallIcon(R.drawable.home)
                .setContentTitle("Grocery Tracker")
                .setContentText("It's time to Update your stock")
                .setAutoCancel(true);
        notificationManager.notify(100, builder.build());
    }
    //}

    public void createNotificationChannel(Context context)
    {
    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
    {
        CharSequence name="Personal Notifications";
        String description="Include all the personal Notifications";

        int importance = NotificationManager.IMPORTANCE_DEFAULT;

        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,name,importance);
        notificationChannel.setDescription(description);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);
    }

    }

}
