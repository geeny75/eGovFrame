package egovframework.let.main.web;

import java.util.Map;

import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.cmm.LoginVO;
import egovframework.let.cop.bbs.service.BoardVO;
import egovframework.let.cop.bbs.service.EgovBBSManageService;
import egovframework.let.sym.mnu.mpm.service.EgovMenuManageService;
import egovframework.let.sym.mnu.mpm.service.MenuManageVO;
import egovframework.let.uss.olh.faq.service.EgovFaqManageService;
import egovframework.let.uss.olh.faq.service.FaqManageDefaultVO;
import egovframework.let.uss.olp.qri.service.EgovQustnrRespondInfoService;

import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

/*코로나19현황 공공API 호출 2021.3.30 (Tue)*/
import java.net.URLEncoder;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.IOException;
import java.util.Calendar;
import java.text.SimpleDateFormat;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/* XML Parser*/

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.json.simple.JSONObject;
/**
 * 템플릿 메인 페이지 컨트롤러 클래스(Sample 소스)
 * @author 실행환경 개발팀 JJY
 * @since 2011.08.31
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2011.08.31  JJY            최초 생성
 *
 * </pre>
 */
@Controller@SessionAttributes(types = ComDefaultVO.class)
public class EgovMainController {

	/**
	 * EgovBBSManageService
	 */
	@Resource(name = "EgovBBSManageService")
    private EgovBBSManageService bbsMngService;

	/** EgovMenuManageService */
	@Resource(name = "meunManageService")
    private EgovMenuManageService menuManageService;

	/** FaqManageService */
	@Resource(name = "FaqManageService")
    private EgovFaqManageService faqManageService;

	/** egovQustnrRespondInfoService */
	@Resource(name = "egovQustnrRespondInfoService")
	private EgovQustnrRespondInfoService egovQustnrRespondInfoService;

	/**
	 * 메인 페이지에서 각 업무 화면으로 연계하는 기능을 제공한다.
	 *
	 * @param request
	 * @param commandMap
	 * @exception Exception Exception
	 */
	@RequestMapping(value = "/cmm/forwardPage.do")
	public String forwardPageWithMenuNo(HttpServletRequest request, @RequestParam Map<String, Object> commandMap)
	  throws Exception{
		return "";
	}

	/**
	 * 템플릿 메인 페이지 조회
	 * @return 메인페이지 정보 Map [key : 항목명]
	 *
	 * @param request
	 * @param model
	 * @exception Exception Exception
	 */
	@RequestMapping(value = "/cmm/main/mainPage.do")
	public String getMgtMainPage(HttpServletRequest request, ModelMap model)
	  throws Exception{

		// 공지사항 메인 컨텐츠 조회 시작 ---------------------------------
		BoardVO boardVO = new BoardVO();
		boardVO.setPageUnit(5);
		boardVO.setPageSize(10);
		boardVO.setBbsId("BBSMSTR_AAAAAAAAAAAA");

		PaginationInfo paginationInfo = new PaginationInfo();

		paginationInfo.setCurrentPageNo(boardVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(boardVO.getPageUnit());
		paginationInfo.setPageSize(boardVO.getPageSize());

		boardVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		boardVO.setLastIndex(paginationInfo.getLastRecordIndex());
		boardVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		Map<String, Object> map = bbsMngService.selectBoardArticles(boardVO, "BBSA02");
		model.addAttribute("notiList", map.get("resultList"));


		// 공지사항 메인컨텐츠 조회 끝 -----------------------------------

		// 자유게시판 메인 컨텐츠 조회 시작 ---------------------------------
		boardVO.setPageUnit(9);
		boardVO.setPageSize(10);
		boardVO.setBbsId("BBSMSTR_BBBBBBBBBBBB");

		paginationInfo.setCurrentPageNo(boardVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(boardVO.getPageUnit());
		paginationInfo.setPageSize(boardVO.getPageSize());

		boardVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		boardVO.setLastIndex(paginationInfo.getLastRecordIndex());
		boardVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		model.addAttribute("bbsList", bbsMngService.selectBoardArticles(boardVO, "BBSA02").get("resultList"));

		// 자유게시판 메인컨텐츠 조회 끝 -----------------------------------



		// FAQ 메인 컨텐츠 조회 시작 ---------------------------------
		/** EgovPropertyService.SiteList */
		FaqManageDefaultVO searchVO = new FaqManageDefaultVO();
		searchVO.setPageUnit(3);
    	searchVO.setPageSize(10);

    	/** pageing */
    	paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

        model.addAttribute("faqList", faqManageService.selectFaqList(searchVO));

		// FAQ 메인 컨텐츠 조회 끝 -----------------------------------

        // 설문참여 메인 컨텐츠 조회 시작 -----------------------------------
        ComDefaultVO qVO = new ComDefaultVO();
    	qVO.setPageUnit(1);
    	qVO.setPageSize(10);

    	/** pageing */
		paginationInfo.setCurrentPageNo(qVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(qVO.getPageUnit());
		paginationInfo.setPageSize(qVO.getPageSize());

		qVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		qVO.setLastIndex(paginationInfo.getLastRecordIndex());
		qVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

        model.addAttribute("qriList", egovQustnrRespondInfoService.selectQustnrRespondInfoManageList(qVO));

     // 설문참여 메인 컨텐츠 조회 끝 -----------------------------------


		return "main/EgovMainView";
	}

	/**
     * Head메뉴를 조회한다.
     * @param menuManageVO MenuManageVO
     * @return 출력페이지정보 "main_headG", "main_head"
     * @exception Exception
     */
    @RequestMapping(value="/sym/mms/EgovMainMenuHead.do")
    public String selectMainMenuHead(
    		@ModelAttribute("menuManageVO") MenuManageVO menuManageVO,
    		@RequestParam(value="flag", required=false) String flag,
    		ModelMap model)
            throws Exception {

    	LoginVO user =
    		EgovUserDetailsHelper.isAuthenticated()? (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser():null;
    	if(EgovUserDetailsHelper.isAuthenticated() && user!=null){
    		menuManageVO.setTmp_Id(user.getId());
        	menuManageVO.setTmp_Password(user.getPassword());
        	menuManageVO.setTmp_UserSe(user.getUserSe());
        	menuManageVO.setTmp_Name(user.getName());
        	menuManageVO.setTmp_Email(user.getEmail());
        	menuManageVO.setTmp_OrgnztId(user.getOrgnztId());
        	menuManageVO.setTmp_UniqId(user.getUniqId());
    		model.addAttribute("list_headmenu", menuManageService.selectMainMenuHead(menuManageVO));
    		model.addAttribute("list_menulist", menuManageService.selectMainMenuLeft(menuManageVO));
    	}else{
    		menuManageVO.setAuthorCode("ROLE_ANONYMOUS");
    		model.addAttribute("list_headmenu", menuManageService.selectMainMenuHeadByAuthor(menuManageVO));
    		model.addAttribute("list_menulist", menuManageService.selectMainMenuLeftByAuthor(menuManageVO));
    	}

    	if(flag==null){
    		return "main/inc/EgovIncSubHeader"; // 업무화면의 상단메뉴 화면
    	}else if(flag.equals("MAIN")){
    		return "main/inc/EgovIncHeader"; // 메인화면의 상단메뉴 화면
    	}else{
    		return "main/inc/EgovIncSubHeader"; // 업무화면의 상단메뉴 화면
    	}
    }


    /**
     * 좌측메뉴를 조회한다.
     * @param menuManageVO MenuManageVO
     * @param vStartP      String
     * @return 출력페이지정보 "main_left"
     * @exception Exception
     */
    @RequestMapping(value="/sym/mms/EgovMainMenuLeft.do")
    public String selectMainMenuLeft(
    		ModelMap model)
            throws Exception {

    	//LoginVO user = EgovUserDetailsHelper.isAuthenticated()? (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser():null;

    	//LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
    	if(EgovUserDetailsHelper.isAuthenticated()){
    		//인증된 경우 처리할 사항 추가 ...
    		model.addAttribute("lastLogoutDateTime", "로그아웃 타임: 2011-11-10 11:30");
    		//최근 로그아웃 시간 등에 대한 확보 후 메인 컨텐츠로 활용
    	}

      	return "main/inc/EgovIncLeftmenu";
    }
    /**
     * 보건복지부_코로나19 감염_현황을 조회한다.
     *   (출처 : https://www.data.go.kr/data/15043376/openapi.do)
     * @param  
     * @param  
     * @return  
     * @exception 
     */
    @ResponseBody
    @RequestMapping(value="/cmm/main/getCovid19InfStateJson.do", method = RequestMethod.GET)
   // 
    public   void getCovid19InfStateJson(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException  {
    	System.out.println("Start getCovid19InfStateJson ( In Java) ");
    	String serviceKeyEn = "zZmvp8orLEvNI31rbrzuGVtdLvVZ3juLI%2BcH7%2BXyjpCuIqJeSzwN2gWYsf6KJ5AZVUxnjLN2sM24VcNfUsbwow%3D%3D"; 
		String serviceKey = "zZmvp8orLEvNI31rbrzuGVtdLvVZ3juLI+cH7+XyjpCuIqJeSzwN2gWYsf6KJ5AZVUxnjLN2sM24VcNfUsbwow==";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar c1 = Calendar.getInstance();
         
        String strToday = sdf.format(c1.getTime()); 

        c1.add(Calendar.DATE, -1); // 오늘날짜로부터 -1 

        String yesterDay = sdf.format(c1.getTime()); // String으로 저장
        
        String todayCnt = "-";

        StringBuilder urlBuilder = new StringBuilder("http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19InfStateJson"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=" +  serviceKeyEn) ; 
        urlBuilder.append("&" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + URLEncoder.encode("-", "UTF-8")); /*공공데이터포털에서 받은 인증키*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8")     + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8")  + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("startCreateDt","UTF-8") + "=" + URLEncoder.encode(yesterDay, "UTF-8")); /*검색할 생성일 범위의 시작*/
        urlBuilder.append("&" + URLEncoder.encode("endCreateDt","UTF-8") + "=" + URLEncoder.encode(strToday, "UTF-8")); /*검색할 생성일 범위의 종료*/
        
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= HttpURLConnection.HTTP_OK  && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();  	 
        JSONObject jsonObj = new JSONObject();
        if( sb.length() > 0 ) {
        	jsonObj = fXMLParser(sb);
        	
        } 
		
      //  resultCode
       // resultMsg
        //DECIDE_CNT todayCnt
        model.addAttribute("result", jsonObj);
   //     model.addAttribute("domesticCnt", 1);
   //     model.addAttribute("overseasCnt", 1); 
        System.out.println("End (In Java)"); 
        return ;
    } 
    // tag값의 정보를 가져오는 메소드
   	private static String getTagValue(String tag, Element eElement) {
   	    NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
   	    Node nValue = (Node) nlList.item(0);
   	    if(nValue == null) 
   	        return null;
   	    return nValue.getNodeValue();
   	}
   	/* fXMLParser
   	 * XML 형식 문자열 Parsing
   	 * 출처 : https://shonm.tistory.com/126
   	 * */

   	public JSONObject fXMLParser(StringBuilder sb) { 
   		String todayCnt ="";
   		int decideCnt[] = {0, 0, 0};
   		System.out.println(" \n==========fXMLParser ====(In Java)=================\n"+  sb.toString()); 
   		
   		try{ 
   				// parsing할 url 지정(API 키 포함해서)
   				
   				DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
   				DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
   				Document doc = dBuilder.parse(new InputSource(new StringReader(sb.toString())));  //dBuilder.parse(sb.toString());
   				
   				NodeList resultCodeList    =  doc.getElementsByTagName("resultCode");
   				NodeList resultMsgList     =  doc.getElementsByTagName("resultMsg"); 

   				Node resultCodeNode      =  resultCodeList.item(0);//첫번째 element 얻기
   				Node resultMsgNode       =  resultMsgList.item(0);//첫번째 element 얻기
   				
   			    Node resultCodeText     =  resultCodeNode.getChildNodes().item(0);
   			    Node resultMsgText      =  resultMsgNode.getChildNodes().item(0);
   			    
   			    String resultCode = resultCodeText.getNodeValue(); 
			    String resultMsg = resultMsgText.getNodeValue(); 
			    
			    System.out.println("resultCode : " + resultCode + "\nresultMsg : " + resultMsg );
			    
			    if( "00".equals(resultCode) == true ) { 
	   			 	NodeList decideCntList     =  doc.getElementsByTagName("decideCnt");
	   			 	int len = decideCntList.getLength();
	   			 		
	   			 	 
	   			 	for( int ix =0 ; ix <len && ix < 3 ; ix++) {
	   			 
		   			 	Node decideCntNode       =  decideCntList.item(ix);//첫번째 element 얻기
		   				Node decideCntText      =  decideCntNode.getChildNodes().item(0);
		   			   //element의 text 얻기   			   
	   			    
		   				decideCnt[ix] = Integer.parseInt(decideCntText.getNodeValue());  
	   			 	}
	   			 	decideCnt[2] =   decideCnt[0] - decideCnt[1] ;
	   			 	System.out.println("decideCnt[0] : " + decideCnt[0] + "\ndecideCnt[1] : " + decideCnt[1] );
   			    	System.out.println("확진자 수  : " + decideCnt[2]); 
			    }
   			 
   				/* root tag 
   				Element eElement1  = doc.getDocumentElement();
   				eElement1.normalize();
   				System.out.println("Root element :" + eElement1.getNodeName());
   				//eElement1.getTagName()
   				String resultCode = eElement1.getAttribute("resultCode");
   				String resultMsg = eElement1.getAttribute("resultMsg");
   				
   				//String resultCode = getTagValue("resultCode",eElement1);
   				//String resultMsg = getTagValue("resultMsg",eElement1);
   				*/
   				
   				
   				// 파싱할 tag
   				/*
   				NodeList nList = doc.getElementsByTagName("item");
   				//System.out.println("파싱할 리스트 수 : "+ nList.getLength());
   				
   				for(int temp = 0; temp < nList.getLength(); temp++){
   					Node nNode = nList.item(temp);
   					if(nNode.getNodeType() == Node.ELEMENT_NODE){
   						
   						Element eElement = (Element) nNode;
   						System.out.println("######################");
   						//System.out.println(eElement.getTextContent());
   						todayCnt = getTagValue("decideCnt", eElement);
   						System.out.println("확진자 수  : " + todayCnt); 
   					}	// for end
   				}	// if end
   				
   			  */
   			
   		} catch (Exception e){	
   			e.printStackTrace();
   		}	// try~catch end
   		
   	 
		JSONObject jsonObj = new JSONObject(); 
		
		jsonObj.put("todayCnt",Integer.toString( decideCnt[2]) );
		jsonObj.put("totCnt",Integer.toString(decideCnt[0]));
		String data = jsonObj.toString();
		System.out.println("json data ==> " + data);
		
   		return jsonObj;
   	}	 
}