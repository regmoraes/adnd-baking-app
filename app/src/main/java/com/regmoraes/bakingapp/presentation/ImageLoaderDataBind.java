package com.regmoraes.bakingapp.presentation;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;


/**
 *   Copyright {2017} {Rômulo Eduardo G. Moraes}
 **/
public final class ImageLoaderDataBind {

    @BindingAdapter({"bind:imageUrl", "bind:placeholder"})
    public static void loadPicture(ImageView view, String imageUrl, Drawable placeholder) {

        RequestManager defaultRequest = Glide.with(view.getContext());

        if (placeholder != null) {

            defaultRequest.setDefaultRequestOptions(
                    new RequestOptions()
                            .placeholder(placeholder)
            );
        }

        defaultRequest
                .load(imageUrl)
                .into(view);
    }
}
