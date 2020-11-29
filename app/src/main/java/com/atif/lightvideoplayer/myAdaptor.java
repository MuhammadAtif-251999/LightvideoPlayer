package com.atif.lightvideoplayer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class myAdaptor extends RecyclerView.Adapter {

    private Context context;
    ArrayList<File> VideoArrayList;

    public myAdaptor(Context context, ArrayList<File> videoArrayList) {
        this.context = context;
        VideoArrayList = videoArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View mView= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.video_item,viewGroup,false);

        return new VideoHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
   // VideoHolder.txtFileName.setText(MainActivity.fileArrayList.get(position).getName());
        Bitmap bitmapThumbnail= ThumbnailUtils.createVideoThumbnail(VideoArrayList.get(position).getPath(), MediaStore.Images.Thumbnails.MINI_KIND);
        VideoHolder.imageThumbnail.setImageBitmap(bitmapThumbnail);

        VideoHolder.myCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,videoPlayer.class);
                intent.putExtra("position",holder.getAdapterPosition());
                context.startActivity(intent);

            }
        });

    }


    @Override
    public int getItemCount() {
        if (VideoArrayList.size()>0){
            return VideoArrayList.size();
        }
        else return 1;
    }
}
class VideoHolder extends RecyclerView.ViewHolder{

   static TextView txtFileName;
   static ImageView imageThumbnail;
    static CardView myCardView;


    VideoHolder(View view){
        super(view);
    txtFileName=view.findViewById(R.id.txt_videoFileName);
    imageThumbnail=view.findViewById(R.id.iv_thumbail);
    myCardView=view.findViewById(R.id.myCardView);

    }
}
