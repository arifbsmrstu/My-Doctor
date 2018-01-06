package com.themebd.mydoctorfinal;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by arif on 06-Nov-17.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Message> mMessageList;
    private FirebaseAuth mAuth;
    private Context context;

    public MessageAdapter(Context context,List<Message> mMessageList) {
        this.context = context;
        this.mMessageList = mMessageList;
    }


    @Override
    public MessageAdapter.MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_single_layout,parent,false);
        return new MessageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        mAuth = FirebaseAuth.getInstance();
        String current_user = mAuth.getCurrentUser().getUid();

        Message msg = mMessageList.get(position);

        String from_user = msg.getFrom();
        if(from_user.equals(current_user)){
            holder.mTextMessage.setBackgroundColor(Color.WHITE);
            holder.mTextMessage.setTextColor(Color.BLACK);
        }
        else {
            holder.mTextMessage.setBackgroundResource(R.drawable.message_text_background);
            holder.mTextMessage.setTextColor(Color.WHITE);
        }

        String msg_type = msg.getType();

        if(msg_type.equals("text")){
            holder.mTextMessage.setText(msg.getMsg());
            holder.mSendImage.setVisibility(View.INVISIBLE);

        }
        else{
            holder.mTextMessage.setVisibility(View.INVISIBLE);
            Picasso.with(context).load(msg.getMsg()).into(holder.mSendImage);
        }
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextMessage;
        public CircleImageView mProfileImage;
        public ImageView mSendImage;

        public MessageViewHolder(View itemView) {
            super(itemView);

            mTextMessage = (TextView) itemView.findViewById(R.id.txt_msg);
            mProfileImage = (CircleImageView) itemView.findViewById(R.id.message_sender_profile_image);
            mSendImage = (ImageView) itemView.findViewById(R.id.msg_image);
        }
    }


}
