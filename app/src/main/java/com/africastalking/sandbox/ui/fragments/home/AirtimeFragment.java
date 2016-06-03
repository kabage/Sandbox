package com.africastalking.sandbox.ui.fragments.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.africastalking.sandbox.adapter.CommentsAdapter;
import com.africastalking.sandbox.model.MessageModel;
import com.africastalking.swipe.R;
import com.africastalking.sandbox.ui.widgets.SendCommentButton;

import java.util.ArrayList;

/**
 * Created by Lawrence on 10/29/15.
 */
public class AirtimeFragment extends Fragment {

    public static final String ARG_FEED_ID = "feed_id";
    Handler handle;
    private EditText etComment;
    private SendCommentButton btnSendComment;
    private CommentsAdapter commentsAdapter;
    private RecyclerView rvComments;
    private LinearLayout commentsRootLayout;
    private Toolbar toolbar;

    private ArrayList<MessageModel> commentsList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_comments,
                container, false);

        commentsRootLayout = (LinearLayout) getActivity().findViewById(R.id.commentsRootLayout);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvComments = (RecyclerView) rootView.findViewById(R.id.rvComments);
        rvComments.setLayoutManager(linearLayoutManager);
        rvComments.setHasFixedSize(true);


        rvComments.setOverScrollMode(View.OVER_SCROLL_NEVER);
        rvComments.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
//                    commentsAdapter.setAnimationsLocked(true);
                }
            }
        });

        etComment = (EditText) getActivity().findViewById(R.id.edComment);
        btnSendComment = (SendCommentButton) getActivity().findViewById(R.id.btnSendComment);
//        btnSendComment.setOnSendClickListener(getActivity());


        handle = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                int type = msg.what;
                if (type == 0) {
                    commentsAdapter = new CommentsAdapter(getActivity(), commentsList);
                    rvComments.setAdapter(commentsAdapter);
                } else {
                    commentsAdapter = new CommentsAdapter(getActivity(), commentsList);
                    rvComments.setAdapter(commentsAdapter);
                }


            }
        };
        return rootView;
    }


}
