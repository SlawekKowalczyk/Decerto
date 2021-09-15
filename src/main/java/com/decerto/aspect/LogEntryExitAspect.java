package com.decerto.aspect;

import com.decerto.annotation.LogEntryExit;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Aspect
@Component
public class LogEntryExitAspect {

    @Around("@annotation(com.decerto.annotation.LogEntryExit)")
    public Object log(ProceedingJoinPoint point) throws Throwable {
        CodeSignature codeSignature = (CodeSignature) point.getSignature();
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();

        LogEntryExit annotation = method.getAnnotation(LogEntryExit.class);
        ChronoUnit unit = annotation.unit();
        boolean showArgs = annotation.showArgs();
        boolean showResult = annotation.showResult();
        boolean showExecutionTime = annotation.showExecutionTime();

        String methodName = method.getName();
        Object[] methodArgs = point.getArgs();
        String[] methodParams = codeSignature.getParameterNames();
        log.trace(entry(methodName, showArgs, methodParams, methodArgs));

        Instant start = Instant.now();
        Object response = point.proceed();
        Instant end = Instant.now();
        String duration = String.format("%s %s", unit.between(start, end), unit.name().toLowerCase());
        log.trace(exit(methodName, duration, response, showResult, showExecutionTime));

        return response;
    }

    private String entry(String methodName, boolean showArgs, String[] params, Object[] args) {
        StringJoiner message = new StringJoiner(" ")
                .add("Started:")
                .add(methodName)
                .add("method");

        if (showArgs && Objects.nonNull(params) && Objects.nonNull(args) && params.length == args.length) {
            Map<String, Object> values = IntStream.range(0, params.length)
                    .boxed()
                    .collect(Collectors.toMap(i -> params[i], i -> args[i], (a, b) -> b, () -> new HashMap<>(params.length)));
            message.add("with args:")
                    .add(values.values().toString());
        }
        return message.toString();
    }

    private String exit(String methodName, String duration, Object result, boolean showResult, boolean showExecutionTime) {
        StringJoiner message = new StringJoiner(" ")
                .add("Finished:")
                .add(methodName)
                .add("method");

        if (showExecutionTime) {
            message.add("in").add(duration);
        }
        if (showResult) {
            message.add("with return:").add(result.toString());
        }
        return message.toString();
    }
}