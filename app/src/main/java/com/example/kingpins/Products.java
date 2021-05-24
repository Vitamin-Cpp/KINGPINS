package com.example.kingpins;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Products#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Products extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Products() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Products.
     */
    // TODO: Rename and change types and number of parameters
    public static Products newInstance(String param1, String param2) {
        Products fragment = new Products();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    Button Books;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    private final View.OnClickListener mListener = new View.OnClickListener() {
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.bttnStaitionery:
                    // do stuff
                    startActivity(new Intent(getActivity(), stationery.class));
                    break;
                case R.id.bttnPeripherals:
                    // do stuff
                    startActivity(new Intent(getActivity(), peripherals.class));
                    break;
                case R.id.bttnGadgets:
                    // do stuff
                    startActivity(new Intent(getActivity(), gadgets.class));
                    break;
                case R.id.bttnOffice:
                    // do stuff
                    startActivity(new Intent(getActivity(), office.class));
                    break;
                case R.id.bttnFurniture:
                    // do stuff
                    startActivity(new Intent(getActivity(), furniture.class));
                    break;
                case R.id.bttnBooks:
                    // do stuff
                    startActivity(new Intent(getActivity(), books.class));
                    break;
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_products, container, false);
        view.findViewById(R.id.bttnBooks).setOnClickListener(mListener);
        view.findViewById(R.id.bttnFurniture).setOnClickListener(mListener);
        view.findViewById(R.id.bttnOffice).setOnClickListener(mListener);
        view.findViewById(R.id.bttnGadgets).setOnClickListener(mListener);
        view.findViewById(R.id.bttnPeripherals).setOnClickListener(mListener);
        view.findViewById(R.id.bttnStaitionery).setOnClickListener(mListener);
        return view;
    }
}