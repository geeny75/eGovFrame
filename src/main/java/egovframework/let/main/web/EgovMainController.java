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

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.net.URLEncoder;

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
 /*   

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.io.BufferedReader;
import java.io.IOException;
*/
    @RequestMapping(value="/getCovid19InfStateJson.do")
    public String getCovid19InfStateJson(String[] args) {
        StringBuilder urlBuilder = new StringBuilder("http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19InfStateJson"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=서비스키"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + URLEncoder.encode("-", "UTF-8")); /*공공데이터포털에서 받은 인증키*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("startCreateDt","UTF-8") + "=" + URLEncoder.encode("20200310", "UTF-8")); /*검색할 생성일 범위의 시작*/
        urlBuilder.append("&" + URLEncoder.encode("endCreateDt","UTF-8") + "=" + URLEncoder.encode("20200315", "UTF-8")); /*검색할 생성일 범위의 종료*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
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
        System.out.println(sb.toString());
        
        return "";
    } 
}