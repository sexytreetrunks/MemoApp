package com.ex.memoapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ex.memoapp.CallbackCodes;
import com.ex.memoapp.ContentActivity;
import com.ex.memoapp.R;
import com.ex.memoapp.vo.MemoVO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MemolistAdapter extends RecyclerView.Adapter<MemolistAdapter.MemoViewHolder> {
    private List<MemoVO> memoData;
    private Context context;

    public MemolistAdapter(List<MemoVO> memoData, Context context) {
        this.memoData = memoData;
        this.context = context;
    }

    @NonNull
    @Override
    public MemoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                            .inflate(R.layout.recyclerview_item, viewGroup, false);
        MemoViewHolder viewHolder = new MemoViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MemoViewHolder memoViewHolder, int i) {
        memoViewHolder.tv_item_title.setText(memoData.get(i).getTitle());
        memoViewHolder.tv_item_content.setText(memoData.get(i).getContent());
        String formatedDate = formatDatetime(memoData.get(i).getDate());
        memoViewHolder.tv_item_date.setText(formatedDate);
    }

    private String formatDatetime(String rowdatetime) {
        final int mm = 60;
        final int hh = mm * 60;
        final int dd = hh * 24;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formatedDatetime = "";
        Date dbdate = new Date();
        try {
            dbdate = format.parse(rowdatetime);
        }catch (ParseException pex) {
            Log.d("adapter err","db에 저장된 date형식이 잘못됨");
        }
        Date curdate = new Date();
        long diff = curdate.getTime() - dbdate.getTime();
        long sec = diff/1000;
        if(sec < mm) {
            formatedDatetime = sec + "초 전";
        } else if(sec >= mm && sec < hh) {
            formatedDatetime = sec/mm + "분 전";
        } else if(sec >= hh && sec < dd) {
            formatedDatetime = sec/hh + "시간 전";
        } else {
            formatedDatetime = new SimpleDateFormat("yyyy-MM-dd").format(dbdate);
        }
        return formatedDatetime;
    }

    @Override
    public int getItemCount() {
        return memoData.size();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("adapter result","request: "+requestCode+", result: "+resultCode);
        int pos = data.getIntExtra("pos",0);
        if(resultCode == CallbackCodes.RESULTCODE_ADD_MEMO) {
            MemoVO memo = (MemoVO) data.getSerializableExtra("result_data");
            memoData.add(pos,memo);
            notifyItemInserted(pos);
        } else if(resultCode == CallbackCodes.RESULTCODE_UPDATE_MEMO) {
            MemoVO memo = (MemoVO) data.getSerializableExtra("result_data");
            memoData.remove(pos);
            notifyItemRemoved(pos);
            memoData.add(0, memo);
            notifyItemInserted(0);
        } else if(resultCode == CallbackCodes.RESULTCODE_DELETE_MEMO) {
            memoData.remove(pos);
            notifyItemRemoved(pos);
            notifyItemRangeChanged(pos, memoData.size());
        }
    }

    public class MemoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_item_title;
        TextView tv_item_content;
        TextView tv_item_date;

        public MemoViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_item_title = itemView.findViewById(R.id.item_title);
            tv_item_content = itemView.findViewById(R.id.item_content);
            tv_item_date = itemView.findViewById(R.id.item_date);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            Intent intent = new Intent(v.getContext(),ContentActivity.class);
            intent.putExtra("requestCode",CallbackCodes.REQUESTCODE_UPDATE_MEMO);
            intent.putExtra("pos",pos);
            intent.putExtra("memoitem",memoData.get(pos));
            ((Activity)context).startActivityForResult(intent, CallbackCodes.REQUESTCODE_UPDATE_MEMO);
        }
    }
}
