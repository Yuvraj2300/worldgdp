/**
 * 
 */
var pageSize = 10;
$(function() {
	getGDP();

	$("#new-city").on('click', function() {

		loadNewCityModal();
	});

	$("#new-language").on('click', function() {
		loadNewLanguageModal();
	});

	$("#cities").on('click', '.delete-city', function() {
		var cityId = $(this).data("id");
		var result = confirm("Do you want to delete the city?");
		if (result) {
			$.ajax({
				method : "DELETE",
				url : "/worldgdp/api/cities/" + cityId,
				success : function() {
					success("City deleted successfully");
					getCities(1);
				},
				error : function(response) {
					error("Error occurred while deleting city");
				}
			});
		}
	});

	$("#languages").on('click', 'delete-language', function() {
		var code = $(this).data("code");
		var lang = $(this).data("lang");
		var result = confirm("Do you want to delete the language?");
		if (result) {
			$.ajax({
				method : "DELETE",
				url : "/worldgdp/api/languages/" + code + "/language/" + lang,
				success : function() {
					success("Language delted  successfully");
					getLanguages(1);
				},
				error : function(response) {
					error("Error ocurred while deleting the language");
				}
			});
		}
	});
	getCities(1);
	getLanguages(1);
});

function loadNewCityModal() {
	loadModal("city-form-template", {});
	/*
	 * var html = Mustache.to_html($("#city-form-template").html(), {});
	 * $("#worldModal").html(html);
	 */
	setupForm('language-form', function() {
		success("Language Added Successfully");
		getLanguages(1);
	});
	$("#isOfficial").on('change', function() {
		if ($(this).is(":checked"))
			$(this).val(1);
		if (!$(this).is(":checked"))
			$(this).val(0);
	});
}

function getCities(pageNo) {
	var params = {
		"pageNo" : pageNo
	};
	$.getJSON("/worldgdp/api/cities/" + code, params, function(data) {
		var md = {};
		md['list'] = data;
		if (data.length == pageSize) {
			md['more'] = 1;
		} else {
			md['more'] = 0;
		}

		md['pageNo'] = pageNo + 1;
		var html = Mustache.to_html($("#cities-list-template").html(), md);

		if (pageNo == 1) {
			$("#cities").html(html);
		} else {
			$("#cities button").remove();
			$("#cities").append(html);
		}
	});
}

function getLanguages(pageNo) {
	var params = {
		"pageNo" : pageNo
	};
	$.getJSON("/worldgdp/api/languages/" + code, params, function(data) {
		var md = {};
		md['list'] = data;
		if (data.length == pageSize) {
			md['more'] = 1;
		} else {
			md['more'] = 0;
		}
		md['pageNo'] = pageNo + 1;
		md['percentage_fmt'] = function() {
			if (this['percentage']) {
				return this['percentage'].toFixed(2);
			}
		}
		md['isOfficial_Bool'] = function() {
			return (this['isOfficial'] == "TRUE");
		}

		var html = Mustache.to_html($("#languages-list-template").html(), md);
		if (pageNo == 1) {
			$("#languages").html(html);
		} else {
			$("#languages button").remove();
			$("#languages").append(html);
		}
	});
}

function getGDP() {
	$.getJSON("/worldgdp/api/countries/" + code + "/gdp", function(data) {
		
	});
}
