package com.example.demo.scrapper;

import com.example.demo.models.Data;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WebScrapper {
    private WebClient client = new WebClient();
    private List<Data> itensEncontrados = new ArrayList<>();

    public WebScrapper() {
        client.getOptions().setJavaScriptEnabled(false);
        client.getOptions().setCssEnabled(false);
    }

    public List<Data> scrap() {

        try {
            String searchUrl = "https://www.calendarr.com/brasil/";
            HtmlPage page = client.getPage(searchUrl);

            List<HtmlElement> item = (List) page.getByXPath("//*[@id=\"country-container\"]/div[3]/div/div/div[1]/div[5]/ul/li[1]");

            if (Objects.isNull(item)) {
                System.out.println("NAO");
            } else {
                List<HtmlAnchor> listAnchor = (List) item.get(0).getByXPath("//*[@id=\"country-container\"]/div[3]/div/div/div[1]/div[5]/ul/li[1]/div[2]/a");
                List<HtmlSpan> listSpan = (List) item.get(0).getByXPath("//*[@id=\"country-container\"]/div[3]/div/div/div[1]/div[5]/ul/li[1]/div[2]/span");

                if (Objects.nonNull(listAnchor) || Objects.nonNull(listSpan)) {
                    if (Objects.nonNull(listAnchor)) {
                        for (HtmlAnchor htmlAnchor : listAnchor) {
                            itensEncontrados.add(new Data(htmlAnchor.asText()));
                        }
                    }

                    if (Objects.nonNull(listSpan)) {
                        for (HtmlSpan htmlSpan : listSpan) {
                            itensEncontrados.add(new Data(htmlSpan.asText()));
                        }
                    }

                    for (Data feriado : itensEncontrados) {
                        System.out.println(feriado.getNome());
                    }
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return itensEncontrados;
    }


}
