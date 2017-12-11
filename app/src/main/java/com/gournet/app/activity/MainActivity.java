package com.gournet.app.activity;




import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.gournet.app.R;
import com.gournet.app.fragment.EventFragment;
import com.gournet.app.fragment.LocationFragment;


import com.gournet.app.fragment.MapsFragment;
import com.gournet.app.fragment.NotificationsFragment;
import com.gournet.app.fragment.RestaurantsFragment;
import com.gournet.app.fragment.SplitFragment;
import com.gournet.app.model.Event;
import com.gournet.app.model.Token;
import com.gournet.app.model.User;
import com.gournet.app.other.SessionManager;
import com.gournet.app.rest.ApiClient;
import com.gournet.app.rest.ApiEndpointInterface;

import java.io.IOException;
import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

import static com.bumptech.glide.request.RequestOptions.circleCropTransform;


//class GetAvatar extends AsyncTask<Object, Void, Void>{
//    private MainActivity context;
//
//
//    GetAvatar(MainActivity context ) {
//        this.context = context;
//
//    }
//
//    @Override
//    protected Void doInBackground(Object... o) {
//        Retrofit client = ApiClient.service;
//        ApiEndpointInterface.myFullSizeAvatar service = client.create(ApiEndpointInterface.myFullSizeAvatar.class);
//        ResponseBody body=null;
//
//       try {
//            body = service.getImage("user").execute().body();
//        } catch (IOException e) {
//           e.printStackTrace();
//        }
//        byte[] bytes = new byte[0];
//        if (body != null)
//            try {
//                bytes = body.bytes();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//          //final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//        final ImageView imageView = context.navigationView.getHeaderView(0).findViewById(R.id.profileImg);
//
//
//        final byte[] finalBytes = bytes;
//        context.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Glide.with(context).load(finalBytes)
//                        .crossFade()
//                        .thumbnail(0.5f)
//                        .bitmapTransform(new CircleTransform(context))
//                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                        .into(imageView);
//            }
//        });
//        return null;
//    }
//}

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,EventFragment.OnListFragmentInteractionListener {
     SessionManager session;
    private DrawerLayout drawer;
    private  ActionBarDrawerToggle toggle;
    public NavigationView navigationView;
    private TextView navUsername;
    private TextView navFullName;
    private ImageView navProfileImg;
    private int currentNavigationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        session=new SessionManager(MainActivity.this);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



         drawer =  findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

         navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //HashMap<String, String> user =session.getSessionData();

          Bundle extras = getIntent().getExtras();
        User user = (User) extras.getSerializable("user");
        navFullName= navigationView.getHeaderView(0).findViewById(R.id.txtFirstLastName);
        navUsername= navigationView.getHeaderView(0).findViewById(R.id.txtUsername);

           navFullName.setText(user.getUsername());
           navUsername.setText(user.getFullName()) ;

         //  navFullName.setText(user.get(SessionManager.FULL_NAME));
          // navUsername.setText(user.get(SessionManager.USERNAME)) ;
       // new GetAvatar(MainActivity.this).execute();
             getAvatar();

             FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
            tx.replace(R.id.frame, new SplitFragment());
            tx.commit();

            currentNavigationId=navigationView.getMenu().getItem(0).getItemId();
            navigationView.setCheckedItem(R.id.nav_home);

    }

     public void getAvatar()
     {
      ApiClient.imageService.create(ApiEndpointInterface.myFullSizeAvatar.class).getImage("user")
              .subscribeOn(Schedulers.io())
             .observeOn(AndroidSchedulers.mainThread())
             .subscribe(image -> {
                 final ImageView imageView = navigationView.getHeaderView(0).findViewById(R.id.profileImg);
                 Glide.with(MainActivity.this).load(image)
                         .thumbnail(0.5f)
                         .apply(circleCropTransform())
                         .into(imageView);

             });
     }

    @Override
    public void onBackPressed() {
         drawer = findViewById(R.id.drawer_layout);
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
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.action_bar_notification, menu);

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
        if(id!=currentNavigationId) {

            Fragment fragment;
            switch (id) {
                case R.id.nav_home:

                    fragment = new SplitFragment();

                    break;

                case R.id.nav_restaurants:
                    fragment = new RestaurantsFragment();
                    break;

                case R.id.nav_location:
                    fragment = new LocationFragment();
                    break;

                case R.id.nav_notification:
                    fragment = new NotificationsFragment();
                    break;

                default:
                    fragment = new MapsFragment();
            }
            if (fragment != null) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frame, fragment);
                ft.commit();
            }
            currentNavigationId=id;
        }

          drawer =  findViewById(R.id.drawer_layout);
         drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onListFragmentInteraction(Event item) {

    }
}
