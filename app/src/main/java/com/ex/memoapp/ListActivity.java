package com.ex.memoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.ex.memoapp.adapter.MemolistAdapter;
import com.ex.memoapp.vo.MemoVO;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    private Button btn_add;
    private RecyclerView recyclerView_memolist;
    private MemolistAdapter memolist_Adapter;
    private RecyclerView.LayoutManager memolist_layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        recyclerView_memolist = (RecyclerView) findViewById(R.id.recylerview_memolist);
        btn_add = (Button) findViewById(R.id.btn_add);

        memolist_layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView_memolist.setLayoutManager(memolist_layoutManager);

        //TODO: DAO로 메모리스트 가져오기
        ArrayList<MemoVO> memoDataset = new ArrayList<>();
        for(int i=0;i<20;i++) {
            memoDataset.add(new MemoVO(i+1,"제목"+i,"내용"+i,"2019-05-24"));
        }
        // ApplicationContext != Activity Context임! activity context는 this로 사용하면됨ㅇㅇ
        memolist_Adapter = new MemolistAdapter(memoDataset, this);
        recyclerView_memolist.setAdapter(memolist_Adapter);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ContentActivity.class);
                intent.putExtra("memoitem",new MemoVO());
                startActivityForResult(intent, CallbackCodes.REQUESTCODE_ADD_MEMO);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        memolist_Adapter.onActivityResult(requestCode, resultCode, data);
    }
}
