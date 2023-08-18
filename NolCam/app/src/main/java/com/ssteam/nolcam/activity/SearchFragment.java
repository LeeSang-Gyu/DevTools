package com.ssteam.nolcam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.ssteam.nolcam.Interface.DMLMsgEvent;
import com.ssteam.nolcam.Interface.DialogMsgEvent;
import com.ssteam.nolcam.R;
import com.ssteam.nolcam.adapter.RecentlyKeywordAdapter;
import com.ssteam.nolcam.databinding.FragmentSearchBinding;
import com.ssteam.nolcam.db.RecentlyKeywordDBHelper;
import com.ssteam.nolcam.dialog.SearchAreaDialog;
import com.ssteam.nolcam.dialog.CategoryChoiceDialog;
import com.ssteam.nolcam.generic.Keyword;
import com.ssteam.nolcam.viewmodel.SearchViewModel;

import java.util.ArrayList;

import static android.view.View.GONE;

public class SearchFragment extends Fragment implements DialogMsgEvent, DMLMsgEvent {
    FragmentSearchBinding binding;
    MainActivity mainActivity;

    RecentlyKeywordDBHelper dbHelper;

    int areaPos = -1;
    int sigunguPos = -1;
    int categoryPos = -1;

    String area;
    String sigungu;
    String category;

    public SearchFragment(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding = DataBindingUtil.bind(view);
        binding.setLifecycleOwner(requireActivity());
        ViewModel viewModel = new ViewModelProvider(requireActivity()).get(SearchViewModel.class);
        binding.setViewModel((SearchViewModel) viewModel);

        dbHelper = new RecentlyKeywordDBHelper(requireActivity());

        setRecentlyKeywordListView();

        binding.recentlyKeywordDeleteAll.setOnClickListener(v -> {
            dbHelper.deleteAll();
            setRecentlyKeywordListView();
        });

        binding.areaInputBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchAreaDialog areaChoiceDialog = new SearchAreaDialog(requireActivity(), (DialogMsgEvent) SearchFragment.this);
                if (areaPos != -1) {
                    areaChoiceDialog.setAreaPos(areaPos);
                    areaChoiceDialog.setSigunguPos(sigunguPos);
                }
                areaChoiceDialog.show();
            }
        });
        binding.categoryInputBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CategoryChoiceDialog categoryChoiceDialog = new CategoryChoiceDialog(requireActivity(), (DialogMsgEvent) SearchFragment.this);
                if (categoryPos != -1) {
                    categoryChoiceDialog.setCategoryPos(categoryPos);
                }
                categoryChoiceDialog.show();
            }
        });

        binding.searchBtn1.setOnClickListener(searchListener);
        binding.searchBtn2.setOnClickListener(searchListener);

        //애드몹
        binding.adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                binding.animationView.cancelAnimation();
                binding.animationView.setVisibility(GONE);
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adView.loadAd(adRequest);

        // TODO: Add adView to your view hierarchy.
        MobileAds.initialize(requireActivity(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
    }

    public View.OnClickListener searchListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String keyword = binding.keywordInputText.getText().toString();
            if (keyword.length() != 0) {
                dbHelper.insert(keyword);
            }

            setRecentlyKeywordListView();

            Intent intent = new Intent(requireActivity(), SearchResultActivity.class);
            if (keyword.length() < 2) {
                Toast.makeText(getContext(), "검색어를 두 글자 이상 입력해주세요.", Toast.LENGTH_SHORT).show();
            } else {
                intent.putExtra("Keyword", keyword);
            }

            if (areaPos == -1) {
                intent.putExtra("Area", "전체");
            } else {
                intent.putExtra("Area", area);
            }

            if (sigunguPos == -1) {
                intent.putExtra("Sigungu", "전체");
            } else {
                intent.putExtra("Sigungu", sigungu);
            }

            if (categoryPos == -1) {
                intent.putExtra("Category", "전체");
            } else {
                intent.putExtra("Category", category);
            }

            double[] mapXY = mainActivity.getMapXY();

            intent.putExtra("MapX", mapXY[0]);
            intent.putExtra("MapY", mapXY[1]);

            startActivity(intent);
        }
    };

    public void setRecentlyKeywordListView() {
        ArrayList<Keyword> keywords = dbHelper.select();
        if (keywords.size() == 0) {
            binding.recentlyKeywordSizeZeroText.setVisibility(View.VISIBLE);
            binding.recentlyKeywordList.setVisibility(GONE);
        } else {
            RecentlyKeywordAdapter adapter = new RecentlyKeywordAdapter(requireActivity(), (DMLMsgEvent) SearchFragment.this, keywords);
            binding.recentlyKeywordList.setAdapter(adapter);
            binding.recentlyKeywordList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    binding.keywordInputText.setText(keywords.get(i).getKeyword());
                }
            });

            binding.recentlyKeywordSizeZeroText.setVisibility(GONE);
            binding.recentlyKeywordList.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDelete(int id) {
        dbHelper.delete(id);

        setRecentlyKeywordListView();
    }

    @Override
    public void onResult(String value, ArrayList values) {
        ArrayList<String> choiceList = values;

        if (value.equals("Area")) {
            areaPos = Integer.valueOf(choiceList.get(0));
            sigunguPos = Integer.valueOf(choiceList.get(1));

            area = choiceList.get(2);
            sigungu = choiceList.get(3);

            String areaText = choiceList.get(2);
            if (!choiceList.get(2).equals("세종시")) {
                areaText += " " + choiceList.get(3);
            }
            binding.areaInputBtn.setText(areaText);
        } else {
            categoryPos = Integer.valueOf(choiceList.get(0));
            category = choiceList.get(1);

            binding.categoryInputBtn.setText(choiceList.get(1));
        }
    }
}