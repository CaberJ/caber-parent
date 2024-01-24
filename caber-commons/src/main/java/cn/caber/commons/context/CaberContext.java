package cn.caber.commons.context;

import cn.caber.commons.constant.CommonConstant;
import cn.caber.commons.filter.CaberRequestFilter;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class CaberContext {

    private static final ThreadLocal<Map<String, Object>> THREAD_CONTEXT = ThreadLocal.withInitial(() -> new HashMap<>());

    public static String getToken() {
        if (Objects.nonNull(THREAD_CONTEXT.get())) {
            return (String) THREAD_CONTEXT.get().get(CommonConstant.TOKEN);
        }
        return StringUtils.EMPTY;
    }

    public static String getTraceId() {
        if (Objects.nonNull(THREAD_CONTEXT.get())) {
            return (String) THREAD_CONTEXT.get().get(CommonConstant.TRACE_ID);
        }
        return StringUtils.EMPTY;
    }

    public static void init(String token, String traceId) {
        if (StringUtils.isBlank(token)) {
            token = StringUtils.EMPTY;
        }
        if (StringUtils.isBlank(traceId)) {
            traceId = CaberRequestFilter.generateTraceId();
        }
        Map<String, Object> map = Optional.ofNullable(THREAD_CONTEXT.get()).orElse(new HashMap<>());
        map.put(CommonConstant.TOKEN, token);
        map.put(CommonConstant.TRACE_ID, traceId);
        THREAD_CONTEXT.set(map);
    }

    public static Object get(String key) {
        if (Objects.nonNull(THREAD_CONTEXT.get())) {
            return THREAD_CONTEXT.get().get(key);
        }
        return null;
    }

    public static void set(String key, Object value) {
        Map<String, Object> map = Optional.ofNullable(THREAD_CONTEXT.get()).orElse(new HashMap<>());
        map.put(key, value);
        THREAD_CONTEXT.set(map);
    }
}
