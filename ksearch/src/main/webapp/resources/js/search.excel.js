/*******************************************************
* 프로그램명 	 : search.excel.js
* 설명        : 통합검색 환경에서 엑셀파일 다운로드
* 모듈    : -
*    엑셀다운로드		doit()

*******************************************************/

function doit(filename,dataid) {
	var elt = document.getElementById(dataid);
	var wb = XLSX.utils.table_to_book(elt, {sheet:"sheet"});
	return XLSX.writeFile(wb, ( filename+'.xlsx' ));

}
