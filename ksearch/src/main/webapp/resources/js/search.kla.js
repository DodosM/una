/*******************************************************
* 프로그램명 	 : search.kla.js
* 설명        : 통합검색 환경에서 KLA 통해 조회하는 공통 함수 - ajax 함수 사용
* 모듈    : -
*    급상승키워드	rankupper();
*    핫이슈키워드	issueword();
*    최신콘텐츠		newestcontents();
*******************************************************/

/**
 * 화면 구동시 호출하는 함수정보
 * 사용되는 함수목록은 자동완성(), 오타교정(), 인기검색어() 기본 사용합니다.
 * - 해당 정보 호출하기전에 KSF 모듈에서 기능 사용을 적용하였는지 확인되어야 합니다.
 */
$(function(){

	//급상승키워드
	rankupper();

	//검색어별 많이 조회한 콘텐츠
	docidrank();

	//최신콘텐츠
	//newestcontents();

	//click content log writing
	$('.lst-type-wrap .title').on('click', function() {
		var docid = "";
		var section = $(this).parents('.lst-type-wrap').children('h3').attr('data-target');
		var targeturl = "";
		var title = "";

		docid = $(this).parents('li').children('input[name=id]').val();
		targeturl = $(this).attr('href');
		title = $(this).text().trim()==''?$(this).next().children('dt').children('a').text():$(this).text().trim();

		if(title.indexOf('|') > -1) title = title.split('|')[1].trim();

		$('#docid').val(docid);
		$('#section').val(section);
		$('#targeturl').val(targeturl);
		$('#title').val(title);

		var url = "kla/click.do";
		var params = $('#clickForm').serialize();
		$.post(url, params);

	});
});

/**
 * 키워드별 가장 많이 클릭한 콘텐츠
 * @returns
 */
function docidrank(){
	var ajax = {
			type: "GET",
			url: "",
			data: { "duration" : "weekly"},
			success: {},
			error: {}
	};
	ajax.url = 'kla/idrank.do';
	ajax.success = function(data) {
		if(data.length == 0 ) return;


		if(data.length > 0){
			var html = "", activeClasss="";
			var itemValue;
			html +="<ul class=\"lst-keyword count\">";
			$.each(data, function( i, item) {
				i++;
				metaClassText= "";

				itemurl = (item.targeturl).replace("\\u003d","=");
				activeClasss ="";
				if(i < 4){
					activeClasss = "active";
				}

				html +="<li class='"+activeClasss+"'>";
				html += "<span class='number'>"+item.rank+"</span>";
				html += "<a href=\""+itemurl+"\" target=\"_blank\" >" + item.title + "</a>";
				html += "</li>\n";

			});
			html +="</ul>";
		}

		$("#docidranks").html(html);

	};
	ajax.error = function(xhr, ajaxOptions) {
		console.log(ajaxOptions);
	};

	$.ajax(ajax);
}

/**
* 급상승키워드
* @ return str
**/
function rankupper(dateCode){
	var ajax = {
			type: "GET",
			url: "",
			data: { "duration" : "weekly"},
			success: {},
			error: {}
	};
	ajax.url = 'kla/rankup.do';
	ajax.success = function(data) {
		if(data.length == 0 ) return;


		if(data.length > 0){
			//console.log("[initPPK] success");
			var html = "", htmlRank="", htmlPop="";
			var metaClass="", activeClasss="", metaNm="", metaClassText="";
			var itemValue, rankGap;

			$.each(data, function( i, item) {
				metaClassText= "";

				itemValue = (item.keyword).replace(/</gi,"&lt;").replace(/>/gi,"&gt;");
				if(item.rank_gap == "new") {
					metaClass = "new";
					metaNm = "신규";
					metaClassText = "new"
				}else if(item.rank_gap == "out") {
					metaClass = "ico down";
					metaNm = "하락";
				}else{
					rankGap = Number(item.rank_gap);
					if(rankGap == 0){
						metaClass = "ico equal";
						metaNm = "변동없음";
						metaClassText = "-"
					}else if(rankGap > 0){
						metaClass = "ico up";
						metaNm = "상승";
					}else{
						metaClass = "ico down";
						metaNm = "하락";
					}
				}

				html += "<li>";
				html += "<span class='number'>"+item.rank+"</span>";
				html += "<a href=\"javascript:void(0);\">" + itemValue + "</a>";
				html += "<span class=\"right-side\"><span class ='"+metaClass+"'>"+metaClassText+"</span>";
				if(metaNm == "상승" || metaNm == "하락"){
					html += "<span class='hit-num'>"+item.hit_num+"</span>";
				}

				html += "<span class=\"blind\">"+metaNm+"</span>";
				html += "</span></li>\n";

			})
		}
		htmlRank = "<h3>급상승 검색어</h3>";
		htmlRank += "<div class=\"scroll-wrap\">";
		htmlRank += "<ul class=\"lst-keyword\" id=\"scrollstage\">";
		htmlRank += html;
		htmlRank += "</ul>";
		htmlRank += "</div>";

		htmlRank += "<div id=\"showpop\" style=\"display: none;\">"
		htmlRank += "<h3>급상승 검색어</h3>";
		htmlRank += "<div>";
		htmlRank += "<ul class=\"lst-keyword\" data-trcode=\"rise\">";
		htmlRank += html;
		htmlRank += "</ul>";
		htmlRank += "</div>";
		htmlRank += "</div>";


		$("#rankupp").html(htmlRank);

		//급상승키워드 롤링
		rankupperRooling();
	};
	ajax.error = function(xhr, ajaxOptions) {
		console.log(ajaxOptions);
	};

	$.ajax(ajax);
}

function rankupperRooling(){
	 var scrollCtrl    = new ScrollControl('scrollstage', {inteval:30,stop:1000,height:'20'});
     $('footer #showpop').on('mouseleave', function() {
         $(this).css('display','none');
     });
}

