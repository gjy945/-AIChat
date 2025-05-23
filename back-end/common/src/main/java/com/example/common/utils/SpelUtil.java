package com.example.common.utils;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.StandardReflectionParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;

/**
 * 解析SPEL 表达式
 */
public class SpelUtil {

    /**
     * 支持 #p0 参数索引的表达式解析
     * @param rootObject 根对象,method 所在的对象
     * @param spel 表达式
     * @param method ，目标方法
     * @param args 方法入参
     * @return 解析后的字符串
     */
    public static String parse(Object rootObject,String spel, Method method, Object[] args) {
        if (StrUtil.isBlank(spel)) {
            return StrUtil.EMPTY;
        }
        //获取被拦截方法参数名列表(使用Spring支持类库)
        StandardReflectionParameterNameDiscoverer standardReflectionParameterNameDiscoverer = new StandardReflectionParameterNameDiscoverer();
        String[] paraNameArr = standardReflectionParameterNameDiscoverer.getParameterNames(method);
        if (ArrayUtil.isEmpty(paraNameArr)) {
            return spel;
        }
        //使用SPEL进行key的解析
        ExpressionParser parser = new SpelExpressionParser();
        //SPEL上下文
        StandardEvaluationContext context = new MethodBasedEvaluationContext(rootObject,method,args,standardReflectionParameterNameDiscoverer);
        //把方法参数放入SPEL上下文中
        for (int i = 0; i < paraNameArr.length; i++) {
            context.setVariable(paraNameArr[i], args[i]);
        }
        return parser.parseExpression(spel).getValue(context, String.class);
    }
}
