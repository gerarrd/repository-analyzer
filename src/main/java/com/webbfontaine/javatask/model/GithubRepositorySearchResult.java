package com.webbfontaine.javatask.model;

import java.util.List;

import com.webbfontaine.javatask.base.SearchResult;

public class GithubRepositorySearchResult extends SearchResult {
	private List<GithubRepository> items;
	
	public GithubRepositorySearchResult() {}

	public List<GithubRepository> getItems() {
		return items;
	}

	public void setItems(List<GithubRepository> items) {
		this.items = items;
	}
}