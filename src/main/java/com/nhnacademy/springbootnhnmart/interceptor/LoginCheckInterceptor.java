package com.nhnacademy.springbootnhnmart.interceptor;

import com.nhnacademy.springbootnhnmart.domain.User;
import com.nhnacademy.springbootnhnmart.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
public class LoginCheckInterceptor implements HandlerInterceptor {

    private final UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler)
            throws Exception {

        HttpSession session = request.getSession(false);

        // 세션 검사 -> 로그인 실패나 다른 기능으로 인해 userId 없이 세션 객체만 있을 수 있음
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("/cs/login");
            return false;
        }

        String id = (String) session.getAttribute("userId");
        User user = userRepository.getUser(id);

        // 유저 검사 -> 세션은 있는데 유저가 없을 수도 있음
        if (user == null){
            session.invalidate();
            response.sendRedirect("/cs/login");
            return false;
        }

        // 권한 검사 -> customer가 로그인 후 admin용 주소를 입력해서 들어온 경우 돌려보내기
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/cs/admin") && user.getAuth() != User.Auth.ROLE_ADMIN){
            response.sendRedirect("/cs");
            return false;
        }
        return true;
    }
}
