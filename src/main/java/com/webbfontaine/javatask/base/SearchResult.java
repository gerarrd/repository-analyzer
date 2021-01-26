package com.webbfontaine.javatask.base;

import com.fasterxml.jackson.annotation.JsonAlias;

public class SearchResult {
	@JsonAlias("total_count")
	private int totalCount;
	
	@JsonAlias("incomplete_results")
	private boolean incompleteResults;
	
	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public boolean isIncompleteResults() {
		return incompleteResults;
	}

	public void setIncompleteResults(boolean incompleteResults) {
		this.incompleteResults = incompleteResults;
	}

	@Override
	public String toString() {
		return String.format("[total count: %s,\nincomplete results: %s]", totalCount, incompleteResults);
	}
}