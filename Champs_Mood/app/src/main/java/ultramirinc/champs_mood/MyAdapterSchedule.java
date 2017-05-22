package ultramirinc.champs_mood;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.List;
import ultramirinc.champs_mood.models.Break;

/**
 * Created by William on 2017-04-13.
 * This class makes the transition between the raw data and a visual interpretation for the user's breaks
 */

public class MyAdapterSchedule extends RecyclerView.Adapter<MyViewHolderSchedule>{
    /**Contains the list of the schedule's breaks*/
    List<Break> list;

    public MyAdapterSchedule(List<Break> list) {
        this.list = list;
    }

    public MyAdapterSchedule(){

    }
    /**Creates a Visual interpretation using a custom ViewHolder.*/
    @Override
    public MyViewHolderSchedule onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_schedule,viewGroup,false);


        return new MyViewHolderSchedule(view);
    }
    /**Binds the information from a given break with the ViewHolder.*/
    @Override
    public void onBindViewHolder(MyViewHolderSchedule myViewHolderSchedule, int position) {


        Break myBreak = list.get(position);

        myViewHolderSchedule.getDelete().setOnClickListener(e->{

            Animation animation = AnimationUtils.loadAnimation(
                    myViewHolderSchedule.getDayView().getContext(), R.anim.delete_anim);
            animation.setDuration(800);

            myViewHolderSchedule.itemView.startAnimation(animation);
            Log.d("schedule debug", "deleted break");

            myViewHolderSchedule.itemView.postOnAnimationDelayed(new Runnable() {
                @Override
                public void run() {
                    notifyItemRemoved(list.indexOf(myBreak));
                    list.remove(myBreak);
                    deleteBreakFromDb(myBreak);
                }
            },300);

        });

        myViewHolderSchedule.bind(myBreak);
    }
    /**Deletes a break.*/
    private void deleteBreakFromDb(Break breakToDelete) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("breaks");
        ref.child(breakToDelete.getId()).removeValue();
    }
    /**Returns the size of the list.*/
    @Override
    public int getItemCount() {
        return list.size();
    }

}
