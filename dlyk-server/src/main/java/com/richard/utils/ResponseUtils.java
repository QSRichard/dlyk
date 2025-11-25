package com.richard.utils;

import jakarta.servlet.http.HttpServletResponse;

import java.io.PrintWriter;

public class ResponseUtils {

    public static void write(HttpServletResponse response, String result) {
        response.setContentType("application/json;charset=utf-8");

        try (PrintWriter writer = response.getWriter()) {
            writer.write(result);
            writer.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
