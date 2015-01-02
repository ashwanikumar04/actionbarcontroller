package com.ashwanik.useractionbarcontroller;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;


public class MainActivity extends Activity {
    UserActionBarController mActionBarController;
    LinearLayout linearLayout;
    Button showActionBarWithHome;
    Button showActionBarWithShare;
    Button showActionBarWithPlay;
    UserActionBarData data;
    UserActionBarController.ActionListener actionListener = new UserActionBarController.ActionListener() {
        @Override
        public void onAction(Parcelable token) {
            Toast.makeText(MainActivity.this, ((UserActionBarData) token).getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showActionBarWithHome = (Button) findViewById(R.id.showActionBarWithHome);
        showActionBarWithHome.setOnClickListener(new View.OnClickListener() {
                                                     @Override
                                                     public void onClick(View view) {
                                                         if (mActionBarController != null) {
                                                             mActionBarController.hideMessageBar(true);
                                                         }
                                                         mActionBarController = new UserActionBarController(linearLayout, actionListener, R.id.Actionbar_message, R.id.Actionbar_button, R.drawable.ic_home);
                                                         mActionBarController.setHideTime(4000);
                                                         data = new UserActionBarData("Home Clicked");
                                                         mActionBarController.showMessageBar(true, "Click Here", data, "Home");
                                                     }
                                                 }
        );
        showActionBarWithShare = (Button) findViewById(R.id.showActionBarWithShare);
        showActionBarWithShare.setOnClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(View view) {
                                                          if (mActionBarController != null) {
                                                              mActionBarController.hideMessageBar(true);
                                                          }
                                                          mActionBarController = new UserActionBarController(linearLayout, actionListener, R.id.Actionbar_message, R.id.Actionbar_button, R.drawable.ic_action_share);
                                                          mActionBarController.setHideTime(4000);
                                                          data = new UserActionBarData("Share Clicked");
                                                          mActionBarController.showMessageBar(true, "Click Here", data, "Share");
                                                      }
                                                  }
        );
        showActionBarWithPlay = (Button) findViewById(R.id.showActionBarWithPlay);
        showActionBarWithPlay.setOnClickListener(new View.OnClickListener() {
                                                     @Override
                                                     public void onClick(View view) {
                                                         if (mActionBarController != null) {
                                                             mActionBarController.hideMessageBar(true);
                                                         }
                                                         mActionBarController = new UserActionBarController(linearLayout, actionListener, R.id.Actionbar_message, R.id.Actionbar_button, R.drawable.ic_play_over_video);
                                                         mActionBarController.setHideTime(4000);
                                                         data = new UserActionBarData("Play Clicked");
                                                         mActionBarController.showMessageBar(true, "Click Here", data, "Play");
                                                     }
                                                 }
        );
        linearLayout = (LinearLayout) findViewById(R.id.actionBar);
        linearLayout.setVisibility(View.GONE);

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
