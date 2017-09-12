package me.amjadalmutairi.almustaghfirin;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends AppCompatActivity {

    @BindView(R.id.github_button)
    ImageButton github;
    @BindView(R.id.mail_button)
    ImageButton sendMail;
    @BindView(R.id.dev_view)
    TextView devTextView;
    @BindView(R.id.source_code_view)
    TextView sourceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setTitle("");
        ButterKnife.bind(this);
        Typeface arabicFont = Typeface.createFromAsset(getAssets(), "fonts/cairo-light.ttf");
        devTextView.setTypeface(arabicFont);
        sourceTextView.setTypeface(arabicFont);

        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://www.example.com";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        sendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","mutairi.amjad@gmail.com", null));
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });
    }
}
