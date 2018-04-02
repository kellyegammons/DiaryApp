package com.example.mariette.diaryapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mariette.diaryapp.model.Post;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<Post> mPosts;
    private Context mContext;

    public PostAdapter(List<Post> mPosts, Context mContext) {
        this.mPosts = mPosts;
        this.mContext = mContext;
    }

    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View postView = inflater.inflate(R.layout.list_post, parent, false);
        ViewHolder viewHolder = new ViewHolder(postView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PostAdapter.ViewHolder holder, int position) {
        final Post post = mPosts.get(position);
        final Format dateFormat = new SimpleDateFormat("E, MMMM d yyyy 'at' h:mm a");

        holder.viewPostTitle.setText(post.getPostTitle());
        holder.viewPostDate.setText(dateFormat.format(post.getPostTimeStamp()));

        //onClick event to send to add grade activity
        holder.mView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(mContext, ViewPostActivity.class);
                    intent.putExtra(Diary.POST_KEY, post);
                    mContext.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView viewPostTitle;
        public TextView viewPostDate;
        public View mView;
        public ViewHolder(View postView) {
            super(postView);

            viewPostTitle = (TextView) postView.findViewById(R.id.tvPostTitle);
            viewPostDate = (TextView) postView.findViewById(R.id.tvPostDate);
            mView = postView;
        }
    }
}
