package com.ex.memoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ex.memoapp.adapter.MemolistAdapter;
import com.ex.memoapp.db.MemoDAO;
import com.ex.memoapp.vo.MemoVO;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private Button btn_add;
    private RecyclerView recyclerView_memolist;
    private MemolistAdapter memolist_Adapter;
    private RecyclerView.LayoutManager memolist_layoutManager;

    private MemoDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        recyclerView_memolist = (RecyclerView) findViewById(R.id.recylerview_memolist);
        btn_add = (Button) findViewById(R.id.btn_add);

        memolist_layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView_memolist.setLayoutManager(memolist_layoutManager);

        dao = new MemoDAO(getApplicationContext());
        List<MemoVO> memoDataset = dao.getAll();

        // ApplicationContext != Activity Context임! activity context는 this로 사용하면됨ㅇㅇ
        memolist_Adapter = new MemolistAdapter(memoDataset, this);
        recyclerView_memolist.setAdapter(memolist_Adapter);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ContentActivity.class);
                intent.putExtra("requestCode", CallbackCodes.REQUESTCODE_ADD_MEMO);
                intent.putExtra("memoitem",new MemoVO());
                startActivityForResult(intent, CallbackCodes.REQUESTCODE_ADD_MEMO);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String toastMsg = "";
        if(resultCode == CallbackCodes.RESULTCODE_ADD_MEMO)
            toastMsg = getString(R.string.toastMsg_added_memo);
        else if(resultCode == CallbackCodes.RESULTCODE_UPDATE_MEMO)
            toastMsg = getString(R.string.toastMsg_updated_memo);
        else if(resultCode == CallbackCodes.RESULTCODE_DELETE_MEMO)
            toastMsg = getString(R.string.toastMsg_deleted_memo);
        Toast.makeText(this, toastMsg, Toast.LENGTH_SHORT).show();
        memolist_Adapter.onActivityResult(requestCode, resultCode, data);
    }
}
