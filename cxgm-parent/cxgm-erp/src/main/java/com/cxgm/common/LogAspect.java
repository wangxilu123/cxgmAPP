package com.cxgm.common;

import org.springframework.stereotype.Component;

@Component
public class LogAspect {
   /* private static final Logger LOGGER = LoggerFactory.getLogger(DataSource.class);

    @Pointcut("execution(public * com.cxgmerp.controller..*.*(..))")
    public void webLog(){}

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        LOGGER.info("URL : " + request.getRequestURL().toString() + ",IP : " + request.getRemoteAddr() + ",CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName() + ",ARGS : " + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(returning = "object", pointcut = "webLog()")
    public void doAfterReturning(Object object) throws Throwable {
        // 处理完请求，返回内容
        LOGGER.info("RESPONSE : " + object);
    }*/
}
