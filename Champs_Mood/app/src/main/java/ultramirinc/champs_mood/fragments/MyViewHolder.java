package ultramirinc.champs_mood.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ultramirinc.champs_mood.R;

/**
 * Created by William on 2017-04-04.
 */

public class MyViewHolder extends RecyclerView.ViewHolder{

    private TextView nameView;
    private TextView moodView;

    //itemView est la vue correspondante à 1 cellule
    public MyViewHolder(View itemView) {
        super(itemView);

        //c'est ici que l'on fait nos findView

        nameView = (TextView) itemView.findViewById(R.id.name);
        moodView = (TextView) itemView.findViewById(R.id.mood);
    }

    //puis ajouter une fonction pour remplir la cellule en fonction d'un MyObject
    public void bind(MyObject myObject){
        nameView.setText(myObject.getName());
        moodView.setText(myObject.getMood());

    }

}
