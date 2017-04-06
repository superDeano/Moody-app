package ultramirinc.champs_mood.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import ultramirinc.champs_mood.R;

import ultramirinc.champs_mood.R;

/**
 * Created by Étienne Bérubé on 2017-03-23.
 */

public class FriendsFragment extends Fragment {

    private RecyclerView recyclerView;

    private List<MyObject> cities = new ArrayList<>();

    private Context context = getActivity();



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_friends, container, false);

        //remplir la ville
        ajouterVilles();

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        //définit l'agencement des cellules, ici de façon verticale, comme une ListView
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        //pour adapter en grille comme une RecyclerView, avec 2 cellules par ligne
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));

        //puis créer un MyAdapter, lui fournir notre liste de villes.
        // cet adapter servira à remplir notre recyclerview
        recyclerView.setAdapter(new MyAdapter(cities));

        return view;
    }


    private void ajouterVilles() {
        cities.add(new MyObject("Owen Bross", "Hungry", "In Break"));
        cities.add(new MyObject("Gab Cote", "Lit", "Break in 15 minutes"));
        cities.add(new MyObject("Francois Kekesi", "Working", "In Break"));
        cities.add(new MyObject("Dany", "Programming", "Break in 1 hour"));
        cities.add(new MyObject("Alex", "Studying", "Break in 1.5 hour"));
        cities.add(new MyObject("Ming", "Chilling", "In Break"));
    }

}

