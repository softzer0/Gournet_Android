package com.gournet.app.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.MapFragment;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.gournet.app.R;
import com.gournet.app.model.Event;
import com.gournet.app.model.Marker;
import com.gournet.app.rest.ApiClient;
import com.gournet.app.rest.ApiEndpointInterface;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SplitFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SplitFragment extends Fragment {


    public SplitFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SplitFragment newInstance() {
        SplitFragment fragment = new SplitFragment();

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApiClient.service.create(ApiEndpointInterface.homeService.class).getHome()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(body -> {
                    JsonParser parser = new JsonParser();
                    JsonElement result;
                    if (body != null) {
                        try {
                            result = parser.parse(body.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                            return;
                        }
                    } else return;
                    JsonArray results=result.getAsJsonArray();

                    MapsFragment mapsFragment = (MapsFragment) getChildFragmentManager().findFragmentById(R.id.map);
                    mapsFragment.setMarkers(new Gson().fromJson(results.get(1).getAsJsonObject().getAsJsonArray("results"), Marker[].class));

                    EventFragment eventFragment=(EventFragment)getChildFragmentManager().findFragmentById(R.id.fragment);
                    Type type=new TypeToken<ArrayList<Event>>() {}.getType();
                    eventFragment.setEvents(new Gson().fromJson(results.get(2).getAsJsonObject().getAsJsonArray("results"), type));

                    mapsFragment.populateMarkers();

                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
               return inflater.inflate(R.layout.split_pane_layout,container,false);

    }

}
