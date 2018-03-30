package com.regmoraes.bakingapp.presentation;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.regmoraes.bakingapp.R;


/**
 *   Copyright {2017} {Rômulo Eduardo G. Moraes}
 **/
public final class ImageLoaderDataBind {

    @BindingAdapter("databind:imageUrlPath")
    public static void loadPicture(ImageView view, String imageUrl) {


        Glide.with(view.getContext())
                .setDefaultRequestOptions(
                        new RequestOptions()
                                .placeholder(R.drawable.ic_chef_hat_and_fork)
                )
                .load(imageUrl)
                .into(view);
    }
}
