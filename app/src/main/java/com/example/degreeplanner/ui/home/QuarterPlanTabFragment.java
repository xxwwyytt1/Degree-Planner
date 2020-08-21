package com.example.degreeplanner.ui.home;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.degreeplanner.R;
import com.example.degreeplanner.classes.Course;
import com.example.degreeplanner.classes.Quarter;
import com.example.degreeplanner.ui.home.QuarterPlanAdapter;
import com.example.degreeplanner.ui.requirements.RequirementAdapter;
import com.example.degreeplanner.ui.requirements.VerticalSpaceItemDecorator;

public class QuarterPlanTabFragment extends Fragment {

    private SharedHomeViewModel mViewModel;
    private final String space = " ";

    public static QuarterPlanTabFragment newInstance() {
        return new QuarterPlanTabFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.quarter_plan_tab_fragment, container, false);
        mViewModel =
                new ViewModelProvider(requireActivity()).get(SharedHomeViewModel.class);
        SeekBar seekBar = root.findViewById(R.id.quarter_slider);
        this.setSeekBar(seekBar, getContext());

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /*
     * Event callback to when fragment activity is resumed
     */
    @Override
    public void onResume() {
        super.onResume();
    }

    /*
     * Set the slider to have the correct quarter labels
     */
    public void setSeekBar(SeekBar mySeekBar, Context context) {
        mySeekBar.setMax(mViewModel.getMySchedule().getQuarters().size() - 1);
        mySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Code to perform some action when progress is changed.
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Code to perform some action when touch is started.
            }

            /*
             * When user releases slider, corresponding courses are displayed on screen along
             * with the quarter name
             */
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                Quarter currQuarter = mViewModel.getMySchedule().getQuarters().get(mySeekBar.getProgress());
                Log.e("quarter plan frag", "currQuarter size: " + currQuarter.getCourses().size());
                Toast.makeText(context, currQuarter.getName(), Toast.LENGTH_SHORT).show();
                // Initialize the recycler view which holds the list of courses
                RecyclerView quarterView = (RecyclerView) getView().findViewById(R.id.quarter_plan_recycler);
                // set a GridLayoutManager with default vertical orientation and 3 number of columns
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),1);
                // Add space between rows
                quarterView.addItemDecoration(new VerticalSpaceItemDecorator(20));
                quarterView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
                // Adapter initialization
                QuarterPlanAdapter adapter = new QuarterPlanAdapter(getActivity(), currQuarter.getCourses());
                quarterView.setAdapter(adapter);
            }
        });
    }

}