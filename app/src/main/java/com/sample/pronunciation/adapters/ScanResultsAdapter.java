package com.sample.pronunciation.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sample.pronunciation.R;
import com.sample.pronunciation.model.OCRModels.ParsedResult;

import java.util.List;

/**
 * Created by bharat.vakalapudi on 4/4/2017.
 */

public class ScanResultsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ParsedResult> mScanResults;
    private int NUM_OF_HEADERS;
    private int VIEW_TYPE_SEARCH_BAR = 1;
    private int VIEW_TYPE_HEADER = 2;
    private int VIEW_TYPE_PARSED_RESULT = 3;

    public ScanResultsAdapter(List<ParsedResult> scanResults, int numOfHeaders) {
        this.mScanResults = scanResults;
        this.NUM_OF_HEADERS = numOfHeaders;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

       View itemView;

        switch (viewType){
            case VIEW_TYPE_HEADER   :   itemView =  LayoutInflater.from(parent.getContext())
                                                                    .inflate(R.layout.recyclerview_header, parent, false);
                                        return new HeaderViewHolder(itemView);


            case VIEW_TYPE_PARSED_RESULT    :   itemView =  LayoutInflater.from(parent.getContext())
                                                                            .inflate(R.layout.recyclerview_header, parent, false);
                                                return new ViewHolder(itemView);

            case VIEW_TYPE_SEARCH_BAR   :   itemView =  LayoutInflater.from(parent.getContext())
                                                                        .inflate(R.layout.recyclerview_search, parent, false);
                                            return new SearchViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

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

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    class SearchViewHolder extends RecyclerView.ViewHolder {

        public SearchViewHolder(View itemView) {
            super(itemView);
        }
    }
}
