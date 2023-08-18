package com.ssteam.nolcam.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ssteam.nolcam.Interface.DialogMsgEvent;
import com.ssteam.nolcam.R;
import com.ssteam.nolcam.adapter.SearchAreaAdapter;
import com.ssteam.nolcam.databinding.DialogSearchAreaBinding;

import java.util.ArrayList;

public class SearchAreaDialog extends Dialog {
    DialogSearchAreaBinding binding;
    DialogMsgEvent dialogMsgEvent;

    String[] areas = {"서울시", "인천시", "대전시", "대구시", "광주시", "부산시", "울산시", "세종시", "경기도", "강원도", "충청북도", "충청남도", "경상북도", "경상남도", "전라북도", "전라남도", "제주도"};

    String sigungu1 = "강남구,강동구,강북구,강서구,관악구,광진구,구로구,금천구,노원구,도봉구,동대문구,동작구,마포구,서대문구,서초구,성동구,성북구,송파구,양천구,영등포구,용산구,은평구,종로구,중구,중랑구";
    String sigungu2 = "강화군,계양구,미추홀구,남동구,동구,부평구,서구,연수구,옹진군,중구";
    String sigungu3 = "대덕구,동구,서구,유성구,중구";
    String sigungu4 = "남구,달서구,달성군,동구,북구,서구,수성구,중구";
    String sigungu5 = "광산구,남구,동구,북구,서구";
    String sigungu6 = "강서구,금정구,기장군,남구,동구,동래구,부산진구,북구,사상구,사하구,서구,수영구,연제구,영도구,중구,해운대구";
    String sigungu7 = "중구,남구,동구,북구,울주군";
    String sigungu8 = "조치원,연기면,연동면,부강면,금남면,장군면,연서면,전의면,소정면";
    String sigungu9 = "가평군,고양시,과천시,광명시,광주시,구리시,군포시,김포시,남양주시,동두천시,부천시,성남시,수원시,시흥시,안산시,안성시,안양시,양주시,양평군,여주시,연천군,오산시,용인시,의왕시,의정부시,이천시,파주시,평택시,포천시,하남시,화성시";
    String sigungu10 = "강릉시,고성군,동해시,삼척시,속초시,양구군,양양군,영월군,원주시,인제군,정선군,철원군,춘천시,태백시,평창군,홍천군,화천군,횡성군";
    String sigungu11 = "괴산군,단양군,보은군,영동군,옥천군,음성군,제천시,진천군,청원시,청주시,충주시,증평군";
    String sigungu12 = "공주시,금산군,논산시,당진시,보령시,부여군,서산시,서천군,아산시,예산군,천안시,청양군,태안군,홍성군,계룡시";
    String sigungu13 = "경산시,경주시,고령군,구미시,군위군,김천시,문경시,봉화군,상주시,성주군,안동시,영덕군,영양군,영주시,영천시,예천군,울릉군,울진군,의성군,청도군,청송군,칠곡군,포항시";
    String sigungu14 = "거제시,거창군,고성군,김해시,남해군,마산시,밀양시,사천시,산청군,양산시,의령군,진주시,진해시,창녕군,창원시,통영시,하동군,함안군,함양군,합천군";
    String sigungu15 = "고창군,군산시,김제시,남원시,무주군,부안군,순창군,완주군,익산시,임실군,장수군,전주시,정읍시,진안군";
    String sigungu16 = "강진군,고흥군,곡성군,광양시,구례군,나주시,담양군,목포시,무안군,보성군,순천시,신안군,여수시,영광군,영암군,왕도군,장성군,장흥군,진도군,함평군,해남군,화순군";
    String sigungu17 = "남제주군,북제주군,서귀포시,제주시";
    String[] sigunguList = new String[]{
            sigungu1, sigungu2, sigungu3, sigungu4, sigungu5,
            sigungu6, sigungu7, sigungu8, sigungu9, sigungu10,
            sigungu11, sigungu12, sigungu13, sigungu14, sigungu15,
            sigungu16, sigungu17
    };
    String[] currentSigungu;

    int areaPos = -1;
    int sigunguPos = -1;

    public SearchAreaDialog(@NonNull Context context, DialogMsgEvent dialogMsgEvent) {
        super(context);
        this.dialogMsgEvent = dialogMsgEvent;
    }

    public void setAreaPos(int areaPos) {
        this.areaPos = areaPos;
    }

    public void setSigunguPos(int sigunguPos) {
        this.sigunguPos = sigunguPos;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_search_area, null, false);
        setContentView(binding.getRoot());

        setCancelable(true);
        setCanceledOnTouchOutside(true);

        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();

            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;

            params.windowAnimations = R.style.DialogAnimation;
            window.setAttributes(params);

            window.setGravity(Gravity.BOTTOM);
        }

        // Setting
        binding.choiceList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = 3;
                outRect.right = 3;
            }
        });

        if (areaPos == -1) {
            updateAreaListView();
        } else {
            updateSigunguListView(areaPos);

            binding.areaChoiceBtn.setText(areas[areaPos]);
            binding.sigunguChoiceBtn.setText(currentSigungu[sigunguPos]);
        }
        // Event
        binding.closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        binding.areaChoiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAreaListView();
            }
        });

        binding.sigunguChoiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSigunguListView(areaPos);
            }
        });

        binding.choiceFinishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (areaPos == -1 || sigunguPos == -1) {
                    Toast.makeText(getContext(), "지역을 선택 하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                dismiss();

                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.add(areaPos + "");
                arrayList.add(sigunguPos + "");
                arrayList.add(areas[areaPos]);
                arrayList.add(currentSigungu[sigunguPos]);
                dialogMsgEvent.onResult("Area", arrayList);
            }
        });
    }

    public String[] checkArray(String[] value) {
        String[] array = value;

        int count = value.length;
        if (count % 3 != 0) {
            for (int i = 0; i < 2; i++) {
                count++;
                if (count % 3 == 0) {
                    break;
                }
            }

            array = new String[count];
            for (int i = 0; i < count; i++) {
                if (value.length <= i) {
                    array[i] = "";
                    continue;
                }
                array[i] = value[i];
            }
        }

        return array;
    }

    // 지역선택 리스트뷰
    public void updateAreaListView() {
        SearchAreaAdapter areaChoiceAdapter = new SearchAreaAdapter(checkArray(areas));
        if (areaPos != -1) {
            areaChoiceAdapter.setChoicePos(areaPos);
        }

        areaChoiceAdapter.setOnItemClickListener(new SearchAreaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                areaPos = pos;
                sigunguPos = -1;

                binding.areaChoiceBtn.setText(areas[pos]);
                binding.sigunguChoiceBtn.setText("");
                updateSigunguListView(areaPos);
            }
        });

        binding.choiceList.setLayoutManager(new GridLayoutManager(getContext(), 3));
        binding.choiceList.setAdapter(areaChoiceAdapter);
    }

    // 시군구선택 리스트뷰
    public void updateSigunguListView(int pos) {
        if (pos == -1) {
            Toast.makeText(getContext(), "지역을 먼저 선택 하세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        String sigungu = "전체," + sigunguList[pos];
        currentSigungu = sigungu.split(",");

        SearchAreaAdapter areaChoiceAdapter = new SearchAreaAdapter(checkArray(currentSigungu));
        if (sigunguPos != -1) {
            areaChoiceAdapter.setChoicePos(sigunguPos);
        }

        areaChoiceAdapter.setOnItemClickListener(new SearchAreaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                sigunguPos = pos;

                binding.sigunguChoiceBtn.setText(currentSigungu[pos]);
            }
        });
        binding.choiceList.setLayoutManager(new GridLayoutManager(getContext(), 3));
        binding.choiceList.setAdapter(areaChoiceAdapter);
    }
}