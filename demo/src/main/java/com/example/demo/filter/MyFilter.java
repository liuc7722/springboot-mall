package com.example.demo.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import com.example.Response.BaseResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter(urlPatterns = "/*", filterName = "myFilter")
public class MyFilter extends OncePerRequestFilter {

    // 白名單
    final ArrayList<String> whiteList = new ArrayList<>(Arrays.asList(new String[]{"/user/login"}));
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("My Filter getServletPath: " + request.getServletPath());
        System.out.println("My Filter getRequestURL: " + request.getRequestURL());
        
        if(whiteList.contains(request.getServletPath())){
            // 不需要過濾
            filterChain.doFilter(request, response);
            return;
        }

        // session是附屬在request裡面的
        // 判斷有無登入
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("userId2: " + userId);
        if (userId == null) {
            // 未登入，直接response
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("utf-8");
            response.getWriter().println(objectMapper.writeValueAsString(new BaseResponse(999, "未登入(filter)")));
            // throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } else {
            // 放行，到下一個filter或是api本體
            // filterChain.doFilter(request, response);
            // throw new UnsupportedOperationException("Unimplemented method 'doFilterInternal'");
        }

    }

}
