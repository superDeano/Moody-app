package ultramirinc.champs_mood.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
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
 * Created by William on 2017-04-04.
 */

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private List<User> list;
    private Context context;
    private boolean isPokable = true;

    public MyAdapter(List<User> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_friend,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
        User myFriend = list.get(position);
        myViewHolder.getNameView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FriendProfilActivity.class);
                intent.putExtra("userId", myFriend.getId());
                context.startActivity(intent);
            }
        });

        myViewHolder.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (myFriend.isFriend(UserManager.getInstance().getCurrentUser()) && isPokable) {
                   Poke(myFriend);
               }
               else if(myFriend.isFriend(UserManager.getInstance().getCurrentUser()) && !isPokable){
                   Toast.makeText(context, "Already poked!", Toast.LENGTH_SHORT).show();
               }
               else if (myFriend.getId().equals(UserManager.getInstance().getCurrentUser().getId())) {
                   Toast.makeText(context, "You can't poke yourself!", Toast.LENGTH_SHORT).show();
               }
               else {
                   Toast.makeText(context, "Can't poke because this user isn't following you back", Toast.LENGTH_SHORT).show();
               }
            }
        });
        myViewHolder.bind(myFriend);
    }

    private void Poke(User user) {
        User currentUser = UserManager.getInstance().getCurrentUser();
        Notification n = new Notification(currentUser.getName(), Notification_type.poked_you.getNumVal(), user.isFriend(currentUser), currentUser.getId(), user.getId());
        NotificationManager nm = new NotificationManager();
        nm.saveNotification(n);
        Toast.makeText(context, "Poke sent!", Toast.LENGTH_SHORT).show();
        isPokable =false;
        Handler checker = new Handler();
        checker.postDelayed(new Runnable() {
            @Override
            public void run() {
                isPokable=true;
            }
        }, 120000);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
