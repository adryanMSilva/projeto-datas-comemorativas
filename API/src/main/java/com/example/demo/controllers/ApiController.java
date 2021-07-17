package com.example.demo.controllers;

import com.example.demo.models.Data;
import com.example.demo.services.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApiController {

    @Autowired
    private static DataService service;

    @GetMapping(value = "/api/DatasComemorativas/hoje" , produces = "application/json;charset=UTF-8")
    public List<Data> listarDatas(){
        return service.datas();
    }

}
