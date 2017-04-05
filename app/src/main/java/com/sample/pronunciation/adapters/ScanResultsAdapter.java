package com.sample.pronunciation.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sample.pronunciation.R;
import com.sample.pronunciation.model.OCRModels.OCRResponseModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bharat.vakalapudi on 4/4/2017.
 */

public class ScanResultsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final String TAG = ScanResultsAdapter.class.getSimpleName();

    private final int VIEW_TYPE_SEARCH_BAR = 1;

    private final int VIEW_TYPE_HEADER = 2;

    private final int VIEW_TYPE_PARSED_RESULT = 3;

    private List<OCRResponseModel> mScanResults;

    private int NUM_OF_HEADERS;

    private OnItemClickListener mListener;

    public interface OnItemClickListener {

        void onItemClicked(int position, OCRResponseModel resultItem);
    }

    public ScanResultsAdapter(OnItemClickListener listener) {
        this.mListener = listener;
        mScanResults = new ArrayList<>();
    }

    public ScanResultsAdapter(List<OCRResponseModel> scanResults, int numOfHeaders, OnItemClickListener listener) {
        this.mScanResults = scanResults;
        this.NUM_OF_HEADERS = numOfHeaders;
        this.mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView;

        switch (viewType) {
            case VIEW_TYPE_HEADER:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recyclerview_header, parent, false);
                return new HeaderViewHolder(itemView);


            case VIEW_TYPE_PARSED_RESULT:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recyclerview_result_item, parent, false);
                return new ResultViewHolder(itemView);

            case VIEW_TYPE_SEARCH_BAR:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recyclerview_search, parent, false);
                return new SearchViewHolder(itemView);

            default:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recyclerview_result_item, parent, false);
                return new ResultViewHolder(itemView);

        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_HEADER:
                ((HeaderViewHolder) holder).headerItem.setText("Header");
                break;
            case VIEW_TYPE_SEARCH_BAR:
                ((SearchViewHolder) holder).searchView.setHint("Search words");
                break;
            case VIEW_TYPE_PARSED_RESULT:
                Log.d(TAG, "position : " + position + " type result");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClicked(position, mScanResults.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {

        return (mScanResults.size() + NUM_OF_HEADERS + 1);
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            return VIEW_TYPE_SEARCH_BAR;
        } else if (position == 1) {
            return VIEW_TYPE_HEADER;
        } else {
            return VIEW_TYPE_PARSED_RESULT;
        }
    }

    public void addResultAtStart(OCRResponseModel resultModel) {
        this.mScanResults.add(0, resultModel);
        notifyDataSetChanged();
    }

    static class ResultViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.resultItem)
        TextView resultItem;


        ResultViewHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.header)
        TextView headerItem;

        HeaderViewHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class SearchViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.seachView)
        TextView searchView;

        SearchViewHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
