package com.gournet.app.activity;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.gournet.app.R;
import com.gournet.app.fragment.HomeFragment;
import com.gournet.app.fragment.LocationFragment;
import com.gournet.app.fragment.NotificationsFragment;
import com.gournet.app.fragment.RestaurantsFragment;
import com.gournet.app.model.Token;
import com.gournet.app.rest.ApiClient;
import com.gournet.app.rest.ApiEndpointInterface;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;


class GetAvatar extends AsyncTask<Object, Void, Void>{
    private MainActivity context;
    private Token token;

    GetAvatar(MainActivity context, Token token) {
        this.context = context;
        this.token = token;
    }

    @Override
    protected Void doInBackground(Object... o) {
        Retrofit client = ApiClient.generateWToken(token.access);
        ApiEndpointInterface.myAvatarService service = client.create(ApiEndpointInterface.myAvatarService.class);
        ResponseBody body = null;
        try {
            body = service.getImage("user", 48).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bytes = new byte[0];
        if (body != null)
            try {
                bytes = body.bytes();
            } catch (IOException e) {
                e.printStackTrace();
            }
        final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        final ImageView imageView = context.navigationView.getHeaderView(0).findViewById(R.id.imageView);
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                imageView.setImageBitmap(bitmap);
            }
        });
        return null;
    }
}

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private  ActionBarDrawerToggle toggle;
    public NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



         drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

         navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Bundle extras = getIntent().getExtras();

        Token token = (Token) extras.getSerializable("token");

        new GetAvatar(MainActivity.this, token).execute();
    }

    @Override
    public void onBackPressed() {
         drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment=null;
         switch (id)
         {
             case R.id.nav_home:
              fragment=new HomeFragment();
              break;

             case R.id.nav_restaurants:
                 fragment=new RestaurantsFragment();
                 break;

             case R.id.nav_location:
                 fragment=new LocationFragment();
                 break;

             case R.id.nav_notification:
                 fragment=new NotificationsFragment();
                 break;

                 default:
                     fragment=new HomeFragment();
         }
           if(fragment!=null) {
               FragmentManager fm=getSupportFragmentManager();
               FragmentTransaction ft=fm.beginTransaction();
                       ft.replace(R.id.frame,fragment);
                       ft.commit();
           }
         drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
