package sijangkar.perhubungan.jatengprov.myapplication;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SplashscreenActivity extends AppCompatActivity {

    TextView signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashscreenActivity.this,LoginActivity.class));
            }
        },2000);

        Typeface typeface = ResourcesCompat.getFont(this, R.font.petitabold);
        Typeface typeface2 = ResourcesCompat.getFont(this, R.font.petitamedium);
        signin=(TextView) findViewById(R.id.textView3);
        signin.setTypeface(typeface);
    }
}
