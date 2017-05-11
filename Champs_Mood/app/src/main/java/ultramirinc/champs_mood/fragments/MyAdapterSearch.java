package ultramirinc.champs_mood.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class MyAdapterSearch extends RecyclerView.Adapter<MyViewHolderSearch> {
    private Context context;
    private List<User> list;
    private boolean isPokable = true;

    public MyAdapterSearch(List<User> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolderSearch onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_search,viewGroup,false);
        return new MyViewHolderSearch(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolderSearch myViewHolderSearch, int position) {
        User person = list.get(position);
        Button b = (Button) myViewHolderSearch.getButton();//TODO debug this

        if (UserManager.getInstance().getCurrentUser().isFriend(person)){
            b.setText("poke!");
        }else{
            b.setText("Follow");
        }

        //load profile
        myViewHolderSearch.getNameView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if((UserManager.getInstance().getCurrentUser().getName()).equals(myViewHolderSearch.getNameView().getText())){
                    Toast.makeText(context, "Hello?", Toast.LENGTH_SHORT).show();
                }
                else{*/
                Intent intent = new Intent(context, FriendProfilActivity.class);
                intent.putExtra("userId", person.getId());
                context.startActivity(intent);
            }
        });
        //handle action link (add or poke)
        myViewHolderSearch.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //If is already our friend, will poke !
                if (person.isFriend(UserManager.getInstance().getCurrentUser())
                        && UserManager.getInstance().getCurrentUser().isFriend(person) && isPokable){
                    Poke(person);
                }
                else if(person.isFriend(UserManager.getInstance().getCurrentUser())
                        && UserManager.getInstance().getCurrentUser().isFriend(person) && !isPokable){
                    Toast.makeText(context, "Already poked!", Toast.LENGTH_SHORT).show();
                }
                else if(!(person.isFriend(UserManager.getInstance().getCurrentUser()))
                        && UserManager.getInstance().getCurrentUser().isFriend(person)){
                    Toast.makeText(context, "Can't poke because this user isn't following you back", Toast.LENGTH_SHORT).show();
                }
                else if (person.getId().equals(UserManager.getInstance().getCurrentUser().getId())) {
                    Toast.makeText(context, "You can't follow yourself!", Toast.LENGTH_SHORT).show();
                }
                else {
                    // not in our friends so we will add user.
                    Boolean added = UserManager.getInstance().getCurrentUser().addToFriendList(person);
                    if (added) {
                        // will update our friend list.
                        UserManager.getInstance().editUserInformations(UserManager.getInstance().getCurrentUser());

                        Toast.makeText(context, "Friend request sent!", Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();
                        Notification n = new Notification(UserManager.getInstance().getCurrentUser().getName(),
                                Notification_type.followed_you.getNumVal(),
                                person.isFriend(UserManager.getInstance().getCurrentUser()),
                                UserManager.getInstance().getCurrentUser().getId(),
                                person.getId());

                        NotificationManager nm = new NotificationManager();
                        nm.saveNotification(n);
                    }
                }

            }
        });
        myViewHolderSearch.bind(person);
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
