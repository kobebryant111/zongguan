package com.tansun.wf.decision.util;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Map;

/**
 * el表达式解析
 * @author ldh
 */
public class ElUtil {

    /**
     * el表达式解析
     * @param expr 你好，#{#user}
     * @param map {"user", "xxx"}
     * @return 你好，xxx
     */
    public static String readExpr(String expr, Map<String, Object> map) {
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(expr, new TemplateParserContext());
        EvaluationContext context = new StandardEvaluationContext();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            context.setVariable(entry.getKey(), entry.getValue());
        }
        return expression.getValue(context, String.class);
    }
}
