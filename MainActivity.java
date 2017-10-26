package com.iqbalproject.aplikasigis;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.iqbalproject.aplikasigis.Fragment.AboutFragment;
import com.iqbalproject.aplikasigis.Fragment.HomeFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;


public class MainActivity extends AppCompatActivity {

    //private agar hanya dapat digunakan oleh main activity saja
    private BottomBar mBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //action pada Bottom Bar
        mBottomBar = (BottomBar) findViewById(R.id.bottomBar);
        mBottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            Fragment mFragment = null;
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tabHome){
                    mFragment = new HomeFragment();
                }else if (tabId == R.id.tabAbout){
                    mFragment = new AboutFragment();
                }else if (tabId == R.id.tabProfil){
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content, mFragment)
                        .commit();
            }
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Apakah Anda ingin keluar ?")
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Server.STATUS_LOGIN = false;
                        finish();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

}
