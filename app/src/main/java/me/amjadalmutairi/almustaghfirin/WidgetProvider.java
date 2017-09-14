package me.amjadalmutairi.almustaghfirin;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.Toast;

import static me.amjadalmutairi.almustaghfirin.MainActivity.PREFS_NAME;

/**
 * Created by amjadalmutairi on 9/13/17.
 */

public class WidgetProvider extends AppWidgetProvider {

    private static final String MINUS_CLICKED = "minus";
    private static final String RESET_CLICKED = "reset";
    private static final String INCREMENT_CLICKED = "increment";
    private int counter;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        counter = settings.getInt("counter", 0);

        for (int currentWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
            views.setOnClickPendingIntent(R.id.w_minus, getPendingSelfIntent(context, MINUS_CLICKED));
            views.setOnClickPendingIntent(R.id.w_reset, getPendingSelfIntent(context, RESET_CLICKED));
            views.setOnClickPendingIntent(R.id.w_increment, getPendingSelfIntent(context, INCREMENT_CLICKED));
            views.setTextViewText(R.id.w_counter, String.valueOf(counter));
            appWidgetManager.updateAppWidget(currentWidgetId, views);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName thisAppWidget = new ComponentName(context.getPackageName(), WidgetProvider.class.getName());
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);

        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME,  Context.MODE_PRIVATE);
        counter = settings.getInt("counter", 0);

        if (MINUS_CLICKED.equals(intent.getAction())) {
            if (counter > 0) {
                counter--;
                views.setTextViewText(R.id.w_counter, String.valueOf(counter));
                appWidgetManager.updateAppWidget(thisAppWidget, views);
                updateCounter(context);
            } else {
                Toast.makeText(context, R.string.minus_error_message , Toast.LENGTH_SHORT).show();
            }
        }

        if (RESET_CLICKED.equals(intent.getAction())) {
            counter = 0;
            views.setTextViewText(R.id.w_counter, "0");
            appWidgetManager.updateAppWidget(thisAppWidget, views);
            updateCounter(context);
        }

        if (INCREMENT_CLICKED.equals(intent.getAction())) {
            counter++;
            views.setTextViewText(R.id.w_counter, String.valueOf(counter));
            appWidgetManager.updateAppWidget(thisAppWidget, views);
            updateCounter(context);
        }
    }

    private PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }
    private void updateCounter(Context context){
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME,  Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("counter", counter);
        editor.apply();
    }
}