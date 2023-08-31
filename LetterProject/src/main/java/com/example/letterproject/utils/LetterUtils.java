package com.example.letterproject.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import us.codecraft.webmagic.Spider;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LetterUtils {


    private static String get_json(String s){
        s=s.replace("page:","\"page\":");
        s=s.replace("pageNo:","\"pageNo\":");
        s=s.replace("totalCount:","\"totalCount\":");
        s=s.replace("totalPages:","\"totalPages\":");
        s=s.replace("pageSize:","\"pageSize\":");
        s=s.replace("result:","\"result\":");
        s=s.replace("originalId:","\"originalId\":");
        s=s.replace("letterType:","\"letterType\":");
        s=s.replace("letterTypeName:","\"letterTypeName\":");
        s=s.replace("letterTitle:","\"letterTitle\":");
        s=s.replace("showLetterTitle:","\"showLetterTitle\":");
        s=s.replace("writeDate:","\"writeDate\":");
        s=s.replace("orgNames:","\"orgNames\":");
        s=s.replace("showOrgNames:","\"showOrgNames\":");
        s=s.replace("'","\"");
        return s;
    }
    @SneakyThrows

    public void loadLetters(){
        Document start_page = Jsoup.parse(new URL("https://www.beijing.gov.cn/hudong/hdjl/sindex/bjah-index-hdjl!letterListJson.action?keyword=&startDate=&endDate=&letterType=0&page.pageNo=1&page.pageSize=0&orgtitleLength=26"), 30000);
        String json_start=start_page.text();
        json_start=get_json(json_start);
        ObjectMapper objectMapper=new ObjectMapper();
        JsonNode jsonNode=objectMapper.readTree( json_start);
        String num= String.valueOf(jsonNode.get("page").get("totalCount")).replace("\"","");
        Document end_page=Jsoup.parse(new URL("https://www.beijing.gov.cn/hudong/hdjl/sindex/bjah-index-hdjl!letterListJson.action?keyword=&startDate=&endDate=&letterType=0&page.pageNo=1&page.pageSize="+num+"&orgtitleLength=26"),3000);
        String json_end=end_page.text();
        json_end=get_json(json_end);
        jsonNode=objectMapper.readTree(json_end);
        ArrayNode arrayNode= (ArrayNode) jsonNode.get("result");
        List<String> url_list=new ArrayList<>();
        for(JsonNode i:arrayNode){
            String id=i.get("originalId").toString().replace("\"","");
            String kind=i.get("letterTypeName").toString().replace("\"","");
            if(kind.equals("咨询")) {
                url_list.add("https://www.beijing.gov.cn/hudong/hdjl/com.web.consult.consultDetail.flow?originalId="+id);
            }
            else {
                url_list.add("https://www.beijing.gov.cn/hudong/hdjl/com.web.suggest.suggesDetail.flow?originalId="+id);
            }
        }
        String[] urls=url_list.toArray(new String[0]);
        Spider.create(new LetterCrawler())
                .thread(100)
                .addUrl(urls)
                .addPipeline(new LetterFilePipeline("LetterProject/src/main/resources/static/data/letter_htmls/"))
                .run();

    }

}
