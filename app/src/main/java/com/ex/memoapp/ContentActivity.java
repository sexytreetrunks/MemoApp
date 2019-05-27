package com.ex.memoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ex.memoapp.db.MemoDAO;
import com.ex.memoapp.vo.MemoVO;

public class ContentActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_title;
    private EditText et_content;
    private Button btn_save;
    private Button btn_remove;

    private MemoDAO dao;
    private MemoVO memo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        et_title = (EditText) findViewById(R.id.et_title);
        et_content = (EditText) findViewById(R.id.et_content);
        btn_save = (Button) findViewById(R.id.btn_save);
        btn_remove = (Button) findViewById(R.id.btn_remove);
        btn_save.setOnClickListener(this);
        btn_remove.setOnClickListener(this);

        Intent intent = getIntent();
        memo = (MemoVO) intent.getSerializableExtra("memoitem");
        et_title.setText(memo.getTitle());
        et_content.setText(memo.getContent());

        dao = new MemoDAO(getApplicationContext());
    }

    //TODO: request값에 따라서 dao로 db작업
    @Override
    public void onClick(View v) {
        Button btn = (Button) v;
        Intent intent = new Intent();
        int resultCode = 0;
        switch (btn.getId()) {
            case R.id.btn_remove:
                if(memo.getId()==0){
                    resultCode = CallbackCodes.RESULTCODE_NOOP;
                } else {
                    resultCode = CallbackCodes.RESULTCODE_DELETE_MEMO;
                }
                break;
            case R.id.btn_save:
                if(memo.getId() == 0) {
                    resultCode = CallbackCodes.RESULTCODE_ADD_MEMO;
                } else {
                    resultCode = CallbackCodes.RESULTCODE_UPDATE_MEMO;
                }
                break;
        }
        // remove, save의 경우 수정된 리스트가 recyclerview에 반영되어야함
        setResult(resultCode, intent);
        finish();
    }
}
