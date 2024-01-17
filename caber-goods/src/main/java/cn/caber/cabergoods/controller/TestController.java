package cn.caber.cabergoods.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping("/file")
@Slf4j
public class TestController {

    @PostMapping("/upload")
    @ResponseBody
    public String handleFileUpload(@RequestPart("file") MultipartFile file) {
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
}
