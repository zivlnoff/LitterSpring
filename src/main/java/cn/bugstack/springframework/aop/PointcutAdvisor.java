package cn.bugstack.springframework.aop;

public interface PointcutAdvisor extends Advisor{
    Pointcut getPointcut();
}
