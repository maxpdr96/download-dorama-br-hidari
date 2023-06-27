package com.hidarisoft.downloaddoramabrhidari;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class SpringApp {

    public static void main(String[] args) throws IOException {
        Document docCustomConn = Jsoup.connect("URL")
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
                .get();
        var episodes = docCustomConn.select(".episodios .episodiotitle");
        var titleDorama = docCustomConn.select(".data h1").text();
        episodes.forEach(element -> {
            var singleEpisodes = element.select("a").attr("href");
            var nameEpisode = element.select("a").text().replaceAll("ó", "o");
            nameEpisode = titleDorama + " " + nameEpisode;
            try {
                downloadEpisode(singleEpisodes, nameEpisode);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
    }


    public static void downloadEpisode(String singleUrlVideo, String nameEpisode) throws IOException {
        Document docCustomConn = Jsoup.connect(singleUrlVideo)
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
                .get();

        var urlVideo = docCustomConn.select("#playcontainer .metaframe").attr("src");

        docCustomConn = Jsoup.connect(urlVideo)
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
                .get();

        Elements scripts = docCustomConn.select("script");

        for (Element script : scripts) {
            String scriptContent = script.html();

            if (scriptContent.contains("file")) {
                scriptContent = scriptContent.replaceAll("var jw =", "");
                ObjectMapper objectMapper = new ObjectMapper();
                var doramaData = objectMapper.readValue(scriptContent, DoramaData.class);
                if (!doramaData.getFile().isBlank()) {
                    System.out.println("Arquivo do dorama: " + doramaData.getFile());
                    URL url = new URL(doramaData.getFile());

                    InputStream inVideo = url.openStream();
                    FileOutputStream out = new FileOutputStream(nameEpisode + ".mp4");

                    byte[] buffer = new byte[1024];
                    int len;
                    System.out.println("Inicio de download do ep: " + nameEpisode);
                    while ((len = inVideo.read(buffer, 0, 1024)) != -1) {
                        out.write(buffer, 0, len);
                    }
                    System.out.println("fim");
                    inVideo.close();
                    out.close();
                    break;
                }
            }
        }
    }
    public static void downloadEpisodeMega(String singleUrlVideo, String nameEpisode) throws IOException {
        Document docCustomConn = Jsoup.connect(singleUrlVideo)
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
                .get();

        var urlVideo = docCustomConn.select("#playcontainer #source-player-2 .metaframe").attr("src");
        System.out.println(urlVideo);
        docCustomConn = Jsoup.connect(urlVideo)
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
                .get();

        System.out.println(docCustomConn.html());
        Elements scripts = docCustomConn.select("script");

        for (Element script : scripts) {
            String scriptContent = script.html();

            if (scriptContent.contains("file")) {
                scriptContent = scriptContent.replaceAll("var jw =", "");
                ObjectMapper objectMapper = new ObjectMapper();
                var doramaData = objectMapper.readValue(scriptContent, DoramaData.class);
                if (!doramaData.getFile().isBlank()) {
                    System.out.println("Arquivo do dorama: " + doramaData.getFile());
                    URL url = new URL(doramaData.getFile());

                    InputStream inVideo = url.openStream();
                    FileOutputStream out = new FileOutputStream(nameEpisode + ".mp4");

                    byte[] buffer = new byte[1024];
                    int len;
                    System.out.println("Inicio de download do ep: " + nameEpisode);
                    while ((len = inVideo.read(buffer, 0, 1024)) != -1) {
                        out.write(buffer, 0, len);
                    }
                    System.out.println("fim");
                    inVideo.close();
                    out.close();
                    break;
                }
            }
        }
    }

}
