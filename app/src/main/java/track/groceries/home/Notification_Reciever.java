package track.groceries.home;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;

import track.groceries.home.R;


public class Notification_Reciever extends BroadcastReceiver {
    private final String CHANNEL_ID="Personal Notifications";

    @Override
    public void onReceive(Context context,Intent intent)
    {
        //if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
        createNotificationChannel(context);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent main = new Intent(context, MainActivity.class);
        //main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingMain = PendingIntent.getActivity(context, 100, main, 0);
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
