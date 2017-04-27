package ultramirinc.champs_mood;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import java.util.List;

/**
 * Created by William on 2017-04-13.
 */

public class MyAdapterSchedule extends RecyclerView.Adapter<MyViewHolderSchedule>{

    List<Break> list;

    public MyAdapterSchedule(List<Break> list) {
        this.list = list;
    }

    @Override
    public MyViewHolderSchedule onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_schedule,viewGroup,false);


        return new MyViewHolderSchedule(view);
    }

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
                }
            },300);



        });

        myViewHolderSchedule.bind(myBreak);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
