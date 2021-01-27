package com.webbfontaine.javatask.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webbfontaine.javatask.helper.GithubCommitsPerDayHelper;
import com.webbfontaine.javatask.helper.GithubRepositoryAnalysisHelper;
import com.webbfontaine.javatask.helper.GithubRepositoryDatatableHelper;
import com.webbfontaine.javatask.model.GithubCommit;
import com.webbfontaine.javatask.model.GithubRepositorySearchResult;
import com.webbfontaine.javatask.model.GithubUser;
import com.webbfontaine.javatask.restservice.GithubRestService;
import com.webbfontaine.javatask.service.GithubRepositoryService;

@Service
public class GithubRepositoryServiceImpl implements GithubRepositoryService {
	private final GithubRestService githubRestService;

	@Autowired
	public GithubRepositoryServiceImpl(GithubRestService githubRestService) {
		this.githubRestService = githubRestService;
	}

	@Override
	public GithubRepositoryAnalysisHelper getRepositoryAnalysis(String fullName) {
		List<GithubUser> contributors = githubRestService.getContributors(fullName);
		List<GithubCommit> lastHundredCommits = githubRestService.getLastHundredCommits(fullName);
		List<GithubCommitsPerDayHelper> overallDailyCommits = new ArrayList<>();
		final Map<String, Object> dailyCountObject = new HashMap<>();

		GithubRepositoryAnalysisHelper helper = new GithubRepositoryAnalysisHelper();
		helper.setContributors(contributors);

		dailyCountObject.put("currentDate", null);
		dailyCountObject.put("dailyCommit", null);

		lastHundredCommits.forEach(commit -> {
			String date = commit.getCommit().getCommitter().getDate().substring(0, 10);

			if (!date.equals(dailyCountObject.get("currentDate"))) {
				dailyCountObject.put("currentDate", date);
				dailyCountObject.put("dailyCommit", new GithubCommitsPerDayHelper(0, date));
				overallDailyCommits.add((GithubCommitsPerDayHelper) dailyCountObject.get("dailyCommit"));
			}

			((GithubCommitsPerDayHelper) dailyCountObject.get("dailyCommit")).incrementCount();
			
			contributors.stream()
					.filter(contributor -> commit.getAuthor() != null && contributor.getId().equals(commit.getAuthor().getId()))
					.findFirst().orElseGet(() -> {
						if(commit.getAuthor() != null) {
							contributors.add(commit.getAuthor());
							return commit.getAuthor();
						} else {
							return new GithubUser();
						}
					}).incrementCommitCount();
		});
		
		contributors.forEach(contributor -> contributor.adjustImpact(lastHundredCommits.size()));

		helper.setCommitsPerDay(overallDailyCommits);
		return helper;
	}

	@Override
	public GithubRepositoryDatatableHelper getRepositories(String search, Long offset, Long limit) {
		GithubRepositorySearchResult result = githubRestService.getRepositories(search);
		GithubRepositoryDatatableHelper helper = new GithubRepositoryDatatableHelper();
		helper.setTotal(new Long(result.getTotalCount()));
		helper.setRows(result.getItems());
		return helper;
	}
}