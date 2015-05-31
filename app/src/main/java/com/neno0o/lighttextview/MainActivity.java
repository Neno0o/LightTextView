package com.neno0o.lighttextview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;

import com.neno0o.lighttextviewlib.LightTextView;


public class MainActivity extends AppCompatActivity {

    Button button;
    CardView cardView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        cardView = (CardView) findViewById(R.id.card_view);
        imageView = (ImageView) findViewById(R.id.image);

        LightTextView lightTextView = new LightTextView(this);
        lightTextView.setText("CLICK");
        lightTextView.setBackgroundColor(getResources().getColor(R.color.blue));
        lightTextView.setCurrentView(button);

        LightTextView lightTextView2 = new LightTextView(this);
        lightTextView2.setText("HOT");
        lightTextView2.setBackgroundColor(getResources().getColor(R.color.red));
        lightTextView2.setPosition(LightTextView.Position.RIGHT_CORNER);
        lightTextView2.setCurrentView(cardView);

        LightTextView lightTextView3 = new LightTextView(this);
        lightTextView3.setText("HD");
        lightTextView3.setBackgroundColor(getResources().getColor(R.color.black));
        lightTextView3.setCurrentView(imageView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
