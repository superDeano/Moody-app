package ultramirinc.champs_mood.fragments;

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

import ultramirinc.champs_mood.R;

/**
 * Created by Étienne Bérubé on 2017-03-23.
 */

public class FriendsFragment extends AppCompatActivity {

    private RecyclerView recyclerView;

    private List<MyObject> cities = new ArrayList<>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //remplir la ville
        ajouterVilles();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        //définit l'agencement des cellules, ici de façon verticale, comme une ListView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //puis créer un MyAdapter, lui fournir notre liste de villes.
        // cet adapter servira à remplir notre recyclerview
        recyclerView.setAdapter(new MyAdapter(cities));
    }


    private void ajouterVilles() {
        cities.add(new MyObject("Owen Brosseau", "Hungry"));
        cities.add(new MyObject("Gabriel Cote", "Relaxin"));
        cities.add(new MyObject("Francois Kekesi", "Lit"));
        cities.add(new MyObject("Dany", "Programming"));
        cities.add(new MyObject("Ming", "Studying"));
        cities.add(new MyObject("Alex", "Working"));
    }

}

