package com.example.demo.services;

import com.example.demo.models.Data;
import com.example.demo.scrapper.WebScrapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DataService {
    public static List<Data> listDatas;

    public DataService() {
        listDatas = new ArrayList<>();
        WebScrapper scrapper = new WebScrapper();
        listDatas = scrapper.scrap();
    }


    public static List<Data> datas(){
        return listDatas;
    }

}
