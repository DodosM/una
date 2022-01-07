/*******************************************************
* 프로그램명 	 : search.ksf.js
* 설명        : 통합검색 환경에서 KSF 통해 조회하는 공통 함수 - ajax 함수 사용
* 모듈    : -
*    자동완성		autocomplete();
*    인기검색어	popularkey()
*    추천검색어	related()
*    오타교정		getSpellchek()
*    최근검색어	recent()
*******************************************************/

/**
 * 화면 구동시 호출하는 함수정보
 * 사용되는 함수목록은 자동완성(), 오타교정(), 인기검색어() 기본 사용합니다.
 * - 해당 정보 호출하기전에 KSF 모듈에서 기능 사용을 적용하였는지 확인되어야 합니다.
 */
$(function(){

	//자동완성에 검색어 표시
	autorecent();

	//자동완성
	autocomplete();

	//연관검색어
	related();

	//인기검색어
	popularkey();

	//최근검색어(위젯)
	$( "#recent" ).recent();

	//related();

	//오타교정 건수체크하기
	var rsCount = '<c:out value="${total}" />';
	var kwd = '<c:out value="${params.kwd}" />';

	if(kwd.length > 0 && rsCount == 0){
		//getSpellchek();
	}

	//오류메시지출력
	/*
	var message = '<c:out value="${message}" />';
	if(message.length > 0){
		alert(message);
		return false;
	}
	*/

});

/**
* 통합검색어 인기검색어
* @ return str
**/
function popularkey(){
	var ajax = {
			type: "GET",
			url: "",
			data: { "domain_no" :0, "max_count":10},
			success: {},
			error: {}
	};
	ajax.url = 'ksf/ppk.do';
	ajax.success = function(data) {

		if(data.length > 0){
			//console.log("[initPPK] success");
			var ppkHTML = "";
			var metaClass="", activeClasss="", metaNm="", metaClassText="";
			ppkHTML = "<ul data-trcode=\"pword\" class=\"konan-rankings lst-keyword\">";
			var ppkValue;

			$.each(data, function( i, item) {
				i++;
				ppkValue = (item.ppk).replace(/</gi,"&lt;").replace(/>/gi,"&gt;");
				 metaClassText="";
				if(item.meta == "new") {
					metaClass = "new";
					metaNm = "신규";
					metaClassText = "new";
				}
				else if( Number(item.meta) == 0){
					metaClass = "equal";
					metaNm = "변동없음";
					metaClassText = "-";
				}
				else if( Number(item.meta) > 0) {
					metaClass = "up";
					metaNm = "상승";
				}
				else	 {
					metaClass = "down";
					metaNm = "하락";
				}

				activeClasss ="";
				if(i < 4){
					activeClasss = "active";
				}

			//	<li class="active"><span class="number">1</span><a href="javascript:void(0);">서울사랑상품권</a><span class="right-side"><span class="ico up"></span><span class="blind">상승</span></span></li>

				ppkHTML +=	"<li class='"+activeClasss+"'>";
				ppkHTML += 	"<span class='number'>"+i+"</span>";
				ppkHTML +=	"<a href=\"javascript:void(0);\">" + ppkValue + "</a>";
				ppkHTML += "<span class=\"right-side\"><span class ='ico "+metaClass+"'>"+metaClassText+"</span>";
				ppkHTML += "<span class=\"blind\">"+metaNm+"</span>";
				ppkHTML +=	"</span></li>\n";

			})
		}
			ppkHTML += "</ul>";
			$("#rankings").html(ppkHTML);
	};
	ajax.error = function(xhr, ajaxOptions) {
		console.log(ajaxOptions);
	};

	$.ajax(ajax);
}


function autorecent(){
	$('header input[name=kwd]').on("propertychange change keyup paste input",function() {
		$('header input[name=kwd]').autorecent();
	});
}

/**
* 통합검색어 자동완성
* ksf 표준소스 기준으로 ajax 호출하여 데이터를 보여준다.
* @ return str
**/
function autocomplete(){
	// 자동완성 위젯 초기화
	var flagAutoCompleteInitDone;

    //자동완성
    $('header input[name=kwd]')
    .each(function(){  // IE11 hack
        // first we handle 'input' event and prevent its further propagation
        // until our variable is initialized with setTimeout() handler call
        if ((navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') != -1) || navigator.userAgent.search('Microsoft Internet Explorer')) {
           this.addEventListener('input',
            function(ev){
                if( typeof flagAutoCompleteInitDone === 'undefined'){
                    ev.stopImmediatePropagation();
                }
            }, false);
        }
    }) // /IE11 hack
    .autocomplete({   source: 'ksf/akc.do'   });
}


/**
* 카테고리 매칭 함수(카운트).
* 카테고리 코드값을 넘겨주면, total 값을 리턴한다.
*
* @ return str
**/
function getSpellchek() {
	var kwd = $.trim($("input[name=kwd]").val());

	var ajax = {
			type: "GET",
			url: "",
			data: {},
			success: {},
			error: {}
	};
	ajax.url = 'ksf/spell.do';
	ajax.data = { term :kwd};
	ajax.success = function(data) {
		//console.log("[getSpellchek] success");
		if(data.length > 0){
			var rsData;

			rsData = '<strong>검색어 교정</strong>';
			data.forEach(function(item) {
				rsData += '<em><a href=\"javascript:getSpellKwd(\''+item+'\')\">'+item+'</a></em> ';
			});

			rsData += ' 으로 검색할까요?';
			$(".proofs_wrap").html(rsData);
			$(".proofs_wrap").css("display","block");
			}
	};
	ajax.error = function(xhr, ajaxOptions) {
		console.log(ajaxOptions);
	};

	$.ajax(ajax);
};

//검색어 교정을 통해 바로 검색활성화
function getSpellKwd(value){
	//dftSchKwd( value );
};


//연관검색어
function related(){
	var ajax = {
			type: "GET",
			url: "",
			data: { "domain_no" :0, "max_count":10, "term":"1"},
			success: {},
			error: {}
	};
	ajax.url = 'ksf/kre.do';
	ajax.success = function(data) {
		if(data.length < 1){
			$("#related").addClass("dn");
			return;
		}

		if(data.length > 0){
			$("#related").removeClass("dn");

			var html = "";
			var metaClass="", activeClasss="", metaNm="", metaClassText="";
			html = "<ul class=\"kre\" data-trcode=\"rword\">";
			var ppkValue;

			$.each(data, function( i, item) {
				html +=	" <li><a href=\"javascript:void(0);\">"+item+"</a></li>\n";
			});
			html += "</ul>";
		}

		$("#related dd").html(html);
	};
	ajax.error = function(xhr, ajaxOptions) {
		console.log(ajaxOptions);
	};

	$.ajax(ajax);
};
