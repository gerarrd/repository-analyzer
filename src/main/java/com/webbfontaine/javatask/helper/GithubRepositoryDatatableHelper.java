package com.webbfontaine.javatask.helper;

import java.util.List;

import com.webbfontaine.javatask.model.GithubRepository;

public class GithubRepositoryDatatableHelper {
	private Long total;
	private Long totalNotFiltered;
	private List<GithubRepository> rows;
	
	public GithubRepositoryDatatableHelper() {}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Long getTotalNotFiltered() {
		return totalNotFiltered;
	}

	public void setTotalNotFiltered(Long totalNotFiltered) {
		this.totalNotFiltered = totalNotFiltered;
	}

	public List<GithubRepository> getRows() {
		return rows;
	}

	public void setRows(List<GithubRepository> rows) {
		this.rows = rows;
	}
}
