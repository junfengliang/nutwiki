package cn.genlei.nutwiki.service;

import cn.genlei.nutwiki.utils.MimeUtil;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Arrays;

/**
 * @author junfeng
 */
@Service
public class ContentService {
    String docRoot = "/Users/nid/docs/kafka";
    private static final String ROOT ="/";
    private static final String REPLACE_TAG ="${md}";
    private static final String TEMPLATE ="template.html";
    private static final String DEFAULT_CONTENT_TYPE ="text/html";
    private static final String ENCODING ="utf-8";
    private static final int BUFFER = 16*1024;

    public void content(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();
        if(ROOT.equals(uri)){
            uri = "README.md";
        }
        File root = new File(docRoot);
        File dest = new File(root,uri);
        if(dest.exists()){
            String ext = MimeUtil.getExt(uri);
            boolean isText = MimeUtil.isText(ext);
            try(FileInputStream fis = new FileInputStream(dest)) {
                String html = "";
                response.setContentType(MimeUtil.getContentType(ext));

                if(isText){
                    String text = IOUtils.toString(new FileInputStream(dest),"utf-8");
                    if(MimeUtil.isMarkdown(ext)) {
                        html = text2html(text);
                        html = patchTemplate(html);
                    }else{
                        html = text;
                    }
                    response.setCharacterEncoding(ENCODING);
                    response.getWriter().write(html);
                }else {
                    byte[] bs = new byte[BUFFER];
                    while (true){
                        int len = fis.read(bs);
                        if(len==-1){
                            break;
                        }
                        response.getOutputStream().write(bs,0,len);
                    }
                }
            }  catch (IOException e) {
                response.getWriter().write(e.getMessage());
                e.printStackTrace();
            }
        }else{
            response.setContentType(DEFAULT_CONTENT_TYPE);
            response.setCharacterEncoding(ENCODING);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.getWriter().write("404 not found");
        }
    }

    private String patchTemplate(String html) {
        File root = new File(docRoot);
        File dest = new File(root,TEMPLATE);
        if(dest.exists()){
            try(FileInputStream fis = new FileInputStream(dest)) {
                String text = IOUtils.toString(new FileInputStream(dest), ENCODING);
                String val = StringUtils.replace(text,REPLACE_TAG,html);
                return val;
            }catch (IOException e){
                e.printStackTrace();
                return html;
            }
        }else{
            return html;
        }
    }

    private String text2html(String text) {

        MutableDataSet options = new MutableDataSet();

        // uncomment to set optional extensions
        options.set(Parser.EXTENSIONS, Arrays.asList(TablesExtension.create(), StrikethroughExtension.create()));

        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();

        // You can re-use parser and renderer instances
        Node document = parser.parse(text);
        String html = renderer.render(document);
        return html;
    }
}
