package com.mohan.merutest.ui.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.mohan.merutest.Constant;
import com.mohan.merutest.R;
import com.mohan.merutest.adapters.RecipesListAdapter;
import com.mohan.merutest.entity.RecipesData;
import com.mohan.merutest.utils.SharedPreferenceUtil;

import java.util.List;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;
    private SearchView search_view;
    private RecipesListAdapter adapter;
    private List<RecipesData> recipesDataMain;
    private SharedPreferenceUtil sharedPreferenceUtil;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);

        sharedPreferenceUtil = new SharedPreferenceUtil();

        search_view = view.findViewById(R.id.search_view);
        final RecyclerView recyclerView = view.findViewById(R.id.recyclerview_recipies);
        adapter = new RecipesListAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        SwitchMaterial switchMaterial = view.findViewById(R.id.toggle_btn);
        switchMaterial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                } else {
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                }
            }
        });

        ItemTouchHelper.SimpleCallback ite = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT) {
                    recipesDataMain.remove(position);
                    adapter.notifyItemRemoved(position);
                } else if (direction == ItemTouchHelper.UP) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    //Yes button clicked
                                    adapter.notifyDataSetChanged();
                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(recipesDataMain.get(position).getPublisher_url()));
                                    startActivity(browserIntent);
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    recipesDataMain.remove(position);
                                    adapter.notifyItemRemoved(position);
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                    builder.setMessage("What is in your Mind...?").setPositiveButton("Visit", dialogClickListener)
                            .setNegativeButton("Remove", dialogClickListener).show();
                } else if (direction == ItemTouchHelper.RIGHT) {
                    adapter.notifyDataSetChanged();
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(recipesDataMain.get(position).getPublisher_url()));
                    startActivity(browserIntent);
                }
            }
        };
        new ItemTouchHelper(ite).attachToRecyclerView(recyclerView);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        // TODO: Use the ViewModel

        String q = sharedPreferenceUtil.getSharedPreferenceString(requireActivity(), "QUERY", "");
        if (q != null && !q.equalsIgnoreCase("")) {
            Constant.q = q;
            mViewModel.init();
            mViewModel.getListLiveData().observe(getViewLifecycleOwner(), new Observer<List<RecipesData>>() {
                @Override
                public void onChanged(List<RecipesData> recipesData) {
                    Log.e("SUCCESS", "SUCCESS");
                    adapter.setRecipesData(recipesData);
                    recipesDataMain = recipesData;
                }
            });
        }

        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                sharedPreferenceUtil.setSharedPreferenceString(requireActivity(), "QUERY", query);
                Constant.q = query;
                mViewModel.init();
                mViewModel.getListLiveData().observe(getViewLifecycleOwner(), new Observer<List<RecipesData>>() {
                    @Override
                    public void onChanged(List<RecipesData> recipesData) {
                        Log.e("SUCCESS", "SUCCESS");
                        adapter.setRecipesData(recipesData);
                        recipesDataMain = recipesData;
                    }
                });

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

}
