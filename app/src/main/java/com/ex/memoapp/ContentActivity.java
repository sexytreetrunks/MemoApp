package com.ex.memoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ex.memoapp.db.MemoDAO;
import com.ex.memoapp.vo.MemoVO;

public class ContentActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_title;
    private EditText et_content;
    private Button btn_save;
    private Button btn_remove;

    private MemoDAO dao;
    private MemoVO memo;
    private int requestCode;
    private int pos;
    private int resultCode;

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

        resultCode = CallbackCodes.RESULTCODE_NOOP;
        Intent intent = getIntent();
        requestCode = intent.getIntExtra("requestCode",CallbackCodes.REQUESTCODE_ADD_MEMO);
        pos = intent.getIntExtra("pos",0);
        memo = (MemoVO) intent.getSerializableExtra("memoitem");
        et_title.setText(memo.getTitle());
        et_content.setText(memo.getContent());

        dao = new MemoDAO(getApplicationContext());
    }

    @Override
    public void onClick(View v) {
        Button btn = (Button) v;
        Intent intent = new Intent();
        switch (btn.getId()) {
            case R.id.btn_remove:
                if(requestCode == CallbackCodes.REQUESTCODE_ADD_MEMO){
                    Toast.makeText(this, getString(R.string.toastMsg_cannot_delete_memo), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    dao.delete(memo.getId());
                    resultCode = CallbackCodes.RESULTCODE_DELETE_MEMO;
                }
                break;
            case R.id.btn_save:
                if(et_title.getText().toString().equals("")) {
                    Toast.makeText(this, getString(R.string.toastMsg_title_required),Toast.LENGTH_SHORT).show();
                    return;
                }
                if(requestCode == CallbackCodes.REQUESTCODE_ADD_MEMO) {
                    memo.setTitle(et_title.getText().toString());
                    memo.setContent(et_content.getText().toString());
                    long rowId = dao.insert(memo);
                    memo.setId(rowId);
                    resultCode = CallbackCodes.RESULTCODE_ADD_MEMO;
                } else {
                    memo.setTitle(et_title.getText().toString());
                    memo.setContent(et_content.getText().toString());
                    dao.update(memo);
                    resultCode = CallbackCodes.RESULTCODE_UPDATE_MEMO;
                }
                intent.putExtra("result_data", memo);
                break;
        }
        intent.putExtra("pos",pos);
        setResult(resultCode, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(resultCode, intent);
        finish();
    }
}
