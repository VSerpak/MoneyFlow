package com.example.vserp.moneyflow.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vserp.moneyflow.R;
import com.example.vserp.moneyflow.utils.Prefs;

/**
 * Created by vserp on 5/24/2016.
 */
public class ExpensesAdapter extends RecyclerView.Adapter{

    private Context mContext;
    private Cursor mCursor;

    public ExpensesAdapter(Context mContext, Cursor mCursor) {
        this.mContext = mContext;
        this.mCursor = mCursor;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_expenses_adapter,parent,false);
        return new InnerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        InnerViewHolder innerViewHolder = (InnerViewHolder) holder;
        mCursor.moveToPosition(position);

        innerViewHolder.tvName.setText(mCursor.getString(mCursor.getColumnIndex(Prefs.EXPENSE_NAMES_FIELD_NAME)));

        innerViewHolder.tvVolume.setText(mCursor.getString(mCursor.getColumnIndex(Prefs.EXPENSE_FIELD_VOLUME)));
        innerViewHolder.tvDate.setText(mCursor.getString(mCursor.getColumnIndex(Prefs.EXPENSE_FIELD_DATE)));
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    private class InnerViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName;
        public TextView tvVolume;
        public TextView tvDate;

        public InnerViewHolder(View view) {
            super(view);

            tvName = (TextView) view.findViewById(R.id.tvNameItemExpenses);
            tvVolume = (TextView) view.findViewById(R.id.tvVolumeItemExpenses);
            tvDate = (TextView) view.findViewById(R.id.tvDateItemExpenses);

        }
    }
}
