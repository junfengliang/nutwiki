package cn.genlei.nutwiki.controller;

import cn.genlei.nutwiki.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author junfeng
 */
@Controller
public class ContentController {
    @Autowired
    ContentService contentService;

    @RequestMapping(value = {"/**"},method = RequestMethod.GET)
    public void html(HttpServletRequest request, HttpServletResponse response) throws IOException {
        contentService.content(request,response);
    }

    @RequestMapping(value = {"/test"},method = RequestMethod.GET)
    public void testHtml(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().write("111 test::" + request.getRequestURI());
    }
}
