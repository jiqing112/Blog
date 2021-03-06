package com.blog.util;

import com.blog.model.Article;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * @package: com.blog.util
 * @Author: 陈淼
 * @Date: 2016/6/6
 * @Description: 文件的工具类，其上传路径文件夹为“当前项目名/resources/article”。
 *               删除文章的文件夹路径也为“当前项目名/resources/article”。
 *               注意，因为maven使用package命令后项目目录结构与工作目录完全不同（例如：没有target文件夹），
 *               因此在上传和删除文件的需要文件目录时应当注意package后实际的目录结构。
 */
public class FileUtil {
    public FileUtil() {}

    public static String upload(CommonsMultipartFile file,
                              Article article, HttpServletRequest request) throws IOException {
        if(file != null){
            //获取当前文件原始文件名
            String originalFileName = file.getOriginalFilename();
            //构造新文件名
            String newFileName = article.getCategoryId() + "-" + article.getTitle()
                    + "-" + originalFileName;
            //获取当前项目的所在的真实地址
            String realPathDir = request.getSession().getServletContext().getRealPath("/");
            String articlePath = realPathDir +"resources"+ File.separator +"article" + File.separator + newFileName;
            File newfile = new File(articlePath);
            if (!newfile.exists())
                newfile.mkdirs();
            //将文件传到指定的目录中
            file.transferTo(newfile);
            return "resources"+ File.separator +"article" + File.separator + newFileName;
        }
        return null;
    }

    public static boolean delete(String articlePath, HttpServletRequest request){
        String realPathDir = request.getSession().getServletContext().getRealPath("/")+articlePath;
        File article = new File(realPathDir);
        if (article.exists()){
            article.delete();
            return true;
        }
        return false;
    }
}
