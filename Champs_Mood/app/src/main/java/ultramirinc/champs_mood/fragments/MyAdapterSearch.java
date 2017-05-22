package ultramirinc.champs_mood.fragments;
//Done
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;
import ultramirinc.champs_mood.FriendProfileActivity;
import ultramirinc.champs_mood.R;
import ultramirinc.champs_mood.managers.NotificationManager;
import ultramirinc.champs_mood.managers.UserManager;
import ultramirinc.champs_mood.models.Break;
import ultramirinc.champs_mood.models.Notification;
import ultramirinc.champs_mood.models.Notification_type;
import ultramirinc.champs_mood.models.User;

/**
 * Created by William on 2017-04-06.
 * This class creates a binding between the raw data and the visual interpretation for the results .
 * This class binds it with a MyViewHolderNotification object.
 */

public class MyAdapterSearch extends RecyclerView.Adapter<MyViewHolderSearch>{
    /**The context to the activity*/
    private Context context;
    /**Contains the list of the user's search*/
    private List<User> list;
    /**Determines if the friend can be poked or not*/
    private boolean isPokable = true;

    public MyAdapterSearch(List<User> list, Context context) {
        this.list = list;
        this.context = context;
    }
    /**Creates a Visual interpretation using a custom ViewHolder.*/
    @Override
    public MyViewHolderSearch onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_search,viewGroup,false);
        return new MyViewHolderSearch(view);
    }
    /**Binds the information from a given search with the ViewHolder.*/
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
                Intent intent = new Intent(context, FriendProfileActivity.class);
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
                    poke(person);
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
        checkBreakStatus(myViewHolderSearch, person);
    }
    /**Pokes a given user.*/
    private void poke(User user) {
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
    /**Returns the size of the list.*/
    @Override
    public int getItemCount() {
        return list.size();
    }
    /**Gets the user's break status (time before a break, in break or no more breaks).*/
    private void checkBreakStatus(MyViewHolderSearch viewHolderSearch, User u) {

        ArrayList<Break> friendBreaks = new ArrayList<>();
        //Loading things from database

        DatabaseReference breaksReference = FirebaseDatabase.getInstance().getReference("breaks");
        Query breakQuery = breaksReference.orderByChild("userId").equalTo(u.getId());

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("debug bt ", "inside checkBreakListener for: "+u.getName() );
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    friendBreaks.add(singleSnapshot.getValue(Break.class));
                }
                viewHolderSearch.setBreakText(breakCalculator(u, friendBreaks));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Not Handled
            }
        };
        breakQuery.addListenerForSingleValueEvent(postListener);
    }

    public String breakCalculator(User u, ArrayList<Break> friendBreaks){
        u.setBreaks(friendBreaks);

        java.util.GregorianCalendar current = new java.util.GregorianCalendar();
        int currentDay = adaptDayOfWeek(current.get(java.util.Calendar.DAY_OF_WEEK));

        long closestBreakMin = 1440; //Why? bcz: Initialise variable at 24 hrs (because 23:59 is the max value possible between now and the closest break in the same day) ps: this var keeps track of the closest break of the current day.
        Break closestBreak = null;
        long closestBreakDuration = 0;

        // find closest break for the day (if any)
        for (int i=0; i < friendBreaks.size(); i++) {
            Break breakNode = friendBreaks.get(i);

            if (currentDay == breakNode.getIntDay()) {

                GregorianCalendar now = new GregorianCalendar();

                GregorianCalendar breakStart = new GregorianCalendar();
                breakStart.set(Calendar.HOUR_OF_DAY,breakNode.getStart().getHour());
                breakStart.set(Calendar.MINUTE,(breakNode.getStart().getMinute()));
                breakStart.set(Calendar.SECOND,0);

                long timeDiffInMinutes = getDateDiff(now, breakStart, TimeUnit.MINUTES);

                GregorianCalendar breakEnd = new GregorianCalendar();
                breakEnd.set(Calendar.HOUR_OF_DAY,breakNode.getEnd().getHour());
                breakEnd.set(Calendar.MINUTE,breakNode.getEnd().getMinute());
                breakEnd.set(Calendar.SECOND,0);

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

        String text = "";

        if (friendBreaks.isEmpty()){
            text = "No breaks at all";

        } else if (closestBreak == null) {
            text = "No breaks today";

        } else {
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

        }
        Log.d("Debug bt: ", ""+text);

        return text;
    }
    /**Returns the difference between two dates in milliseconds.*/
    public static long getDateDiff(GregorianCalendar date1, GregorianCalendar date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime().getTime() - date1.getTime().getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }

    /**There's a mismatch between our original int values of weekdays with the java.utils.calendar integer values of weekdays.*/
    private int adaptDayOfWeek(int weekday) {
        int newValue = 0;
        switch(weekday) {
            case 1:
                newValue = 7;
                break;
            case 2:
                newValue = 1;
                break;
            case 3:
                newValue = 2;
                break;
            case 4:
                newValue = 3;
                break;
            case 5:
                newValue = 4;
                break;
            case 6:
                newValue = 5;
                break;
            case 7:
                newValue = 6;
                break;
        }
        return newValue;
    }
}
