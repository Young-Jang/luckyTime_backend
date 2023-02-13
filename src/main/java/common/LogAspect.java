package toyproject.almigty.common;

import java.util.Enumeration;
import java.util.Objects;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Aspect
@Component
class LogAspect {

    @Around("execution(* toyproject.almigty.board.*Controller.*(..))")
    public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {
        long startAt = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().getName();
        printRequestInfo(methodName, joinPoint.getArgs());
        return printResponseInfo(methodName, joinPoint.proceed(), startAt);
    }

    private String printHeaderInfo() {
        HttpServletRequest request = currentRequest();
        if (Objects.isNull(request)) {
            return StringUtils.EMPTY;
        }

        String headerInfos = StringUtils.EMPTY;
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerInfo = headerName.concat("=").concat(request.getHeaders(headerName).nextElement());
            if (headerInfos.equals(StringUtils.EMPTY))
                headerInfos = headerInfo;
            else
                headerInfos = headerInfos.concat(",").concat(headerInfo);
        }
        return headerInfos;
    }

    private void printRequestInfo(String methodName, Object[] args) {
        String headerInfo = printHeaderInfo();
        if (Objects.isNull(args)) {
            log.info("[REQUEST][{}]->HEADER[{}], PARAM[]"
                    , methodName.replaceAll("\r\n", "")
                    , headerInfo.replaceAll("\r\n", ""));
            return;
        }

        String requestDtoStr = StringUtils.EMPTY;
        for (Object arg : args) {
            if(arg==null){
                continue;
            }else if (requestDtoStr.equals(StringUtils.EMPTY))
                requestDtoStr = arg.toString();
            else
                requestDtoStr = requestDtoStr.concat(", ").concat(arg.toString());
        }
        log.info("[REQUEST][{}]->HEADER[{}], PARAM[{}]"
                , methodName.replaceAll("\r\n", "")
                , headerInfo.replaceAll("\r\n", "")
                , requestDtoStr.replaceAll("\r\n", ""));
    }

    private Object printResponseInfo(String methodName, Object proceed, long startAt) {
        long endAt = System.currentTimeMillis();
        log.info("[RESPONSE][{}]->DTODATA[{}]->({}ms)"
                , methodName.replaceAll("\r\n", "")
                , proceed.toString().replaceAll("\r\n", "")
                , endAt - startAt);
        return proceed;
    }

    private HttpServletRequest currentRequest() {
        // Use getRequestAttributes because of its return null if none bound
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return Optional.ofNullable(servletRequestAttributes).map(ServletRequestAttributes::getRequest).orElse(null);
    }
}