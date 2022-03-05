package cn.genlei.nutwiki;

import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.Arrays;

/**
 * @author junfeng
 */
public class Test {
    public static void main(String[] args) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("test.md");

        String postContent = IOUtils.toString(classPathResource.getInputStream(),"utf-8");

        MutableDataSet options = new MutableDataSet();

        // uncomment to set optional extensions
        options.set(Parser.EXTENSIONS, Arrays.asList(TablesExtension.create(), StrikethroughExtension.create()));

        // uncomment to convert soft-breaks to hard breaks
        //options.set(HtmlRenderer.SOFT_BREAK, "<br />\n");

        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();

        // You can re-use parser and renderer instances
        Node document = parser.parse(postContent);
        String html = renderer.render(document);
        // "<p>This is <em>Sparta</em></p>\n"
        System.out.println(html);
        FileOutputStream fos = new FileOutputStream(new File("/Users/nid/Downloads/test.html"));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos,"utf-8"));
        IOUtils.write(html,writer);
        writer.flush();
        writer.close();
    }
}
