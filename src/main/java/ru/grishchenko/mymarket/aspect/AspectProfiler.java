package ru.grishchenko.mymarket.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Aspect
@EnableAspectJAutoProxy
@Component
public class AspectProfiler {

    private final Map<String, Integer> methodMap = new HashMap<>();
    private final Map<String, Long> methodTimeMap = new HashMap<>();

    @Pointcut("execution(public * ru.grishchenko.mymarket..*.*(..))")
    private void allMethodsProfiler(){};

    @Pointcut("execution(public * ru.grishchenko.mymarket.controllers.*.*(..))")
    private void controllersProfiler(){};

    @Before("allMethodsProfiler()")
    public void beforeMethods2(JoinPoint joinPoint) {
        int count = 1;
        String methodName = joinPoint.getSignature().toString();
        if (methodMap.containsKey(methodName)) {
            count = methodMap.get(methodName);
            count++;
        }
        methodMap.put(methodName, count);

        //  при каждом вызове вычисляем метод вызванный большее кол-во раз
        Map.Entry<String, Integer> maxEntry = methodMap.entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get();
        System.out.println("Max use method: " + maxEntry.getKey() + "; Use count: " + maxEntry.getValue());
    }

    @Around("controllersProfiler()")
    public Object controllerTimeProfiler(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        long end = System.currentTimeMillis();
        long duration = end - start;
        //  при каждом вызове считаем время выполнения метода и записываем(обновляем если уже вызывался) его в map
        methodTimeMap.put(proceedingJoinPoint.getSignature().toString(), duration);

        //  нахоим метод выполняющийся дольше всех
        Map.Entry<String, Long> maxEntry = methodTimeMap.entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get();
        System.out.println("Method longer time: " + maxEntry.getKey() + "; Time: " + maxEntry.getValue() + " ms");
        return result;
    }
}
