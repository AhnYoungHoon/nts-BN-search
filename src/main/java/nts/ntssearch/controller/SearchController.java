package nts.ntssearch.controller;

import jakarta.servlet.http.HttpServletResponse;
import nts.ntssearch.domain.closedNum;
import nts.ntssearch.domain.presentNum;
import nts.ntssearch.service.SearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
public class SearchController {
    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/afterLogin/search")
    public String search(){
        return "afterLogin/search";
    }

    @GetMapping("/afterLogin/presentList")
    public String present(Model model){
        List<presentNum> presentList = searchService.findPN();
        model.addAttribute("presentNum", presentList);
        return "afterLogin/presentList";
    }

    @GetMapping("/afterLogin/closedList")
    public String closed(Model model){
        List<closedNum> closedList = searchService.findCN();
        model.addAttribute("closedNum", closedList);
        return "afterLogin/closedList";
    }

    public static void alert(HttpServletResponse response, String msg) {
        try {
            response.setContentType("text/html; charset=utf-8");
            PrintWriter w = response.getWriter();
            w.write("<script>alert('"+msg+"');</script>");
            w.flush();
            w.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

//    @PostMapping("http://api.odcloud.kr/api/nts-businessman/v1/validate?serviceKey=kMQVtVR8c3Ptw6aaSib1U4PkPeo6ADa4hhUDadtAiLbzfX%2FZsDuYYwGl5mbkvgzY8lGVGOlvuU6UBFibFmobOg%3D%3D")
    @PostMapping("/afterLogin/search")
    public void searchBusinessNum(SearchForm form, HttpServletResponse response) throws IOException {
        String b_no = form.getBusiness_num();

        String searchResult = searchService.searchBusinessNumber(b_no);

        if(searchResult.equals("존재하지 않는 사업자 목록")){
            alert(response, "존재하지 않는 사업자 목록");
        } else if (searchResult.equals("존재하는 사업자 목록")) {
            alert(response, "존재하는 사업자 목록");
        }else{
            alert(response, "에러");
        }
    }
}
