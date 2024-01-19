package cn.caber.cabergoods.controller;

import cn.caber.cabergoods.po.Param;
import cn.caber.cabergoods.service.TestService;
import cn.caber.commons.buried.log.BuriedPoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Autowired
    private TestService testService;

    @PostMapping("/upload")
    @ResponseBody
    @BuriedPoint
    public String handleFileUpload(@RequestParam("name") String name, @RequestPart("file") MultipartFile file) {
        if (!file.isEmpty()) {

            InputStream inputStream = null;
            try {
                inputStream = file.getInputStream();
                Scanner scanner = new Scanner(inputStream, "UTF-8");
                return scanner.hasNext() ? scanner.next() : "";
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "File uploaded successfully!";
        } else {
            return "File is empty!";
        }
    }

    @PostMapping("/upload1")
    @ResponseBody
    @BuriedPoint
    public String handleFileUpload1(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            // 处理文件流，例如保存到磁盘或进行其他操作
            // file.getInputStream() 获取文件输入流
            // ...
            return "File uploaded successfully!";
        } else {
            return "File is empty!";
        }
    }

    @PostMapping("/11")
    @ResponseBody
    @BuriedPoint
    public Param param0(@RequestBody Param param) {
        return param;
    }

    @GetMapping("/22")
    @ResponseBody
    @BuriedPoint
    public Param param1(@RequestParam("name") String name) {
        Param param = new Param();
        param.setName(name);
        param.setId(22222L);
        return param;
    }


    @PostMapping("/33")
    @ResponseBody
    public Param param2(@RequestParam("name") String name, @RequestBody Param param) {
        param.setName(name);
        return param;
    }

    @PostMapping("/44")
    @ResponseBody
    public Param param4(@RequestParam("name") String name, @RequestBody Param param) {
        Param res = testService.doService(name, param);
        int a= 1/0;
        return res;
    }

}
