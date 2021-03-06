package com.tugcankoparan.example;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.ValidationUtils;
import java.math.BigDecimal;
import java.util.Locale;


@Configuration
public class ExampleMain3 {

    @Bean
    OrderValidator validator(){
        return new OrderValidator();
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(ExampleMain3.class);

        Order order = new Order();
        order.setOrderPrice(BigDecimal.ZERO);
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(order, Order.class.getName());
        DefaultMessageCodesResolver messageCodesResolver = (DefaultMessageCodesResolver) bindingResult.getMessageCodesResolver();
        messageCodesResolver.setMessageCodeFormatter(DefaultMessageCodesResolver.Format.POSTFIX_ERROR_CODE);
        OrderValidator validator = context.getBean(OrderValidator.class);
        ValidationUtils.invokeValidator(validator, order, bindingResult);
        bindingResult.getAllErrors().forEach(e -> System.out.println(context.getMessage(e, Locale.US)));
    }
}