package com.example.demo.authenication;

import com.example.demo.ComponentAccessor;
import com.example.demo.common.ErrorCode;
import com.example.demo.common.utils.TokenUtils;
import com.example.demo.user.entity.User;
import com.example.demo.user.service.UserService;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

@Component
public class JwtFilter extends OncePerRequestFilter {
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String token = request.getHeader("token");
		if (StringUtils.isEmpty(token) || !TokenUtils.validateToken(token)) {
			result.put("error", ErrorCode.AUTHENTICATION_FAILED.getValue());
			writeResponseToHttpResponse(result, response, HttpStatus.UNAUTHORIZED.value());
		} else if(!TokenUtils.validateToken(token)) {
			result.put("error", "Token Expired");
			writeResponseToHttpResponse(result, response, HttpStatus.UNAUTHORIZED.value());
		}

		UserService userService = ComponentAccessor.getComponent(UserService.class);
		String userid = request.getHeader("userid");
		User user = userService.findById(Long.parseLong(userid));
		if (user.getName() == null) {
			result.put("error", "User not found");
			writeResponseToHttpResponse(result, response, HttpStatus.BAD_REQUEST.value());
		}

		filterChain.doFilter(request, response);

	}
	
	public static void writeResponseToHttpResponse(final Object response, final HttpServletResponse httpServletResponse,
			final int status) {
		httpServletResponse.setStatus(status);

		if (response != null) {
			PrintWriter printWriter = null;
			try {
				printWriter = httpServletResponse.getWriter();
			} catch (IOException e) {
			}
			if (printWriter != null) {
				final String json = new Gson().toJson(response);
				printWriter.write(json);
				printWriter.flush();
				printWriter.close();
			}
		}
	}
}
