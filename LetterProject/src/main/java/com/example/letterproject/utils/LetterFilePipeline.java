package com.example.letterproject.utils;

import lombok.SneakyThrows;
import org.apache.commons.codec.digest.DigestUtils;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.FilePipeline;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class LetterFilePipeline extends FilePipeline {
    public LetterFilePipeline(String path) {
        super(path);
    }
    @SneakyThrows
    @Override
    public void process(ResultItems resultItems, Task task) {
        String path = this.path + PATH_SEPERATOR + task.getUUID() + PATH_SEPERATOR;
        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(Files.newOutputStream(this.getFile(path + DigestUtils.md5Hex(resultItems.getRequest().getUrl()) + ".html").toPath()), StandardCharsets.UTF_8));
        String page=resultItems.get("html");
        printWriter.println("<url>"+resultItems.getRequest().getUrl()+"</url>");
        printWriter.println(page);
        printWriter.close();
    }
}
