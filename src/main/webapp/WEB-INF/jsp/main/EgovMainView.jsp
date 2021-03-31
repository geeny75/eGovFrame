<%--
  Class Name : EgovMainView.jsp
  Description : 메인화면
  Modification Information

      수정일         수정자                   수정내용
    -------    --------    ---------------------------
     2011.08.31   JJY       경량환경 버전 생성

    author   : 실행환경개발팀 JJY
    since    : 2011.08.31
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import ="egovframework.com.cmm.LoginVO" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!--※ proj4.js 파일경로를 찾지 못함  -->
<!--  <script type="text/javascript" src="<c:url value="/js/proj4.js" />" ></script>  -->
<script src="http://code.jquery.com/jquery-latest.js"></script> 
<script src="http://code.jquery.com/jquery-latest.min.js"></script> 
<meta http-equiv="content-language" content="ko">
<title>eGovFrame 포털사이트</title>
<link href="<c:url value='/'/>css/default.css" rel="stylesheet" type="text/css" >

</head>
<body>

<script type="text/javascript">
	/*코로나19 현황 공공데이터 API 호출(크로스도메인으로 인한 jsonp 이용)*/
	function fCovid19InfStateJson(){  
		var todayCnt = "-";
		var totCnt = "-";
		var url =  "/pst_webapp" + "/cmm/main/getCovid19InfStateJson.do"	;
		console.log( "Start - fCovid19InfStateJson (jsp) \n"  + url);
		$.ajax({
			url: url
			,type:"GET" 
			,headers: {
				    'Authorization': '(redacted)',
				    'Access-Control-Allow-Origin': '*' 
			}
		//	,dataType:"jsonp"											// 크로스도메인으로 인한 jsonp 이용, 검색결과형식 JSON 
			,dataType:"json"											// 크로스도메인으로 인한 jsonp 이용, 검색결과형식 JSON 
			,crossDomain:true
			,contentType: 'application/json;charset=UTF-8' 
			//,contentType: 'json' 
			,success:function(jsonStr){		
				//alert('success');// jsonStr :   검색 결과 JSON 데이터		
				//alert(jsonStr);
				const obj = JSON.parse(jsonStr)
				console.log(" \n==========결과 =========(In Jsp)===json :"+ jsonStr);
				console.log(" \n== obj  :"+ obj);
				todayCnt = obj.todayCnt;
				totCnt = obj.totCnt;
				  
			  /*
				var errCode = jsonStr.results.common.errorCode;
				var errDesc = jsonStr.results.common.errorMessage;
				if(errCode != "0"){ 
					alert(errCode+"="+errDesc);
				}else{
					if(jsonStr!= null){						// 결과 JSON 데이터 파싱 및 출력
						$(jsonStr.results).each(function(){
							
						}
					}
				} */
			}
			,error: function(xhr,status, error){
				//alert("에러발생");										// AJAX 호출 에러
				console.log("에러발생 \n status : "+status + "\n error : " +  error);	
			},
		    complete: function(data) {
		     // alert('complete')
		     console.log("complete \n"+data +"\n------------------");
		    }
		    
		}); 
		//alert(document.getElementById("todayCnt").innerHTML );  
		
		document.getElementById("todayCnt").innerHTML = todayCnt;
		//document.getElementById("domesticCnt").innerHTML = 1;
		//document.getElementById("overseasCnt").innerHTML = 1; 
		
		console.log( "End (jsp)" );
	}
	/*  공공API를 이용한  좌표 얻기*/
	function fGetCoordinate(){ 
		// AJAX 좌표 검색 요청
		$.ajax({
			url:"https://www.juso.go.kr/addrlink/addrCoordApiJsonp.do"	// 좌표검색 OPEN API URL
			,type:"post"
			,data:$("#form").serialize() 								// 요청 변수 설정
			,dataType:"jsonp"											// 크로스도메인으로 인한 jsonp 이용, 검색결과형식 JSON 
			,crossDomain:true
			,success:function(jsonStr){									// jsonStr : 주소 검색 결과 JSON 데이터			
				$("#list").html("");									// 결과 출력 영역 초기화
				var errCode = jsonStr.results.common.errorCode;
				var errDesc = jsonStr.results.common.errorMessage;
				
				console.log(errCode + " : " + errDesc);
				
				if(errCode != "0"){ 
					alert(errCode+"="+errDesc);
				}else{
					if(jsonStr!= null){
						console.log("jsonStr : "+ jsonStr);
						makeListJson(jsonStr);							// 결과 JSON 데이터 파싱 및 출력
					}
				}
			}
			,error: function(xhr,status, error){
				alert("에러발생");										// AJAX 호출 에러
			}
			 
		});
		 
	}

	function makeListJson(jsonStr){
		var htmlStr = "";
		htmlStr += "<table>";
		// jquery를 이용한 JSON 결과 데이터 파싱
		$(jsonStr.results.juso).each(function(){
			 
			var grs80 = proj4('+proj=tmerc +lat_0=38 +lon_0=127.5 +k=0.9996 +x_0=1000000 +y_0=2000000 +ellps=GRS80 +units=m +no_defs');
			var wgs84 = proj4('+proj=longlat +ellps=WGS84 +datum=WGS84 +no_defs');

			console.log(grs80);
			console.log(wgs84);
			// 네이버 맵에서 사용가능한 좌표. p[1] => lat, p[0] => lng 위도/경도 유형: WG84 ,  X/Y 좌표 유형: UTM-K (GRS80)
			var p = proj4(grs80, wgs84, [Number(this.entX), Number(this.entY)]); 

			document.getElementById("latitude").innerHTML = p[0];
			document.getElementById("latitude").innerHTML =  p[1];
			document.getElementById("tm").innerHTML= this.entX;
			document.getElementById("tmY").innerHTML= this.entY;
			 	
	 
			htmlStr += "<tr>";
			htmlStr += "<td>"+this.admCd+"</td>";
			htmlStr += "<td>"+this.rnMgtSn+"</td>";
			htmlStr += "<td>"+this.udrtYn+"</td>";
			htmlStr += "<td>"+this.buldMnnm+"</td>";
			htmlStr += "<td>"+this.buldSlno+"</td>";
			htmlStr += "<td>"+this.bdMgtSn+"</td>";
			htmlStr += "<td>"+this.entX+"</td>";
			htmlStr += "<td>"+this.entY+"</td>";
			htmlStr += "<td>"+this.bdNm+"</td>";
			htmlStr += "</tr>";
		});
		htmlStr += "</table>";
		// 결과 HTML을 FORM의 결과 출력 DIV에 삽입
		$("#list").html(htmlStr);
		console.log("\n======================결과==============\n" + htmlStr);
	}	

	
	window.onload = function(){  
		

		var roadAddr = "서울 금천구 가산디지털2로 179";
		document.getElementById("roadAddr").innerHTML = roadAddr;
		
		
	//	fGetCoordinate(); 
		
		fCovid19InfStateJson(); 
	} 
	
</script>
<noscript><p>자바스크립트를 지원하지 않는 브라우저에서는 일부 기능을 사용하실 수 없습니다.</p></noscript>
<!-- login status start -->
<div id="login_area"><c:import url="/EgovPageLink.do?link=main/inc/EgovIncTborder" /></div>
<!-- //login status end -->
<!-- wrap start -->
<div id="wrap">
	<!-- header start -->
	<div id="header"><c:import url="/sym/mms/EgovMainMenuHead.do?flag=MAIN" /></div>
	<!-- //header end -->
	<!-- 타이틀이미지, 로그인 시작 -->
	<div id="titlewrap">
		<div class="main_img"><img src="<c:url value='/'/>images/header/img_portal_title.gif" width="719" height="94" alt="" /></div>
		<div class="main_login">
    		<%
               LoginVO loginVO = (LoginVO)session.getAttribute("LoginVO");
               if(loginVO != null){
            %>
    		<ul>
    		    <li><h3>[로그인정보 제공(예시)]</h3></li>
    		    <li><%= loginVO.getName()%>님 로그인하셨습니다.</li>
    		    <li>로그인 :2011-08-30 12:45 (예)</li>
    		    <li>받은 쪽지 : 3건 (예)</li>
    		</ul>
    		<%  } else { %>
            <!-- 메인화면 로그인위치를 사용하는 경우 -->
			<ul>
				<li>
					<input disabled="disabled" type="text" class="input_style" maxlength="25" title="아이디를 입력하세요." id="user_id" name="user_id" />
				</li>
				<li>
					<input disabled="disabled" type="password" class="input_style" maxlength="25" title="비밀번호를 입력하세요." id="user_password" name="user_password" />
				</li>
			</ul>
			<ul class="btn_area">
				<li><input disabled="disabled" type="checkbox" id="idsave"><label for="idsave">아이디 저장</label></li>
				<li><a href="<c:url value='/uat/uia/egovLoginUsr.do'/>"><img alt="로그인" src="<c:url value='/'/>images/header/btn_login.gif" /></a></li>
			</ul>
			<div class="find_idpw">[메인화면 로그인위치 예시]</div>
	     <%  } %>
		</div>
	</div>
	<!-- //타이틀이미지, 로그인 끝 -->
	<div id="bodywrap">
		<div id="leftcontent_wrap">
			<!-- 한번에 신청하는 민원 시작 -->
			<div class="leftcontent01">
				<div class="leftcontent01_title"><img src="<c:url value='/'/>images/header/img_leftcontent_title01.gif" width="228" height="31" alt="한번에 신청하는 민원" /></div>
				<div class="btn_detail01"><a href="#LINK" onclick="javascript:goMenuPage('2000000'); return false;" ><img src="<c:url value='/'/>images/header/btn_detailview.gif" width="71" height="17" alt="자세히 보기" title="자세히 보기 링크를 통해 샘플화면으로 이동합니다.(민원)" /></a></div>
				<div class="leftcontent01_btn">
					<ul>
						<li><a href="#LINK"><img src="<c:url value='/'/>images/header/btn_leftcontent01.gif" width="70" height="87" alt="기술지원 필요 시 유지보수 민원" title="자세히 보기 링크를 통해 샘플화면으로 이동합니다.(민원)" /></a></li>
						<li><a href="#LINK"><img src="<c:url value='/'/>images/header/btn_leftcontent02.gif" width="67" height="87" alt="구매제품  A/S민원" title="자세히 보기 링크를 통해 샘플화면으로 이동합니다.(민원)" /></a></li>
					</ul>
				</div>
			</div>
			<!-- //한번에 신청하는 민원 끝 -->
			<div class="leftcontent02">
				<div class="leftcontent02_title"><A HREF="#LINK"><img src="<c:url value='/'/>images/header/img_leftcontent_title02.gif" width="210" height="71" alt="홍보물 정보 보기" title="기타자료에 대한 샘플링크 위치 표시입니다.(이동, 기능 없음)" /></A></div>
				<div class="btn_detail02"><a href="#LINK"><img src="<c:url value='/'/>images/header/btn_detailview.gif" width="71" height="17" alt="자세히 보기" title="기타자료에 대한 샘플링크 위치 표시입니다.(이동, 기능 없음)" /></a></div>
			</div>
			<div class="leftcontent03">
				<div class="leftcontent03_title">
				    <a href="<c:url value='/uss/olp/qnn/EgovQustnrRespondInfoManageList.do'/>">
				    <img src="<c:url value='/'/>images/header/img_leftcontent_title03.gif" width="228" height="44" alt="포털설문참여" />
				    </a>
				</div>
				<c:forEach var="result" items="${qriList}" varStatus="status">
				<div class="vote02">
                    <ul>
                        <li><h3>${result.qestnrSj}</h3>
                        <ul>
                            <li>
                                <a href="<c:url value='/uss/olp/qnn/EgovQustnrRespondInfoManageList.do'/>">
                                <img alt="참여하기" src="<c:url value='/'/>images/header/btn_vote.gif" />
                                </a>
                            </li>
                        </ul>
                        </li>
                     </ul>
				</div>
				</c:forEach>
			</div>
		</div>
		<!-- 중간 영역 시작 -->
		<div id="middlecontent_wrap">
			<div id="news">
				<ul>
					<li><img alt="공지사항" src="<c:url value='/'/>images/header/tab01_on.gif" /></li>
					<li><img alt="묻고답하기" src="<c:url value='/'/>images/header/tab02_off.gif" /></li>
					<li class="li_line" ><span class="btn_more_board"><a href="<c:url value='/cop/bbs/selectBoardList.do?bbsId=BBSMSTR_AAAAAAAAAAAA'/>"><img src="<c:url value='/'/>images/header/bg_more.gif" alt="게시물 더보기"/></a></span></li>
				</ul>
			</div>
			<div id="content01" style="display: visible; height:130px">
				<ul>
				    <c:forEach var="result" items="${notiList}" varStatus="status">
				        <li>
				            <div class="boardtext">
				            <a href="<c:url value='/cop/bbs/selectBoardList.do?bbsId=BBSMSTR_AAAAAAAAAAAA'/>">
				            <c:if test="${result.replyLc!=0}">
                                <c:forEach begin="0" end="${result.replyLc}" step="1">
                                    &nbsp;
                                </c:forEach>
                                <img src="<c:url value='/images/reply_arrow.gif'/>" alt="reply arrow"/>
                            </c:if>
                            <c:choose>
                                <c:when test="${result.isExpired=='Y' || result.useAt == 'N'}">
                                    <c:out value="${result.nttSj}" />
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${result.nttSj}" />
                                </c:otherwise>
                            </c:choose>
				            </a></div>
				            <div class="date"><c:out value="${result.frstRegisterPnttm}"/></div></li>
				    </c:forEach>
				</ul>
			</div>
			<div id="content02" style="display:none;">
				<ul>
					<li><div class="boardtext"><a href="#LINK">7월보안점검행사안내입니다(행사장소:표준프레임워크 1층 로비) </a></div><div class="date">2011-07-29</div></li>
				</ul>
			</div>
			<div id="faq_div">
				<div class="faq_img1">
				    <img alt="FAQ목록" src="<c:url value='/'/>images/header/img_middlecontent_title02.gif" />
				    <span class="btn_more_board">
				    <a href="<c:url value='/uss/olh/faq/FaqListInqire.do' />">
				        <img src="<c:url value='/'/>images/header/bg_more.gif" alt="FAQ더보기"/>
				    </a>
				    </span>
				</div>
				<div class="faq_img2"><img alt="프레임워크 경량화 서비스에 대해 자주 사용하는 질문 등에 대한 일반적인 답변들을 확인할 수 있습니다." src="<c:url value='/'/>images/header/img_middlecontent_subtitle.gif" /></div>
				<c:forEach var="result" items="${faqList}" varStatus="status">
				<div class="faq_list">
					<ul>
						<li class="q"><a href="<c:url value='/uss/olh/faq/FaqListInqire.do' />"><c:out value="${result.qestnSj}"/></a></li>
						<li class="a"><c:out value="${fn:substring(fn:escapeXml(result.answerCn), 0, 70)}" /></li>
					</ul>
				</div>
				</c:forEach>
			</div>
		</div>
		<!-- //중간 영역 끝 -->
		<div id="rightcontent_wrap">
			<div id="download_div">
				<div class="download_img">
				    <a href="<c:url value='/cop/bbs/selectBoardList.do?bbsId=BBSMSTR_BBBBBBBBBBBB'/>">
				    <img alt="최신게시물 목록" src="<c:url value='/'/>images/header/img_rightcontent_title01.gif" />
				    </a>
				</div>
				<ol>
				    <c:set var="index" value="1"/>
				    <c:forEach var="result" items="${bbsList}" varStatus="status">
				    <li><img src="<c:url value='/'/>images/header/num0${index}.gif" alt="" />
				        <a href="<c:url value='/cop/bbs/selectBoardList.do?bbsId=BBSMSTR_BBBBBBBBBBBB'/>">
				        <c:out value="${fn:substring(fn:escapeXml(result.nttSj), 0, 18)}" />
				        </a>
				    </li>
				    <c:set var="index" value="${index+1}"/>
                    </c:forEach>
                    <c:if test="${fn:length(bbsList) == 0}" >
                        <li>최신 게시물이 없습니다.</li>
                    </c:if>
				</ol>
			</div>
			<div id="banner_div">
			    <div class="bnpadtop"> 
				    <H4></H4><a href="http://ncov.mohw.go.kr/" target="_blank" >코로나19 현황</a></H4>
				    <UL> 
				    	<li>일일 : <p id="todayCnt" name ="todayCnt"></p></li> 
				    </UL>
			    </div>
				<div class="bnpadding">
				 	<H4></H4><a href="https://www.juso.go.kr/addrlink/main.do?cPath=99JM" target="_blank"  title="도로명주소 개발자 센터">현재위치</a></H4>
				    <UL> 
				    	<li>도로명주소 : <span id="roadAddr" name="roadAddr" ></span></li>
				    	<li>위경도 좌표 : <span id="tmX" name="tmX" ></span>,<span id="tmY" name="tmY" ></span></li>
				    	<li>TM 좌표 : <span id="latitude" name="latitude" ></span>, <span id="longitude" name=""longitude ></span></li>
				    </UL> 
				</div>
				<div class="bnpadding"><a href="http://www.nia.or.kr/" target="_blank"><img src="<c:url value='/'/>images/header/banner_nia.png" alt="한국정보화진흥원" /></a></div>
				<div class="bnpadding"><a href="http://www.egovframe.go.kr/" target="_blank"><img src="<c:url value='/'/>images/header/banner_egovportal.gif" alt="전자정부표준프레임워크 포털" /></a></div>
				<div class="bnpadding"><a href="http://open.egovframe.go.kr/" target="_blank"><img src="<c:url value='/'/>images/header/banner_opencmm.gif" alt="오픈커뮤니티" /></a></div>
			</div>
			
		</div>
	</div>
	
	<form name="form" id="form" method="post">
		<input type="text" name="confmKey" value="devU01TX0FVVEgyMDIxMDMyOTEzMjgxNjExMDk3ODQ="/><!-- 요청 변수 설정 (승인키) -->
		<input type="text" name="resultType" value="json"/> <!-- 요청 변수 설정 (검색결과형식 설정, json) --> 
		<input type="text" name="admCd"   value="1154510100"/> <!-- 요청 변수 설정 (행정구역코드) 금천구     -->  	
		<input type="text" name="rnMgtSn" value="115453117002"/><!-- 요청 변수 설정 (도로명코드) --> 
		<input type="text" name="udrtYn" value="0"/> <!-- 요청 변수 설정 (지하여부) -->
		<input type="text" name="buldMnnm" value="179"/><!-- 요청 변수 설정 (건물본번) --> 
		<input type="text" name="buldSlno" value="0"/><!-- 요청 변수 설정 (건물부번) -->
		<input type="button" onClick="fGetCoordinate();" value="좌표검색하기"/>
		<div id="list" ></div><!-- 검색 결과 리스트 출력 영역 -->	
	</form>
	<!-- footer 시작 -->
	<div id="footer"><c:import url="/EgovPageLink.do?link=main/inc/EgovIncFooter" /></div>
	<!-- //footer 끝 -->
</div>
<!-- //wrap end -->
</body>
</html>
<%

%>