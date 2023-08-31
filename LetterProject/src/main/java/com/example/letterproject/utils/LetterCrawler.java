package com.example.letterproject.utils;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class LetterCrawler implements PageProcessor {
    private Site site=new Site();
    private static int num=0;
    @Override
    public void process(Page page) {
        System.out.println(++num);
        page.putField("html",page.getRawText());
    }

    @Override
    public Site getSite() {
        site.setCharset("UTF-8");
        site.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36 Edg/115.0.1901.188");
        return site;
    }
}
