/**
 * konan 통합검색 검색 조회를 위한 스크립트
 * ============================================
 *
 * */
var domainUrl="localhost";
var imgs = new Array();
function getimagesize(url, target) {
    if ( typeof(imgs[url])=="undefined" || !imgs[url].complete ) { // 정의되지 않았거나 로딩되지 않은 경우
        imgs[url] = new Image(); // 이미지 Object
        imgs[url].src = url; // 이미지 지정
        setTimeout("getimagesize('"+url+"','"+target+"')",100); // 0.1초 후 재귀 호출
    } else {
		 $('.gallery figure a'+target).attr('data-size',imgs[url].width+"x"+imgs[url].height);
    }
}

//쿠키값 설정
function setCookie(c_name,value,exdays) {
  var exdate=new Date();
  exdate.setDate(exdate.getDate() + exdays);
  var c_value=escape(value) + ((exdays==null) ? "" : "; expires="+exdate.toUTCString()) + "; domain="+domainUrl;
  document.cookie=c_name + "=" + c_value;
}

//쿠키값 조회
function getCookie(c_name) {
  var i,x,y,cookies=document.cookie.split(";");
  for (i=0;i<cookies.length;i++) {
    x=cookies[i].substr(0,cookies[i].indexOf("="));
    y=cookies[i].substr(cookies[i].indexOf("=")+1);
    x=x.replace(/^\s+|\s+$/g,"");

    if (x==c_name) {
      return unescape(y);
    }
  }
}

// 페이지 선택
function gotopage(p) {
	$('#page').val((p+1));
	$('#contents').load($('#historyForm').attr('action'), $('#historyForm').serializeArray());
	$('#historyForm').submit();
}

// 결과내재검색
function reSrch() {
	var preKwds = $('#preKwds').val();
	var curKwd = $('#kwd').val();
	var preKwdList = preKwds.split(',');

	preKwdList.forEach(function(v, i) {
		if( v == curKwd) {
			preKwdList.splice(i, 1);
		}
	});

	preKwds = preKwdList.join(",");

	if(preKwds != '') {
		preKwds = preKwds+','+curKwd;
	}else {
		preKwds = curKwd;
	}

	$('#tr_code').val('s_chk');
	$('#preKwds').val(preKwds);
}

//날짜를 입력 하면 오늘 날짜로부터 숫자만큼 전날의 날짜를 yyyy-mm-dd 형식으로 돌려 준다.
function caldate(day) {
	var y, m, d;
	var today = new Date();
	var caldate = new Date(Date.parse(today) - day*1000*60*60*24);

	y = caldate.getFullYear();
	m = caldate.getMonth()+1;
	m = m < 10 ? '0'+m : m;
	d = caldate.getDate();
	d = d < 10 ? '0'+d : d;

	return y+'-'+m+'-'+d;
}

//날짜를 입력 하면 오늘 날짜로부터 숫자만큼 전날의 날짜를 yyyymmdd 형식으로 돌려 준다.
function caldate2(date) {
	var today = new Date();
	var y, m, d;

	if(date == 'w') {
		today.setDate(today.getDate()-7);
	}else if(date == 'm') {
		today.setMonth(today.getMonth()-1);
	}else if(date == 'y') {
		today.setFullYear(today.getFullYear()-1);
	}else {
		return '';
	}

	y = today.getFullYear();
	m = today.getMonth()+1;
	m = m < 10 ? '0'+m : m;
	d = today.getDate();
	d = d < 10 ? '0'+d : d;

	return y+''+m+''+d;
}

// 날짜 포맷 변환 (yyyymmdd -> yyyy.mm.dd)
function dateFormat(str) {
	var result = str;
	if (str != "" ) {
		if (str.length == "8") {
			year = str.substr(0,4);
			month = str.substr(4,2);
			day = str.substr(6,2);
			result = year+"."+month+"."+day;
		}
	}
	return result;
}

function searchOpengov(_this) {
	$('#group').val($(_this).children('span').attr('data-tab'));
	$('#historyForm').submit();
}

function detailCheck(kwd) {
	$('#detail').val(false);
	$('#basickwd').val('');
	$('#exactkwd').val('');
	$('#inkwd').val('');
	$('#notkwd').val('');

	if(kwd.indexOf(' -') > -1 || kwd.indexOf(' +') > -1 || kwd.indexOf(' "') > -1) {
		var word = kwd.split("\"");
		var word2 = '';
		var basickwd = '';
		var basickwdCnt = 0;
		var exactkwd = '';
		var exactkwdCnt = 0;
		var inkwd = '';
		var inkwdCnt = 0;
		var notkwd = '';
		var notkwdCnt = 0;
		word.forEach(function(v, i) {
			if(i%2 == 1) {
				if(exactkwdCnt > 0) exactkwd += ',';
				exactkwd +=v;
				exactkwdCnt++;
			}else {
				word2 = v.split(" ");

				word2.forEach(function(v2, i) {
					if(v2.length > 0) {
						if(v2.indexOf('-')==0) {
							if(notkwdCnt > 0) notkwd += ',';
							notkwd += v2.replace('-','');
							notkwdCnt++;
						}else if(v2.indexOf('+')==0){
							if(inkwdCnt > 0) inkwd += ',';
							inkwd += v2.replace('+','');
							inkwdCnt++;
						}else {
							if(basickwdCnt > 0) basickwd += ',';
							basickwd += v2;
							basickwdCnt++;
						}
					}
				});
			}
		});

		$('#detail').val(true);
		$('#basickwd').val(basickwd.trim());
		$('#exactkwd').val(exactkwd.trim());
		$('#inkwd').val(inkwd.trim());
		$('#notkwd').val(notkwd.trim());
	}
}

function showFilter() {
	var view = $('.search-option-inner');
	$('.more-layer').hide();
	view.show();
	$('.search-option a').attr('class','active').find('>span').attr('class','down');
	$('#srch_opt').val('y');
}

function hideFilter() {
	var view = $('.search-option-inner');
	$('.more-layer').hide();
	view.hide();
	$('.search-option a').removeClass('active').find('>span').removeClass('down');
	$('#srch_opt').val('n');
}


// 검색
$(function() {
	var sort = $('#sort').val();

	// 검색 타깃 초기화 및 사이드바 위젯 초기화
	$('.gnb')
		.find('li > span').removeClass('active').end()
		.find('[data-target=' + $('#category').val() + ']').addClass('active').end();

	if($('#srch_opt').val() == 'y') {
		showFilter();
	};

	//검색옵션 버튼
	$('.search-option a').on('click',function(){
		var view = $('.search-option-inner');
		if(view.is(':visible')){
			$('.more-layer').hide();
			view.hide();
			$(this).removeClass('active').find('>span').removeClass('down');
			$('#srch_opt').val('n');
		} else {
			$('.more-layer').hide();
			view.show();
			$('.search-option-menu a').find('>span').attr('class','ico-set up');
			$(this).attr('class','active').find('>span').attr('class','down');
			$('#srch_opt').val('y');
		}
	});

	//검색옵션 메뉴
	$('.search-option-menu > li > a,.more-btn > a').on('click',function(){
		var view = $(this).next();
		if(view.is(':visible')){
			view.hide();
			if($(this).find('>span').attr('class') != 'gnb-bar'){
				$(this).find('>span').attr('class','ico-set up');
			}
		} else {
			$('.more-layer').hide();
			view.show();
			if($(this).find('>span').attr('class') != 'gnb-bar'){
				$('.search-option-menu a').find('>span').attr('class','ico-set up');
				$(this).find('>span').attr('class','ico-set down');
			} else if($(this).find('>span').attr('class') == 'gnb-bar'){
				$('.search-option-menu a').find('>span').attr('class','ico-set up');
			}
		}
	});

	// 필터 초기화
	var sortObj = $('header .search-option-inner .more-layer[data-param=sort]')
					.find('a').removeClass('active').end()
					.find("[data-cd='"+sort+"']");
	sortObj.addClass('active').end();
	$('header .search-option-inner .more-layer[data-param=sort]').prev('a').text(sortObj.text()).append($('<span>').addClass('ico-set up'));

	if($('#date').val() != '') {
		var dateObj = $('header .search-option-inner .more-layer[data-param=date]')
						.find('a').removeClass('active').end()
						.find('[data-cd='+$('#date').val()+']');
		dateObj.addClass('active').end();
		if($('#date').val() == 'input') {
			$('#sdate').val($('#startDate').val());
			$('#edate').val($('#endDate').val());
			$('header .search-option-inner .more-layer[data-param=date]').prev('a').text(dateFormat($('#startDate').val())+'~'+dateFormat($('#endDate').val())).append($('<span>').addClass('ico-set up'));
			//날짜 타입 입력 시 길이 조절
			$('header .search-option-inner > ul >li:nth-child(2)').attr("style","width:185px");
			$('header .search-option-inner > ul').attr("style","width:630px");

		}else {
			$('header .search-option-inner .more-layer[data-param=date]').prev('a').text(dateObj.text()).append($('<span>').addClass('ico-set up'));
			$('header .search-option-inner').find('li:nth-child(2)').attr("style","");
			$('header .search-option-inner > ul').attr("style","");
		}
		showFilter();
	}else {
		$('header .search-option-inner .more-layer[data-param=date]')
			.find('a').removeClass('active').end()
			.find('[data-cd=""]').addClass('active').end();
	}

	if($('#fields').val() != '') {
		var fieldsObj = $('header .search-option-inner .more-layer[data-param=fields]')
						.find('a').removeClass('active').end()
						.find('[data-cd='+$('#fields').val()+']');
		fieldsObj.addClass('active').end();
		$('header .search-option-inner .more-layer[data-param=fields]').prev('a').text(fieldsObj.text()).append($('<span>').addClass('ico-set up'));
		showFilter();
	}else {
		$('header .search-option-inner .more-layer[data-param=fields]')
			.find('a').removeClass('active').end()
			.find('[data-cd=""]').addClass('active').end();
	}

	// detail search 초기화
	if($('#detail').val() == 'true') {
		$('#detailkwd').val($('#kwd').val().trim());

		var basickwd = $('#basickwd').val().trim();
		var exactkwd = $('#exactkwd').val().trim();
		var inkwd = $('#inkwd').val().trim();
		var notkwd = $('#notkwd').val().trim();

		$('#s1').val(basickwd);
		$('#s2').val(exactkwd);
		$('#s3').val(inkwd);
		$('#s4').val(notkwd);
		$('#detailkwd').css('overflow-y','scroll');

		$('#column-left .detail').css('display','block');
		var detailTxt = "";

		if(basickwd.length > 0) {
			detailTxt += "<span>'"+basickwd+"'</span>에 대한 검색결과 중 ";
		}
		if(exactkwd.length > 0) {
			detailTxt += "<span>'"+exactkwd+"'</span>이(가) 순서대로 존재";
			detailTxt += (inkwd.length > 0 || notkwd.length > 0) ? "하고 " : "한 ";
		}
		if(inkwd.length > 0) {
			detailTxt += "<span>'"+inkwd+"'</span>을(를) 포함";
			detailTxt += notkwd.length > 0 ? "하고 " : "한 ";
		}
		if(notkwd.length > 0) {
			detailTxt += "<span>'"+notkwd+"'</span>을(를) 제외한 ";
		}
		detailTxt += "상세검색 결과입니다.";

		$('#column-left .detail').append(detailTxt);
		showFilter();
	}else {
		$('#detailkwd').val($('#kwd').val());
		$('#s1').val($('#kwd').val());
	}

	// 그룹박스 초기화
	$('#groupbox').find('input[name=group]').each(function() {
		$(this).prop('checked', ($.inArray($(this).next().children('span.groupnm').text(), initValues) != -1));
	});

	// Grouping Site 초기화
	if($('#group').val()) {
		var initValues = $('#group').val().split(',');
		$('#groupbox').find('input[name=group]').each(function() {
			$(this).prop('checked', ($.inArray($(this).next().children('span.groupnm').text(), initValues) != -1));
		});
	}

	// 정렬 초기화
	$('section#column-left .result-sort, #container .result-sort')
	    .find("a[data-sort='"+sort+"']").addClass('active').end()
		.find('form').deserialize($('#historyForm').serializeArray());

	// 상세검색 입력 이벤트
	$('#s1, #s2, #s3, #s4').keyup(function(e) {
		if(e.which != 188 && e.which != 32) {
			var id = $(this).attr('id');
			var basictxt = $('#s1').val();
			var exactTxt = $('#s2').val();
			var inTxt = $('#s3').val();
			var notTxt = $('#s4').val();
			var thisTxt = $(this).val().replace(/,\s*,+/g,',');

			switch(id) {
				case 's2' : exactTxt = $.trim(thisTxt); break;
				case 's3' : inTxt = thisTxt; break;
				case 's4' : notTxt = thisTxt; break;
				default : basictxt = thisTxt;
			}

			exactTxt = exactTxt.length>0?"\""+exactTxt.replace(/,\s*/g,'\" \"')+"\"":"";
			inTxt = inTxt.length>0?"+"+inTxt.replace(/,\s*/g,' +'):"";
			notTxt = notTxt.length>0?"-"+notTxt.replace(/,\s*/g,' -'):"";

			$('#detailkwd').val((basictxt+" "+exactTxt+" "+inTxt+" "+notTxt).trim());
			$('#detailkwd').css('overflow-y','scroll');
		}
	});

	// 결과 내 재검색 플래그 설정
	if ($('#resrch').val() == 'true') {
		$('header #search_re').prop('checked', true);
	}

	/* Event Handlers */

	/* 검색 폼 제출 시 */
	$('#historyForm').submit(function(e) {
		var resrchYn = $('header #search_re').is(':checked');
		if(resrchYn == true) reSrch();
		else $('#preKwds').val($("#kwd").val());

		$('#id').val('');
		$('#url').val('');
		$('#tit').val('');
		//$('#categorize').val($('#category').val() != 'total');
		$('#resrch').val(resrchYn);
		$('#recent').recent('add', $('#kwd').val()); // 최근 검색어 추가
	});


	/* 상단 키워드 입력 폼을 통한 검색 시 */
	$('header form, footer form').submit(function(e) {
		e.preventDefault();
		e.stopPropagation();
		var kwd = $(this).find('input[name=kwd]').val();
		var ctgr = $('#category').val();
		var trcode = "search01";

		if($(this).parents('header').length < 1) trcode = "search02";

		if ($.trim(kwd)) {
			detailCheck(kwd);
			$('#page').val('1');
			$('#group').val('');
			$('#kwd').val(kwd);
			$('#sort').val('d');
			$('#date').val('');
			$('#fields').val('');
			$('#category').val(ctgr=='site'?'total':ctgr);
			$('#tr_code').val(trcode);
			$('#historyForm').submit();
		} else {
			$('#kwd').val("");
			$('#page').val('1');
			$('#historyForm').submit();
		}
	});



	/* Grouping Handler */
	$('#groupbox').on('change', 'input[name=group]', function () {
		var obj = $(this).parents('#groupbox');

		// 개별사이트 선택시, 전체선택 체크해제
		if($(this).val()) {
			obj.find(':checkbox[value=""]').prop('checked', false);
		}else {
			// 전체선택시 toggle
			if($(this).prop('checked')) obj.find(':checkbox').not(this).prop('checked', true);
			else obj.find(':checkbox').not(this).prop('checked', false);
		}
	}).on('click', 'button', function() {

		var obj = $(this).parents('#groupbox');

		var checkedList = obj.find('input[name=group]:checked').map(function() {
			return $(this).next().children('span.groupnm').text();
		}).get().join(',');

		if(checkedList.length <1){
			return;
		}
		$('#group').val(checkedList);
		$('#page').val(1);
		$('#historyForm').submit();
	});



	/* 오타교정 클릭 시 */
	$('.spc').on('click', 'a', function() {
		$('#autospc').val($(this).attr('data-auto'));
		$('#kwd').val($(this).text());
		$('#page').val(1);
		$('#detail').val(false);
		$('#historyForm').submit();
	});

	/* 검색어 추천, 인기검색어, 최근검색어, 오타교정어, 내가찾은검색어 클릭 시 */
	$('.kre, #rankings, #recent, #suggestions, .issue').on('click', 'a', function() {
		$('#kwd').val($(this).text());
		$('#page').val(1);
		$('#detail').val(false);
		$('#tr_code').val($(this).parents('[data-trcode]').attr('data-trcode'));
		$('#sort').val('d');
		$('#date').val('');
		$('#fields').val('');
		$('#group').val('');
		$('#historyForm').submit();
	});

	/* 카테고리 탭 클릭시 */
	$('.gnb').on('click', 'li.ctgr a', function() {
		var target = $(this).attr('data-target');
		var trcode = $(this).attr('data-trcode');
		var kwd = $('#kwd').val();
		if(kwd.indexOf('::') > 0) kwd = kwd.substring(0,kwd.indexOf('::'));
		$('#kwd').val(kwd);
		$('#category').val(target);
		$('#page').val('1');
		$('#group').val('');
		$('#sort').val('d');
		$('#tr_code').val(trcode);
		$('#historyForm').submit();
	});

	/* 필터(검색옵션) */
	$('header .search-option-inner').on('click', '.more-menu li', function() {
		if($(this).children('div').length < 1) {
			var param = $(this).parent().parent().attr('data-param');
			var dataCd = $(this).children('a').attr('data-cd');
			$('#'+param).val(dataCd);
			$('#tr_code').val($(this).children('a').attr('data-trcode'));
//			$('#startDate').val('');
//			$('#endDate').val('');
			if(param=="sort"){
				var sortObj = $(this).find('a').find("[data-cd='"+dataCd+"']");
				$('#sortNm').val(sortObj.text());
			}
			$('#historyForm').submit();
		}
	}).on('click', '.more-layer[data-param=date] button', function() {
		$('#date').val($(this).parent().attr('data-cd'));
		$('#tr_code').val($(this).parent().attr('data-trcode'));
		$('#startDate').val($('#sdate').val());
		$('#endDate').val($('#edate').val());
		$('#historyForm').submit();
	}).on('click', '.more-layer[data-param=detail] button, .detail .init', function() {
		var cls = $(this).attr('class');
		var kwd = $(this).parent().parent().children().children('#detailkwd').val().trim();
		var basickwdObj = $(this).parent().siblings('dl').find('#s1');
		var exactkwdObj = $(this).parent().siblings('dl').find('#s2');
		var inkwdObj = $(this).parent().siblings('dl').find('#s3');
		var notkwdObj = $(this).parent().siblings('dl').find('#s4');

		if(cls.indexOf('srch') > -1) {
			$('#kwd').val(kwd);
			if(exactkwdObj.val().trim() == '' && inkwdObj.val().trim() == '' && notkwdObj.val().trim() == '') {
				$('#detail').val(false);
			}else {
				$('#detail').val(true);
				$('#basickwd').val(basickwdObj.val().trim());
				$('#exactkwd').val(exactkwdObj.val().trim());
				$('#inkwd').val(inkwdObj.val().trim());
				$('#notkwd').val(notkwdObj.val().trim());
			}

			$('#page').val('1');
			$('#group').val('');
			$('header #search_re').prop('checked', false);
			$('#sort').val('d');
			$('#date').val('');
			$('#fields').val('');
			$('#tr_code').val($(this).parent().parent().parent().attr('data-trcode'));
			$('#historyForm').submit();
		}else if (cls.indexOf('exit') > -1) {
			obj = $('.more-layer[data-param=detail]');
			obj.hide().end();
			obj.prev().children('span').removeClass('down').addClass('up');
		}else if (cls == 'init') {
			$('#detailkwd').text('');
			basickwdObj.val('');
			exactkwdObj.val('');
			inkwdObj.val('');
			notkwdObj.val('');
		}
	});

	/* 정렬 클릭시 */
	$('section#column-left .result-sort, #container .result-sort').on('click', 'a', function() {
		$('#sort').val($(this).attr('data-sort'));
		$('#srch_opt').val('n');
		$('#page').val('1');
		$('#historyForm').submit();
	});

	/* 검색 결과 영역 이벤트 */
	$('.lst-type-wrap').on('click', '.more > a', function() {
		// 결과 더보기 - 통합검색
		var target = $(this).attr('data-target');
		var trcode = $(this).attr('data-trcode');

			$('#category').val(target);
			$('#page').val(1);
			$('#tr_code').val(trcode);
			$('#historyForm').submit();
	});

	$('body').on('click', function(event) {
		if($(event.target).parents('.more-btn').length === 0) {
			if($('.more-btn .more-layer').is(':visible')) {
				$('.more-btn .more-layer').toggle();
			}
		}

		if($(event.target).parents('.seoul-common-gnb').length === 0) {
			if($('.seoul-gnb-menu-section').is(':visible')) {
				$('.seoul-gnb-menu-section').toggle();
			}
		}

		if (
			  ($(event.target).attr('class') == "ui-icon ui-icon-circle-triangle-e")
			  || ($(event.target).attr('class') == "ui-icon ui-icon-circle-triangle-w") || ($(event.target).attr('class') == "ui-datepicker-title")
			  || ($(event.target).attr('class') == "ui-datepicker-month") || ($(event.target).attr('class') == "ui-datepicker-year")
			  || ($(event.target).attr('class') == "ui-datepicker-prev ui-corner-all ui-state-hover ui-datepicker-prev-hover")
			  || ($(event.target).attr('class') == "ui-datepicker-next ui-corner-all ui-state-hover ui-datepicker-next-hover")
			  || ($(event.target).attr('class') == " ui-datepicker-week-end ui-datepicker-other-month ui-datepicker-unselectable ui-state-disabled")
			  || ($(event.target).attr('class') == " ui-datepicker-other-month ui-datepicker-unselectable ui-state-disabled")
			  || ($(event.target).attr('class') == "ui-datepicker-title")
			  || ($(event.target).attr('class') == "ui-datepicker-header ui-widget-header ui-helper-clearfix ui-corner-all")
			  || ($(event.target).attr('scope') == "col") || ($(event.target).attr('title') == "Sunday") || ($(event.target).attr('title') == "Monday")
			  || ($(event.target).attr('title') == "Tuesday") || ($(event.target).attr('title') == "Wednesday") || ($(event.target).attr('title') == "Thursday")
			  || ($(event.target).attr('title') == "Friday") || ($(event.target).attr('title') == "Saturday")
			  || ($(event.target).attr('class') == "btnsmt chk")
			  ) {
			  return;
		  }

		if($(event.target).parents('.search-option-menu').length === 0) {
			if($('.search-option-menu .more-layer').is(':visible')) {
				$('.search-option-menu .more-layer').css('display','none');
				$('.search-option-menu a span').attr('class', 'ico-set up');
			}
		}

		if($(event.target).parents('.sns-btn').length === 0) {
			if($('.sns-btn .more-layer').is(':visible')) {
				$('.sns-btn .more-layer').toggle();
			}

			if($('.sns-btn .urlcp-alrt').is(':visible')) {
				$('.sns-btn .urlcp-alrt').toggle();
			}
		}

		if($(event.target).parents('.auto-search').length === 0) {
			if($('.auto-search').is(':visible')) {
				$('.auto-search').toggle();
			}
		}
	});

	/* 통합게시판 첨부파일 미리보기, 다운로드 링크 */
	$('.news-total-file, .web-total-file').on('click', '.view', function() {
		var obj = $(this).parent().parent();
		var srvc_id = obj.siblings('[name=srvc_id]').val();
		var upper_no = obj.siblings('[name=upper_no]').val();
		var file_ty = obj.siblings('[name=file_ty]').val();
		var file_no = obj.siblings('[name=file_no]').val();
		var bbs_no = obj.siblings('[name=bbs_no]').val();
		var ntt_no = obj.siblings('[name=ntt_no]').val();
		file_preview(srvc_id, upper_no, file_ty, file_no, bbs_no, ntt_no);

	}).on('click', '.down', function() {
		location.href = $(this).parent().parent().siblings('[name=downlink]').val();
	});

});
