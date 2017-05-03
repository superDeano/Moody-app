package ultramirinc.champs_mood.fragments;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ultramirinc.champs_mood.FriendProfilActivity;
import ultramirinc.champs_mood.R;
import ultramirinc.champs_mood.models.Notification;

/**
 * Created by William on 2017-04-06.
 */

public class MyAdapterNotification extends RecyclerView.Adapter<MyViewHolderNotification> {

    private Context context;
    private List<Notification> list;

    public MyAdapterNotification(List<Notification> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolderNotification onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_notification,viewGroup,false);
        return new MyViewHolderNotification(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolderNotification myViewHolderNotification, int position) {
        Notification notification = list.get(position);

        myViewHolderNotification.getSentByView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("listener Debug", "Coucou");
                Intent intent = new Intent(context, FriendProfilActivity.class);

                intent.putExtra("userId", notification.getSenderId());

                context.startActivity(intent);
            }
        });

        myViewHolderNotification.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO implement poke / add
            }
        });

        myViewHolderNotification.bind(notification);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
