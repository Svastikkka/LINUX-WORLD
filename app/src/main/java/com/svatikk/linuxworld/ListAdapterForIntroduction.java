package com.svatikk.linuxworld;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.util.List;

/**
 * Created by manshusharma on 12/10/17.
 */
public class ListAdapterForIntroduction extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Data> dataItems;
    ImageLoader imageLoader = MySingletoneForAll.getInstance().getImageLoader();
    Context context ;

    public ListAdapterForIntroduction(Activity activity, List<Data> dataItems) {
        this.activity = activity;
        this.dataItems = dataItems;
    }

    @Override
    public int getCount() {
        return dataItems.size();
    }

    @Override
    public Object getItem(int location) {
        return dataItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.card_view_for_introduction, null);

        if (imageLoader == null)
            imageLoader = MySingletoneForAll.getInstance().getImageLoader();
        ImageView thumbNail = (ImageView) convertView
                .findViewById(R.id.thumbnail);


        TextView title = (TextView) convertView.findViewById(R.id.title);

        CardView cardView = (CardView) convertView.findViewById(R.id.cardView);




        // getting movie data for the row
        Data m = dataItems.get(position);

        // thumbnail image
        // thumbNail.setImageUrl(m.getThumbnailUrl(),imageLoader);
        //Glide.with(convertView).load(m.getThumbnailUrl()).apply(RequestOptions.placeholderOf(R.drawable.logo_of_jiet)).apply(RequestOptions.overrideOf(450, 450)).apply(RequestOptions.fitCenterTransform()).into(thumbNail);
        UrlImageViewHelper.setUrlDrawable(thumbNail, m.getThumbnailUrl(),R.drawable.loading);



        //add animation to imageview
        Animation animation = AnimationUtils.loadAnimation(convertView.getContext(), R.anim.welcome_fade_in);
        thumbNail.setAnimation(animation);

        //add animation to cardview
        Animation animation2 = AnimationUtils.loadAnimation(convertView.getContext(), R.anim.move_up);
        cardView.setAnimation(animation2);

        // title
        title.setText(m.getTitle());


        return convertView;
    }

}