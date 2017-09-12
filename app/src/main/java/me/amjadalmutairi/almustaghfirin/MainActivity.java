package me.amjadalmutairi.almustaghfirin;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    public static final String PREFS_NAME = "IstighfarCounter";
    private int counter ;

    @BindView(R.id.incrementButton) ImageButton increment;
    @BindView(R.id.decrementButton)  ImageButton decrement;
    @BindView(R.id.resetButton)  ImageButton reset;
    @BindView(R.id.counterView)  TextView counterTextView;
    @BindView(R.id.astaghfirullahTextView)  TextView astaghfirullahTextView;
    @BindView(R.id.ayah)  TextView ayahTextView;
    @BindView(R.id.shareButton) ImageButton share;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setTitle("");

        Typeface arabicFont = Typeface.createFromAsset(getAssets(),  "fonts/cairo-light.ttf");
        astaghfirullahTextView.setTypeface(arabicFont);
        ayahTextView.setTypeface(arabicFont);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        counter = settings.getInt("counter", 0);
        counterTextView.setText(String.valueOf(counter));

        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter++;
                counterTextView.setText(String.valueOf(counter));
            }
        });

        decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(counter > 0 ) {
                   counter--;
                   counterTextView.setText(String.valueOf(counter));
               } else {
                   Toast.makeText(getApplicationContext(), getString(R.string.minus_error_message), Toast.LENGTH_SHORT).show();
               }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder confirmReset =   new AlertDialog.Builder(MainActivity.this)
                        .setTitle("")
                        .setMessage(getString(R.string.reset_confirm_q))
                        .setIcon(R.drawable.ic_reset)
                        .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                counter = 0 ;
                                counterTextView.setText(String.valueOf(counter));
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog alertDialog = confirmReset.create();
                alertDialog.show();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String packageName = getApplicationContext().getPackageName();

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/html");
                String shareBody = getString(R.string.share_message) + "\n" + "https://play.google.com/store/apps/details?id=" + packageName ;
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.share_title) + " " + getString(R.string.app) + " " + getString(R.string.app_name));
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_title)));
            }
        });
    }

    @Override
    protected void onStop(){
        super.onStop();
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("counter", counter);
        editor.apply();
    }
}
