package com.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CommandAction {
    void requestPro(HttpServletRequest request, HttpServletResponse response);
}
