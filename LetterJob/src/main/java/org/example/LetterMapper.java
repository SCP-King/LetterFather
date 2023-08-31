package org.example;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LetterMapper extends Mapper<Text,Text,Text,Text> {

    private Text outK=new Text();
    private List<Text> outV=new ArrayList<>();
    private String page;
    private Document doc;
    @Override
    protected void map(Text key, Text value, Mapper<Text, Text, Text, Text>.Context context) throws IOException, InterruptedException {
        page=value.toString();
        doc= Jsoup.parse(page);
        String url=doc.getElementsByTag("url").first().text();
        String letterType=null;
        String letterTitle=null;
        String replyOrganization=null;
        if(url.contains("consult")) letterType="咨询";
        else letterType="建议";
        String id=url.substring(url.lastIndexOf("=") + 1);
        Elements strong = doc.getElementsByTag("strong");
        letterTitle=strong.get(0).text();
        replyOrganization=strong.get(1).text();
        String letterRecipient=doc.getElementsByClass("col-xs-10 col-lg-3 col-sm-3 col-md-4 text-muted").first().text().substring(4);
        String letterDate=doc.getElementsByClass("col-xs-5 col-lg-3 col-sm-3 col-md-3 text-muted").first().text().substring(3);
        String letterContent=doc.getElementsByClass("col-xs-12 col-md-12 column p-2 text-muted mx-2").first().text();
        String replyDate=doc.getElementsByClass("col-xs-12 col-sm-3 col-md-3 my-2").first().text().substring(5);
        String replyContent=doc.getElementsByClass("col-xs-12 col-md-12 column p-4 text-muted my-3").first().text();
        outK.set(id);
        outV.add(new Text(replyContent));
        outV.add(new Text(replyDate));
        outV.add(new Text(replyOrganization));
        outV.add(new Text(letterContent));
        outV.add(new Text(letterDate));
        outV.add(new Text(letterRecipient));
        outV.add(new Text(letterTitle));
        outV.add(new Text(letterType));
        outV.add(new Text(id));
        for(Text i:outV){
            context.write(outK,i);
        }
    }
}
