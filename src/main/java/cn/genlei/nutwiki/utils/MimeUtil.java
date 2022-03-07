package cn.genlei.nutwiki.utils;

import java.util.HashMap;

/**
 * @author junfeng
 */
public class MimeUtil {
    public static final String TEXT = "text/";
    public static final String MARKDOWN = "md";
    public static final String DEFAULT_TYPE = "application/octet-stream";
    static HashMap<String,String> contentTypeMap = new HashMap<>();
    static {
        contentTypeMap.put("css","text/css");
        contentTypeMap.put("js","text/javascript");
        contentTypeMap.put("html","text/html");
        contentTypeMap.put("md","text/html");
        contentTypeMap.put("txt","text/plain");
        contentTypeMap.put("jpg","image/jpeg");
        contentTypeMap.put("png","image/png");
        contentTypeMap.put("bmp","image/bmp");
        contentTypeMap.put("gif","image/gif");
    }
    public static String getExt(String path){
        if(path==null){
            return "";
        }
        int off = path.lastIndexOf(".");
        if(off==-1){
            return "";
        }
        return path.substring(off+1).toLowerCase();
    }
    public static boolean isText(String ext){
        String type = contentTypeMap.getOrDefault(ext,"");
        if(type.contains(TEXT)){
            return true;
        }
        return false;
    }
    public static boolean isMarkdown(String ext){
        return MARKDOWN.equals(ext);
    }
    public static String getContentType(String ext){
        return contentTypeMap.getOrDefault(ext,DEFAULT_TYPE);
    }

}
