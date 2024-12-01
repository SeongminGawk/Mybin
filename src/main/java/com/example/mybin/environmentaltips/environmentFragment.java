package com.example.mybin.environmentaltips;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.mybin.R;

import java.util.HashMap;
import java.util.Map;

//분리수거 프래그먼트
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link environmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class environmentFragment extends Fragment {

    EditText searchEditText;
    Button searchButton;
    TextView resultTextView;
    Map<String, String> disposalMethods;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public environmentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment environmentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static environmentFragment newInstance(String param1, String param2) {
        environmentFragment fragment = new environmentFragment();
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

        disposalMethods = new HashMap<>();
        disposalMethods.put("플라스틱 병", "플라스틱 병은 재활용이 가능합니다.");
        disposalMethods.put("유리 병", "유리 병은 재활용이 가능합니다.");
        disposalMethods.put("캔", "캔은 재활용이 가능합니다.");
        disposalMethods.put("종이", "종이는 재활용이 가능합니다.");
        disposalMethods.put("신문지", "신문지는 재활용이 가능합니다.");
        disposalMethods.put("종이컵", "종이컵은 깨끗이 씻어서 재활용 가능합니다.");
        disposalMethods.put("스티로폼", "스티로폼은 재활용이 가능합니다.");
        disposalMethods.put("비닐", "비닐은 재활용이 가능합니다.");
        disposalMethods.put("플라스틱 용기", "플라스틱 용기는 깨끗이 씻어서 재활용 가능합니다.");
        disposalMethods.put("전지", "전지는 지정된 수거함에 배출해야 합니다.");
        disposalMethods.put("형광등", "형광등은 지정된 수거함에 배출해야 합니다.");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_environment, container, false);

        // View 초기화
        searchEditText = view.findViewById(R.id.search_edit_text);
        searchButton = view.findViewById(R.id.search_button);
        resultTextView = view.findViewById(R.id.result_text_view);

        // 검색 버튼 클릭 설정
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String garbageType = searchEditText.getText().toString();
                String disposalMethod = disposalMethods.get(garbageType);
                if (disposalMethod != null) {
                    resultTextView.setText(disposalMethod);
                } else {
                    resultTextView.setText("해당 쓰레기의 분리수거 방법을 찾을 수 없습니다.");
                }
            }
        });

        return view;
    }
}