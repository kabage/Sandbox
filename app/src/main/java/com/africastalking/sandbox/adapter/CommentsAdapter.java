package com.africastalking.sandbox.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.africastalking.swipe.R;
import com.africastalking.sandbox.model.MessageModel;

import java.util.ArrayList;

/**
 * Created by Lawrence on 11/4/15.
 */
public class CommentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<MessageModel> commentsList;


    public CommentsAdapter(Context mContext, ArrayList<MessageModel> commentsList) {
        this.mContext = mContext;
        this.commentsList = commentsList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.item_comment, parent, false);

        final CommentViewHolder commentViewHolder = new CommentViewHolder(view);
        return commentViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MessageModel model = commentsList.get(position);


        if (model != null) {
            final CommentViewHolder viewHolder = (CommentViewHolder) holder;
            viewHolder.tvUser.setText(model.commentor);
            viewHolder.tvComment.setText(model.comment);
        }


    }


    @Override
    public int getItemCount() {
        return (null != commentsList ? commentsList.size() : 0);
    }


    public static class CommentViewHolder extends RecyclerView.ViewHolder {

        ImageView ivUserAvatar;
        TextView tvUser, tvComment;


        public CommentViewHolder(View view) {
            super(view);

            ivUserAvatar = (ImageView) view.findViewById(R.id.ivUserAvatar);
            tvUser = (TextView) view.findViewById(R.id.tvUser);
            tvComment = (TextView) view.findViewById(R.id.tvComment);
        }
    }
}
