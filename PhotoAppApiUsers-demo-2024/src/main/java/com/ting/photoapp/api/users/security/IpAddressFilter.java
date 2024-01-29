package com.ting.photoapp.api.users.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;

public class IpAddressFilter implements Filter {

    private List<String> allowedIpAddresses;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialize filter if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String remoteAddress = request.getRemoteAddr();
        if (allowedIpAddresses.contains(remoteAddress)) {
            chain.doFilter(request, response);
        } else {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.getWriter().write("Access denied for IP address: " + remoteAddress);
            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    @Override
    public void destroy() {
        // Cleanup resources if needed
    }

    public void setAllowedIpAddresses(String... allowedIpAddresses) {
        this.allowedIpAddresses = Arrays.asList(allowedIpAddresses);
    }
}
