package com.gournet.app.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gournet.app.R;
import com.gournet.app.activity.MainActivity;
import com.gournet.app.fragment.EventFragment.OnListFragmentInteractionListener;
import com.gournet.app.model.Event;
import com.gournet.app.rest.ApiClient;
import com.gournet.app.rest.ApiEndpointInterface;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.IOException;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

import static com.bumptech.glide.request.RequestOptions.circleCropTransform;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Event} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class EventRecyclerViewAdapter extends RecyclerView.Adapter<EventRecyclerViewAdapter.ViewHolder> {

    private final List<Event> mValues;
    private final OnListFragmentInteractionListener mListener;


    public EventRecyclerViewAdapter(List<Event> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.eventItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getBusiness().getName());
        holder.mContentView.setText(mValues.get(position).getText());
        int id=mValues.get(position).getBusiness().getId();

        ApiClient.imageService.create(ApiEndpointInterface.businessAvatar.class).getImage("business",id,64)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(image -> {
//                    Glide.with(holder.mImageView.getContext()).load(image)
//                            .thumbnail(0.5f)
//                            .apply(circleCropTransform())
//                           .into(holder.mImageView);

                   holder.mImageView.setImageBitmap(image);

                });


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.eventItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final ImageView mImageView;
        public Event eventItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView =  view.findViewById(R.id.event_name);
            mContentView = view.findViewById(R.id.event_description);
            mImageView=view.findViewById(R.id.event_business_image);


        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }




}
