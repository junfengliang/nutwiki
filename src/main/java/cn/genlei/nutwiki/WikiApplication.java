package cn.genlei.nutwiki;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author junfeng
 */
@SpringBootApplication
public class WikiApplication {
    private static String docRoot;
    public static String getDocRoot(){
        return docRoot;
    }
    public static void main(String[] args){
        if(args!=null && args.length>0){
            docRoot = args[0];
        }
        SpringApplication.run(WikiApplication.class,args);
    }
}
