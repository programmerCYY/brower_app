package com.example.backend.schedule;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.HashMap;
import java.util.Map;

/**
 * @author XXX
 * @since 2018-04-12
 */

public interface NewsPuller {

    void pullNews();

    void pullSports();

    void pullCars();

    void pullEntertain();

    void pullIt();

    default Document getHtmlFromUrl(String url, boolean useHtmlUnit) throws Exception {
        if (!useHtmlUnit) {
            return Jsoup.connect(url)
                    //模拟火狐浏览器
                    .userAgent("Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)")
                    .get();
        } else {
            WebClient webClient = new WebClient(BrowserVersion.CHROME);
            webClient.getOptions().setJavaScriptEnabled(true);
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setActiveXNative(false);
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
            webClient.getOptions().setTimeout(10000);
            HtmlPage rootPage = null;
            try {
                rootPage = webClient.getPage(url);
                webClient.waitForBackgroundJavaScript(10000);
                String htmlString = rootPage.asXml();
                return Jsoup.parse(htmlString);
            } catch (Exception e) {
                throw e;
            } finally {
                webClient.close();
            }
        }
    }

    default Document getHtmlFromUrlPretend(String url, boolean useHtmlUnit) throws Exception {
        if (!useHtmlUnit) {
            Connection connect = Jsoup.connect(url);
            Map<String, String> header = new HashMap<String, String>();
            header.put("Host", "http://yule.sohu.com/");
            header.put("User-Agent", "	Mozilla/5.0 (Windows NT 6.1; WOW64; rv:5.0) Gecko/20100101 Firefox/5.0");
            header.put("Accept", "	text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            header.put("Accept-Language", "zh-cn,zh;q=0.5");
            header.put("Accept-Charset", "	GB2312,utf-8;q=0.7,*;q=0.7");
            header.put("Connection", "keep-alive");
            header.put("Content-Type","multipart/form-data;");
            Connection data = connect.data(header);
            Document document = data.get();
            return document;

        } else {
            WebClient webClient = new WebClient(BrowserVersion.CHROME);
            webClient.getOptions().setJavaScriptEnabled(true);
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setActiveXNative(false);
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
            webClient.getOptions().setTimeout(10000);
            HtmlPage rootPage = null;
            try {
                rootPage = webClient.getPage(url);
                webClient.waitForBackgroundJavaScript(10000);
                String htmlString = rootPage.asXml();
                return Jsoup.parse(htmlString);
            } catch (Exception e) {
                throw e;
            } finally {
                webClient.close();
            }
        }
    }
}
