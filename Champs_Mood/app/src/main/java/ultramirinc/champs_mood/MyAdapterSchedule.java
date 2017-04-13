package ultramirinc.champs_mood;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        myViewHolderSchedule.bind(myBreak);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
