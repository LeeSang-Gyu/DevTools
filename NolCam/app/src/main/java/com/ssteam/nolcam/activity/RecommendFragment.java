package com.ssteam.nolcam.activity;

import static android.view.View.GONE;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.ssteam.nolcam.Interface.ParsingMsgEvent;
import com.ssteam.nolcam.R;
import com.ssteam.nolcam.adapter.CampingSlideAdpater;
import com.ssteam.nolcam.adapter.GroupSelector;
import com.ssteam.nolcam.databinding.FragmentRecommendBinding;
import com.ssteam.nolcam.generic.Camping;
import com.ssteam.nolcam.generic.ResultMsg;
import com.ssteam.nolcam.util.ConnectPHP;
import com.ssteam.nolcam.viewmodel.RecommendViewModel;

import java.util.ArrayList;

public class RecommendFragment extends Fragment implements ParsingMsgEvent {
    FragmentRecommendBinding binding;
    RecommendViewModel viewModel;
    AdLoader adLoader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recommend, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding = DataBindingUtil.bind(view);
        binding.setLifecycleOwner(requireActivity());
        viewModel = new ViewModelProvider(requireActivity()).get(RecommendViewModel.class);
        binding.setViewModel(viewModel);

        GroupSelector selector = new GroupSelector(getContext(), R.drawable.border_filter_choice, R.drawable.border_filter_non_choice);
        selector.addView(binding.weeklyCampingBtn);
        selector.addView(binding.monthlyCampingBtn);
        selector.addView(binding.yearlyCampingBtn);

        binding.weeklyCampingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selector.selectView(view.getId());
                viewModel.rankingChoice(1);
            }
        });
        binding.monthlyCampingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selector.selectView(view.getId());
                viewModel.rankingChoice(2);
            }
        });
        binding.yearlyCampingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selector.selectView(view.getId());
                viewModel.rankingChoice(3);
            }
        });

        binding.hotCampingRefreshBtn.setOnClickListener(v -> {
            rankingCampingParsing(viewModel.ranking.getValue());
        });

        viewModel.ranking.observe(requireActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                rankingCampingParsing(integer);
            }
        });

        rankingCampingParsing(1);

        /*
        네이티브 광고 테스트용 ID : ca-app-pub-3940256099942544/2247696110
        */

         adLoader = new AdLoader.Builder(requireActivity(), "ca-app-pub-3940256099942544/2247696110")
                .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                    @Override
                    public void onNativeAdLoaded(NativeAd nativeAd) {
                        if (adLoader.isLoading()) {
                            Log.e("LODING NOT FINISHED", "LODING NOT FINISHED");
                            // The AdLoader is still loading ads.
                            // Expect more adLoaded or onAdFailedToLoad callbacks.
                        } else {
                            Log.e("test", "SUCCESS");
                            // The AdLoader has finished loading ads.
                            binding.animationView.setVisibility(View.GONE);
                            binding.animationView.cancelAnimation();
                            Log.e("확인", String.valueOf(nativeAd));
                            binding.tpAdmob.setVisibility(View.VISIBLE);
                            binding.tpAdmob.setNativeAd(nativeAd);
                        }

                        if (getActivity().isDestroyed()) {
                            nativeAd.destroy();
                            return;
                        }
                    }
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(LoadAdError adError) {
                        // Handle the failure by logging, altering the UI, and so on.
                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder()
                        // Methods in the NativeAdOptions.Builder class can be
                        // used here to specify individual options settings.
                        .build())
                .build();

        adLoader.loadAd(new AdRequest.Builder().build());
    }

    public void rankingCampingParsing(int ranking) {
        ConnectPHP connectPHP = new ConnectPHP();
        connectPHP.setEvent((ParsingMsgEvent) RecommendFragment.this, new ResultMsg());
        connectPHP.setRanking(ranking);
        connectPHP.createCall(4);
    }


    @Override
    public void onParSingMsg(ResultMsg resultMsg) {
        ArrayList<Camping> campings = (ArrayList<Camping>) resultMsg.getArrayList();

        if (campings.size() != 0) {
            binding.hotCampingContainer.setVisibility(View.VISIBLE);
            binding.hotCampingErrorBox.setVisibility(View.GONE);

            CampingSlideAdpater slideAdpater = new CampingSlideAdpater(requireActivity(), campings);
            binding.hotCampingContainer.setClipToPadding(false);
            binding.hotCampingContainer.setPadding(80, 0, 80, 0);
            binding.hotCampingContainer.setPageMargin(30);
            binding.hotCampingContainer.setAdapter(slideAdpater);
        } else {
            binding.hotCampingContainer.setVisibility(View.GONE);
            binding.hotCampingErrorBox.setVisibility(View.VISIBLE);
            binding.errorRefreshBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rankingCampingParsing(viewModel.ranking.getValue());
                }
            });
        }
    }
}