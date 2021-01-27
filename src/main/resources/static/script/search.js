function closeDetails() {
	$("#repository-details-container").hide();
	$("#search-result-container").show();
}

function loadRepository(fullName) {
	// don't load details if data is being loaded
	if($("#loading-container").is(":visible"))
		return;
	
	$("#repository-details-container").hide();
	$("#search-result-container").hide();
	$("#loading-container").show();
	$("#repo-search-input").prop( "disabled", true );

	$.ajax({url: "repository-details?fullName="+fullName, success: function(result){
		$("#repository-details-container").empty();
		var removeBookmark = $("#bookmarks-list").data("bookmarks").includes(result.fullName);
		$("<div class='d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pb-2 mb-3'>").append(
			$("<h1 class='h2'>").text(result.name),
			$("<div class='btn-toolbar mb-2 mb-md-0'>").append(
				$("<button id='bookmark-button' class='btn btn-sm btn-outline-secondary mr-1' onClick='updateBookmarks(\""+result.fullName+"\")'>")
					.text((removeBookmark? "Remove ": " ") + "Bookmark"),
				$("<button id='close-button' class='btn btn-sm btn-outline-secondary' onClick='closeDetails()'>")
					.text("Close")
			)
		).appendTo("#repository-details-container");
		
		$("#repository-details-container").append(
			$("<div class='border-bottom'>").append(
				$("<h6>").html("<i>By "+result.owner.username+"</i>"),
				$("<p class='lead'>").text(result.description),
			)
		);
	
		$("#repository-details-container").show();
		$.ajax({url: "repository-analysis?fullName="+fullName, success: function(analysis){
			$("#repository-details-container").append(
				$("<canvas class='my-4 chartjs-render-monitor border-bottom' id='daily-commits-graph' width='1507' height='636' style=': block; height: 424px; width: 1005px;'>")
			);
			
			generateDailyCommitGraph(analysis);
			
			var contributorDiv = $("<div class='row'>");
			
			$.each(analysis.contributors, function(i, contributor) {
				$("<div class='col-lg-4'>").append(
					$("<img class='rounded-circle float-left' src='"+contributor.avatarUrl+"' width='100' height='100'>"),
					$("<span class='f5 text-normal text-gray-light float-right'>").text("#"+i),
					$("<div style='margin: 10px 0px 0px 25%; font-size: 50px; font-weight: bolder; float: left;'>").text(contributor.commitImpact + "%"),
					$("<h3 style='clear: both'>").append(
						$("<a class='text-normal' href='#'>").text(contributor.username),
					),
					$("<p>").text("Donec sed odio dui. Etiam porta sem malesuada magna mollis euismod. Nullam id dolor id nibh ultricies vehicula ut id elit. Morbi leo risus, porta ac consectetur ac, vestibulum at eros. Praesent commodo cursus magna.")
				).appendTo(contributorDiv);
			});
			
			contributorDiv.appendTo("#repository-details-container");
			
			$("#loading-container").hide();
			$("#repo-search-input").prop( "disabled", false );
		}});
	}});
}

function formatName(value, row, index) {
	return "<a href='#' class='repo-details-link'>" + value + "</a>";
}

function queryParams(params) {
	params.search = $("#repo-search-input").data("queryParam") || "";
	return params;
}

function populateBookmarks() {
	var bookmarksArray = $("#bookmarks-list").data("bookmarks");
	$("#bookmarks-list").empty();
	
	$.each(bookmarksArray, function(i, bookmark) {
		var icon = $("#bookmark-icon").clone();
		var fullName = bookmark;
		bookmark = bookmark.split("/").pop();
		icon.show();
		$("<li class='nav-item'>").append(
			$("<a class='nav-link' href='#' onClick='loadRepository(\"" + fullName + "\")'>").append(
				icon,
				bookmark
			)
		).appendTo("#bookmarks-list");
	});
	
	if(bookmarksArray.length === 0) {
		var icon = $("#bookmark-icon").clone();
		icon.show();
		$("<li class='nav-item disabled'>").append(
			$("<a class='nav-link'>").append(
				icon,
				"No bookmarks"
			)
		).appendTo("#bookmarks-list");
	}
}

function updateBookmarks(fullName) {
	var bookmarks = $("#bookmarks-list").data("bookmarks");
	
	if(bookmarks.includes(fullName)) {
		bookmarks.splice(bookmarks.indexOf(fullName), 1);
		$("#bookmark-button").text("Bookmark");
	}
	else {
		bookmarks.push(fullName);
		$("#bookmark-button").text("Remove Bookmark");
	}
	
	$("#bookmarks-list").data("bookmarks", bookmarks);
	localStorage.setItem("bookmarked-github-repositories", bookmarks);
	populateBookmarks();
}

function generateDailyCommitGraph(analysis) {
	var labels = [];
	var datasets = [];
	analysis.commitsPerDay.forEach(function(item, idx) {
		labels[idx] = item.date;
		datasets[idx] = item.count;
	});
	
	var ctx = document.getElementById("daily-commits-graph");
	var myChart = new Chart(ctx, {
		type: 'line',
		data: {
			labels: labels,
			datasets: [{
				data: datasets,
				lineTension: 0,
				backgroundColor: 'transparent',
				borderColor: '#007bff',
				borderWidth: 4,
				pointBackgroundColor: '#007bff'
			}]
		},
		options: {
			scales: {
				yAxes: [{
					scaleLabel: {
						display: true,
						labelString: 'Count of commits'
					},
					ticks: {
						beginAtZero: false
					}
				}],
			},
			legend: {
				display: false,
			}
		}
	});
}

window.loadRepo = {
	'click .repo-details-link': function (e, value, row, index) {
		loadRepository(row.fullName);
	}
}

$("#repo-search-input").on("keypress", function(e) {
	if(e.which == 13) {
		var queryParam = $("#repo-search-input").val();
		var $table = $("#repository-list-table");
		$("#repo-search-input").data("queryParam", queryParam);
		$table.bootstrapTable("refresh");
	}
});

// we do this on document ready to populate the list of bookmarks
$(document).ready(function() {
	var bookmarks = localStorage.getItem("bookmarked-github-repositories") || "";
	var bookmarksArray = bookmarks.split(",").filter(function(v){return v!==''}) || [];
	$("#bookmarks-list").data("bookmarks", bookmarksArray);
	populateBookmarks();
});