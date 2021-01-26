package com.webbfontaine.javatask.helper;

public class GithubCommitsPerDayHelper {
	private int count;
	private String date;
	
	public GithubCommitsPerDayHelper() {}

	public GithubCommitsPerDayHelper(int count, String date) {
		this.count = count;
		this.date = date;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public void incrementCount() {
		this.count += 1;
	}
}