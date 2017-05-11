package ultramirinc.champs_mood.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ultramirinc.champs_mood.FriendProfilActivity;
import ultramirinc.champs_mood.R;
import ultramirinc.champs_mood.managers.NotificationManager;
import ultramirinc.champs_mood.managers.UserManager;
import ultramirinc.champs_mood.models.Break;
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
    private MyViewHolder view;

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
        view = myViewHolder;
        User myFriend = list.get(position);
        checkBreakStatus(myFriend);
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

    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }

    private void checkBreakStatus(User u) {

        ArrayList<Break> friendBreaks = new ArrayList<>();
        //Loading things from db
        DatabaseReference breaksReference = FirebaseDatabase.getInstance().getReference("breaks");
        Query breakQuery = breaksReference.orderByChild("userId").equalTo(u.getId());
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    friendBreaks.add(singleSnapshot.getValue(Break.class));
                }
                u.setBreaks(friendBreaks);

                java.util.GregorianCalendar current = new java.util.GregorianCalendar();
                int currentDay = current.get(java.util.Calendar.DAY_OF_WEEK);

                long closestBreakMin = 1440;
                Break closestBreak = null;
                long closestBreakDuration = 0;

                // find closest break for the day (if any)
                for (int i=0; i < friendBreaks.size(); i++) {
                    Break breakNode = friendBreaks.get(i);
                    if (currentDay == breakNode.getIntDay()+1) {
                        Date now = new Date();

                        Date breakStart = new Date(); //Todo NOOOOO pas de deprecated
                        breakStart.setHours(breakNode.getStart().getHour());
                        breakStart.setMinutes((breakNode.getStart().getMinute()));
                        breakStart.setSeconds(0);

                        long timeDiffInMinutes = getDateDiff(now, breakStart, TimeUnit.MINUTES);

                        Date breakEnd = new Date();
                        breakEnd.setHours(breakNode.getEnd().getHour());
                        breakEnd.setMinutes(breakNode.getEnd().getMinute());
                        breakEnd.setSeconds(0);

                        //check if currently in break.
                        long breakDuration = getDateDiff(breakStart, breakEnd, TimeUnit.MINUTES);
                        if (timeDiffInMinutes < 0 && Math.abs(timeDiffInMinutes) <= breakDuration) {
                            //is currently in break! // Loop stops here.
                            closestBreak = breakNode;
                            closestBreakMin = timeDiffInMinutes;
                            closestBreakDuration = breakDuration;
                            break;
                        } else if (timeDiffInMinutes < closestBreakMin) {
                            //this is to handle if there are multiple breaks in one day. The goal is to show when is the CLOSEST break.
                            closestBreak = breakNode;
                            closestBreakMin = timeDiffInMinutes;
                            closestBreakDuration = breakDuration;
                        }
                        else if (timeDiffInMinutes > 0 && closestBreakMin < 0) {
                            //There is still a break in the current day, while the closest break is already passed, so the coming break has priority. (get it?)
                            closestBreak = breakNode;
                            closestBreakMin = timeDiffInMinutes;
                            closestBreakDuration = breakDuration;
                        }
                    }
                }
                if (u.getBreaks().isEmpty()){
                    view.setBreakTextText("No breaks at all");
                    return;
                }

                String text = "";
                if (closestBreak == null) {
                    view.setBreakTextText("No breaks today");
                }
                else {
                    if (closestBreakMin > 0) {
                        // Next break in timediffminutes
                        text = "Next break at : " + closestBreak.getFromTime();
                    }
                    else if (closestBreakMin < 0 && Math.abs(closestBreakMin) <= closestBreakDuration) {
                        text = "In break until : " + closestBreak.getToTime();
                    }
                    else {
                        // break is over.
                        text = "No more breaks today";
                    }


                    view.setBreakTextText(text);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        breakQuery.addListenerForSingleValueEvent(postListener);
    }

}
