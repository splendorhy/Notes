package com.splendor.notes.infrastructure.apiTemp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.SortedMap;

/**
 * @author splendor.s
 * @create 2023/6/10 上午10:44
 * @description
 */
@Slf4j
public class SignFilter implements Filter {
    @Resource
    private RedisUtil redisUtil;

    //从fitler配置中获取sign过期时间
    private Long signMaxTime;

    private static final String NONCE_KEY = "x-nonce-";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        log.info("过滤URL:{}", httpRequest.getRequestURI());

        HttpServletRequestWrapper requestWrapper = new SignRequestWrapper(httpRequest);
        //构建请求头
        RequestHeader requestHeader = RequestHeader.builder()
                .nonce(httpRequest.getHeader("x-Nonce"))
                .timestamp(Long.parseLong(httpRequest.getHeader("X-Time")))
                .sign(httpRequest.getHeader("X-Sign"))
                .build();

        //验证请求头是否存在
        if(StringUtils.isEmpty(requestHeader.getSign()) || ObjectUtils.isEmpty(requestHeader.getTimestamp()) || StringUtils.isEmpty(requestHeader.getNonce())){
            //responseFail(httpResponse, ReturnCode.ILLEGAL_HEADER);
            return;
        }

        /*
         * 1.重放验证
         * 判断timestamp时间戳与当前时间是否操过60s（过期时间根据业务情况设置）,如果超过了就提示签名过期。
         */
        long now = System.currentTimeMillis() / 1000;

        if (now - requestHeader.getTimestamp() > signMaxTime) {
            //responseFail(httpResponse,ReturnCode.REPLAY_ERROR);
            return;
        }

        //2. 判断nonce
        boolean nonceExists = redisUtil.hasKey(NONCE_KEY + requestHeader.getNonce());
        if(nonceExists){
            //请求重复
            //responseFail(httpResponse,ReturnCode.REPLAY_ERROR);
            return;
        }else {
            redisUtil.set(NONCE_KEY+requestHeader.getNonce(), requestHeader.getNonce(), signMaxTime);
        }


        boolean accept;
        SortedMap<String, String> paramMap;
        switch (httpRequest.getMethod()){
            case "GET":
                paramMap = HttpDataUtil.getUrlParams(requestWrapper);
                accept = SignUtil.verifySign(paramMap, requestHeader);
                break;
            case "POST":
                paramMap = HttpDataUtil.getBodyParams(requestWrapper);
                accept = SignUtil.verifySign(paramMap, requestHeader);
                break;
            default:
                accept = true;
                break;
        }
        if (accept) {
            filterChain.doFilter(requestWrapper, servletResponse);
        } else {
            //responseFail(httpResponse,ReturnCode.ARGUMENT_ERROR);
            return;
        }

    }

    /*private void responseFail(HttpServletResponse httpResponse, ReturnCode returnCode)  {
        ResultData<Object> resultData = ResultData.fail(returnCode.getCode(), returnCode.getMessage());
        WebUtils.writeJson(httpResponse,resultData);
    }*/

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String signTime = filterConfig.getInitParameter("signMaxTime");
        signMaxTime = Long.parseLong(signTime);
    }
}
