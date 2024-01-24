package cn.caber.commons.interceptor;

import cn.caber.commons.constant.CommonConstant;
import cn.caber.commons.context.CaberContext;
import feign.RequestInterceptor;
import feign.RequestTemplate;

public class CaberRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        template.header(CommonConstant.TOKEN, CaberContext.getToken());
        template.header(CommonConstant.TRACE_ID, CaberContext.getTraceId());
    }
}
