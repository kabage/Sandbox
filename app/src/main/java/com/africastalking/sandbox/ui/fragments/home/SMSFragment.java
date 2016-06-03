package com.africastalking.sandbox.ui.fragments.home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.africastalking.sandbox.Backend.CreateSocketConnection;
import com.africastalking.sandbox.adapter.CommentsAdapter;
import com.africastalking.swipe.R;
import com.africastalking.sandbox.model.MessageModel;

import com.africastalking.sandbox.ui.widgets.SendCommentButton;

import java.util.ArrayList;

/**
 * Created by Lawrence on 10/29/15.
 */
public class SMSFragment extends Fragment implements SendCommentButton.OnSendClickListener {
    public static final String ARG_FEED_ID = "feed_id";
    Handler handle;
    private EditText etComment;
    private SendCommentButton btnSendComment;
    private CommentsAdapter commentsAdapter;
    private RecyclerView rvComments;
    private LinearLayout commentsRootLayout;
    private Toolbar toolbar;
    private TextView emptyText;
    private ArrayList<MessageModel> commentsList = new ArrayList<>();
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        howToPayDialog(getActivity());
        View rootView = inflater.inflate(R.layout.activity_comments,
                container, false);

        commentsRootLayout = (LinearLayout) getActivity().findViewById(R.id.commentsRootLayout);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvComments = (RecyclerView) rootView.findViewById(R.id.rvComments);
        emptyText = (TextView) rootView.findViewById(R.id.empty_view);
        rvComments.setLayoutManager(linearLayoutManager);
        rvComments.setHasFixedSize(true);
        commentsAdapter = new CommentsAdapter(getActivity(), commentsList);
        rvComments.setAdapter(commentsAdapter);

        rvComments.setOverScrollMode(View.OVER_SCROLL_NEVER);
        rvComments.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
//                    commentsAdapter.setAnimationsLocked(true);
                }
            }
        });

        etComment = (EditText) rootView.findViewById(R.id.edComment);
        btnSendComment = (SendCommentButton) rootView.findViewById(R.id.btnSendComment);
        btnSendComment.setOnSendClickListener(this);
        btnSendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


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


    @Override
    public void onSendClickListener(View v) {

        showProgressBar();
        CreateSocketConnection connection = new CreateSocketConnection(getActivity(), CreateSocketConnection.sendMessageRequest("254704415609", etComment.getText().toString()));
        connection.execute();
        commentsList.add(new MessageModel("", etComment.getText().toString()));
        commentsAdapter.notifyDataSetChanged();

        etComment.setText(null);
        emptyText.setVisibility(View.INVISIBLE);
        btnSendComment.setCurrentState(SendCommentButton.STATE_DONE);
        new Thread() {
            public void run() {
                try {
                    sleep(3000);

                    hideProgressBar();


                } catch (Exception e) {
                    Log.e("threadmessage", e.getMessage());
                }
            }
        }.start();


    }

    public void showProgressBar() {
        progressDialog = ProgressDialog.show(getActivity(), "", "Sending message");


    }

    public void hideProgressBar() {
        progressDialog.dismiss();
    }

}
