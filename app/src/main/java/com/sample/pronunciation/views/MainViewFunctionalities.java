package com.sample.pronunciation.views;

import com.sample.pronunciation.adapters.ScanResultsAdapter;
import com.sample.pronunciation.model.OCRModels.OCRResponseModel;

/**
 * Created by Bharath on 27/03/17, March, 2017.
 */
public interface MainViewFunctionalities {
	
	interface ActivityFunctions {
		
	}
	
	interface ResultsFragmentFunctions {
		
		void startProgress();
		
		void stopProgress();
		
		void showAllItems();
		
		void showResult();

		void addResultAtStart(OCRResponseModel model);
		
		void deleteItem();
		
		void deleteAllItems();
		
		void getAllItems();
		
		void showFailure(String message);
		
		void showDictionaryEntry();

		void setAdapter(ScanResultsAdapter adapter);
		
	}
}
