package ultramirinc.champs_mood.fragments;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import ultramirinc.champs_mood.FriendProfilActivity;
import ultramirinc.champs_mood.R;
import ultramirinc.champs_mood.managers.NotificationManager;
import ultramirinc.champs_mood.managers.UserManager;
import ultramirinc.champs_mood.models.Notification;
import ultramirinc.champs_mood.models.Notification_type;
import ultramirinc.champs_mood.models.User;

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
                //If is already our friend, will poke !
                if (UserManager.getInstance().getCurrentUser().isFriend(notification.getSenderId())) {
                    Poke(notification.getSenderId());
                }
                else {
                    // not in our friends so we will add user.
                    Boolean added = UserManager.getInstance().getCurrentUser().addToFriendList(notification.getSenderId());
                    if (added) {
                        // will update our friend list.
                        UserManager.getInstance().editUserInformations(UserManager.getInstance().getCurrentUser());

                        Toast.makeText(context, "Now following user.", Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();
                        Notification n = new Notification(UserManager.getInstance().getCurrentUser().getName(),
                                Notification_type.followed_you.getNumVal(),
                                true,
                                UserManager.getInstance().getCurrentUser().getId(),
                                notification.getSenderId());

                        NotificationManager nm = new NotificationManager();
                        nm.saveNotification(n);
                    }
                }
            }
        });

        myViewHolderNotification.bind(notification);
    }

    private void Poke(String UserId) {
        User currentUser = UserManager.getInstance().getCurrentUser();
        Notification n = new Notification(currentUser.getName(), Notification_type.poked_you.getNumVal(), true, currentUser.getId(), UserId);
        NotificationManager nm = new NotificationManager();
        nm.saveNotification(n);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
