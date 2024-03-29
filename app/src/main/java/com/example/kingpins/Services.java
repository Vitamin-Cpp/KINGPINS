package com.example.kingpins;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Services#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Services extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Services() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Services.
     */
    // TODO: Rename and change types and number of parameters
    public static Services newInstance(String param1, String param2) {
        Services fragment = new Services();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

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
                case R.id.bttnTutor:
                    // do stuff
                    startActivity(new Intent(getActivity(), Tutor.class));
                    break;
                case R.id.bttnRepair:
                    // do stuff
                    startActivity(new Intent(getActivity(), Repairs.class));
                    break;
                case R.id.bttnMentorship:
                    // do stuff
                    startActivity(new Intent(getActivity(), Mentorship.class));
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_services, container, false);
        view.findViewById(R.id.bttnTutor).setOnClickListener(mListener);
        view.findViewById(R.id.bttnRepair).setOnClickListener(mListener);
        view.findViewById(R.id.bttnMentorship).setOnClickListener(mListener);
        return view;
    }
}