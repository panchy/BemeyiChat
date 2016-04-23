package com.yido.bemeyichat.model;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yido.bemeyichat.App;
import com.yido.bemeyichat.R;

import uk.co.ribot.easyadapter.ItemViewHolder;
import uk.co.ribot.easyadapter.PositionInfo;
import uk.co.ribot.easyadapter.annotations.LayoutId;
import uk.co.ribot.easyadapter.annotations.ViewId;

/**
 * Created by Panch on 23.04.2016.
 */
@LayoutId(R.layout.item_chat)
public class itemChatModel extends ItemViewHolder<chat> {

    @ViewId(R.id.sent)
    LinearLayout sentLayout;

    @ViewId(R.id.received)
    LinearLayout receivedLayout;

    @ViewId(R.id.txtUserReceived)
    TextView usernameReceived;

    @ViewId(R.id.txtReceived)
    TextView textReceived;

    @ViewId(R.id.txtUserSent)
    TextView usernameSent;

    @ViewId(R.id.txtSent)
    TextView textSent;

    public itemChatModel(View view) {
        super(view);
    }


    @Override
    public void onSetValues(chat item, PositionInfo positionInfo) {

        if(item.getId() == App.userID)
        {
            sentLayout.setVisibility(View.VISIBLE);
            receivedLayout.setVisibility(View.GONE);

            textSent.setText(item.getText());
            usernameSent.setText(App.username);

        }
        else
        {
            sentLayout.setVisibility(View.GONE);
            receivedLayout.setVisibility(View.VISIBLE);

            textReceived.setText(item.getText());
            usernameReceived.setText(item.getUsername());


        }

    }
}
