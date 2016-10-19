package com.alex.udacityportifolio.main;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.alex.udacityportifolio.R;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements View.OnClickListener {

    private Button btMovie;
    private Button btBigger;
    private Button btCapstone;
    private Button btMaterial;
    private Button btUbiquitous;
    private Button btStock;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        btMovie = (Button) view.findViewById(R.id.buttonMovie);
        btStock = (Button) view.findViewById(R.id.buttonStock);
        btBigger = (Button) view.findViewById(R.id.buttonBigger);
        btMaterial = (Button) view.findViewById(R.id.buttonMaterial);
        btUbiquitous = (Button) view.findViewById(R.id.buttonUbiquitous);
        btCapstone = (Button) view.findViewById(R.id.buttonCapstone);

        btMovie.setOnClickListener(this);
        btStock.setOnClickListener(this);
        btBigger.setOnClickListener(this);
        btMaterial.setOnClickListener(this);
        btUbiquitous.setOnClickListener(this);
        btCapstone.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonMovie:
                Toast.makeText(getActivity(), getString(R.string.msg_open_movie_app), Toast.LENGTH_SHORT).show();
                break;
            case R.id.buttonStock:
                Toast.makeText(getActivity(), getString(R.string.msg_open_stock_app), Toast.LENGTH_SHORT).show();
                break;
            case R.id.buttonBigger:
                Toast.makeText(getActivity(), getString(R.string.msg_open_bigger_app), Toast.LENGTH_SHORT).show();
                break;
            case R.id.buttonMaterial:
                Toast.makeText(getActivity(), getString(R.string.msg_open_material_app), Toast.LENGTH_SHORT).show();
                break;
            case R.id.buttonUbiquitous:
                Toast.makeText(getActivity(), getString(R.string.msg_open_ubiquitous_app), Toast.LENGTH_SHORT).show();
                break;
            case R.id.buttonCapstone:
                Toast.makeText(getActivity(), getString(R.string.msg_open_capstone_app), Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
