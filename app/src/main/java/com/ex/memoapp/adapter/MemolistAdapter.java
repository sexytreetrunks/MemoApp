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
        memoViewHolder.tv_item_date.setText(memoData.get(i).getDate());
    }

    @Override
    public int getItemCount() {
        return memoData.size();
    }

    //TODO: activityresult값을 토대로 adapter에서 recylerview 내용을 갱신해야함
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("adapter result","request: "+requestCode+", result: "+resultCode);
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
            intent.putExtra("memoitem",memoData.get(pos));
            ((Activity)context).startActivityForResult(intent, CallbackCodes.REQUESTCODE_UPDATE_MEMO);
        }
    }
}