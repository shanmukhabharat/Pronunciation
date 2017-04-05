package com.sample.pronunciation.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sample.pronunciation.R;
import com.sample.pronunciation.model.OCRModels.ParsedResult;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bharat.vakalapudi on 4/4/2017.
 */

public class ScanResultsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	
	private final int VIEW_TYPE_SEARCH_BAR = 1;
	
	private final int VIEW_TYPE_HEADER = 2;
	
	private final int VIEW_TYPE_PARSED_RESULT = 3;
	
	private List<ParsedResult> mScanResults;
	
	private int NUM_OF_HEADERS;
	
	public ScanResultsAdapter(List<ParsedResult> scanResults, int numOfHeaders) {
		
		this.mScanResults = scanResults;
		this.NUM_OF_HEADERS = numOfHeaders;
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
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		
		switch (holder.getItemViewType()) {
			case VIEW_TYPE_HEADER:
				((HeaderViewHolder) holder).headerItem.setText("Header");
				break;
			case VIEW_TYPE_SEARCH_BAR:
				((SearchViewHolder) holder).searchView.setHint("Search words");
				break;
			case VIEW_TYPE_PARSED_RESULT:
				((ResultViewHolder) holder).resultItem.setText(
						(String) mScanResults.get(position).getParsedText());
		}
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
	
	public interface OnItemClickListener {
		
		void onItemClicked(int position, ParsedResult resultItem);
	}
	
	static class ResultViewHolder extends RecyclerView.ViewHolder {
		
		@BindView(R.id.resultItem)
		TextView resultItem;
		
		
		public ResultViewHolder(View itemView) {
			
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}
	
	class HeaderViewHolder extends RecyclerView.ViewHolder {
		
		@BindView(R.id.header)
		TextView headerItem;
		
		public HeaderViewHolder(View itemView) {
			
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}
	
	class SearchViewHolder extends RecyclerView.ViewHolder {
		
		@BindView(R.id.seachView)
		TextView searchView;
		
		public SearchViewHolder(View itemView) {
			
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}
}
