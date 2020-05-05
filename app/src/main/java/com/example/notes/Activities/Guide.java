package com.example.notes.Activities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.notes.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Guide extends Fragment {

    public Guide() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guide, container, false);

        String[] titleArray = {getString(R.string.Deleting)};
        String[] detailArray = {getString(R.string.DeletingDescription)};
        List<Map<String, String>> guidePoints = new ArrayList<>();
        for(int i=0; i< titleArray.length; i++)
        {
            Map<String, String> listItem = new HashMap<>();
            listItem.put("titleKey", titleArray[i]);
            listItem.put("detailKey", detailArray[i]);
            guidePoints.add(listItem);
        }

        SimpleAdapter adapter = new SimpleAdapter(getActivity(), guidePoints, R.layout.guide_points,
                new String[] {"titleKey", "detailKey" },
                new int[] {R.id.text1, R.id.text2 });
        ListView listView = view.findViewById(R.id.guide_points);
        listView.setAdapter(adapter);
        return view;
    }

}
