package com.pinyougou.shop.controller;

import com.pinyougou.entity.Result;
import com.pinyougou.utils.FastDFSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @program:PinYouGou01
 * @description:用于用户上传文件的
 * @author:Mr.lu
 * @create:2019-07-16 13:10
 **/
@RestController
public class UploadController {
    @Value("${FAST_DFS_SERVER}")
    private String FAST_DFS_SERVER;
    private String oldName;
    private String extName;
    private FastDFSClient dfsClient;
    private String uploadFile;

    @RequestMapping("upload")
    public Result upload(MultipartFile file) {
        try {
            //1、解析后缀名
            oldName = file.getOriginalFilename();
            //后缀名
            extName = oldName.substring(oldName.lastIndexOf(".") + 1);
            //2、通过FastDFS上传文件
            dfsClient = new FastDFSClient("classpath:fdfs_client.conf");
            //上传文件
            uploadFile = dfsClient.uploadFile(file.getBytes(), extName);
            //3、拼接返回路径
            String url = FAST_DFS_SERVER + uploadFile;
            return new Result(true, url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, "文件上传失败！");
    }
}
