package ultramirinc.champs_mood.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ultramirinc.champs_mood.R;

/**
 * Created by William on 2017-04-06.
 */

public class MyAdapterNotification extends RecyclerView.Adapter<MyViewHolderNotification> {

    List<Notification> list;

    public MyAdapterNotification(List<Notification> list) {
        this.list = list;
    }

    @Override
    public MyViewHolderNotification onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_notification,viewGroup,false);
        return new MyViewHolderNotification(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolderNotification myViewHolderNotification, int position) {
        Notification notification = list.get(position);

        myViewHolderNotification.bind(notification);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
