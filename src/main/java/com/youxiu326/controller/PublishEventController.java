package com.youxiu326.controller;

import com.youxiu326.gateway.model.GatewayRouteDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 来自：https://blog.csdn.net/lifen0908/article/details/90234261
 */
@RestController
@RequestMapping("/event")
public class PublishEventController {


    @Autowired
    private ApplicationEventPublisher publisher;


    /**
     * 1.使用ApplicationEventPublisher的publishEvent来发布事件
     *
     */
    @GetMapping("/send")
    public String register()  {

        GatewayRouteDefinition gatewayRouteDefinition = new GatewayRouteDefinition();
        gatewayRouteDefinition.setId("test");
        gatewayRouteDefinition.setOrder(666);
        publisher.publishEvent(gatewayRouteDefinition);

        return "send success";

    }


    /**
     * 2.使用@EventListener来监听事件
     */
    @EventListener(condition = "#gatewayRouteDefinition.id!=null")
    public void handleEvent(GatewayRouteDefinition gatewayRouteDefinition) throws Exception{
        System.out.println(gatewayRouteDefinition.getId());
        System.out.println(gatewayRouteDefinition.getOrder());
    }


}
