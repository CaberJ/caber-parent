package cn.caber.commons.filter;

import cn.caber.commons.constant.CommonConstant;
import cn.caber.commons.context.CaberContext;
import cn.hutool.core.lang.ObjectId;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
public class CaberRequestFilter extends OncePerRequestFilter {

    private static final String TRACE_ID = "traceId";


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
            String tranceId = httpServletRequest.getHeader(CommonConstant.TRACE_ID);
            if (StringUtils.isBlank(tranceId)) {
                tranceId = generateTraceId();
            }
            setTraceId(tranceId);
            String token = httpServletRequest.getHeader(CommonConstant.TOKEN);
            CaberContext.init(token, tranceId);
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } finally {
            clearTraceId();
        }
    }

    public static String generateTraceId() {
        return ObjectId.next();
    }

    public static void setTraceId(String traceId) {
        MDC.put(TRACE_ID, traceId);
    }

    public static void clearTraceId() {
        MDC.clear();
    }
}



