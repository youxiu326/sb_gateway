package com.youxiu326.gateway.configuration;

import com.alibaba.fastjson.JSON;
import com.youxiu326.gateway.dao.GatewayRouteDefinitionMapper;
import com.youxiu326.gateway.model.GatewayFilterDefinition;
import com.youxiu326.gateway.model.GatewayPredicateDefinition;
import com.youxiu326.gateway.model.GatewayRouteDefinition;
import com.youxiu326.gateway.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.*;


/**
 *
 * 核心配置类，项目初始化加载数据库的路由配置
 *
 */
@Service
public class GatewayServiceHandler implements ApplicationEventPublisherAware, CommandLineRunner {

    private final static Logger log = LoggerFactory.getLogger(GatewayServiceHandler.class);

    @Autowired
    private RedisRouteDefinitionRepository routeDefinitionWriter;

    private ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    // 获取数据dao
    @Autowired
    private GatewayRouteDefinitionMapper gatewayRouteMapper;

    // springboot启动后执行
    @Override
    public void run(String... args){
        this.loadRouteConfig();
    }

    /**
     * 更新路由
     * @return
     */
    public String loadRouteConfig() {

        //从数据库拿到路由配置
        List<GatewayRouteDefinition> gatewayRouteList = gatewayRouteMapper.queryAllRoutes();

        log.info("网关配置信息：=====>"+ JSON.toJSONString(gatewayRouteList));

        gatewayRouteList.forEach(gatewayRoute -> {

            // 创建路由对象
            RouteDefinition definition = new RouteDefinition();

            definition.setId(gatewayRoute.getId());

            // 设置路由执行顺序
            definition.setOrder(gatewayRoute.getOrder());

            // 设置路由规则转发的目标uri
            URI uri = UriComponentsBuilder.fromHttpUrl(gatewayRoute.getUri()).build().toUri();
            definition.setUri(uri);


            // 设置路由断言
            String predicatesJson = gatewayRoute.getPredicatesJson();
            List<PredicateDefinition> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(predicatesJson)){
                List<GatewayPredicateDefinition> predicateDefinitions = JsonUtils.parseList(predicatesJson, GatewayPredicateDefinition.class);
                predicateDefinitions.stream().forEach(it->{
                    PredicateDefinition p = new PredicateDefinition();
                    p.setName(it.getName());
                    p.setArgs(it.getArgs());
                    predicates.add(p);
                });
            }
            definition.setPredicates(predicates);

            // 设置过滤器
            String filtersJson = gatewayRoute.getFiltersJson();
            List<FilterDefinition> filters = new ArrayList<>();
            if (StringUtils.isNotBlank(filtersJson)){
                List<GatewayFilterDefinition> filterDefinitions = JsonUtils.parseList(filtersJson, GatewayFilterDefinition.class);
                filterDefinitions.stream().forEach(it->{
                    FilterDefinition f = new FilterDefinition();
                    f.setName(it.getName());
                    f.setArgs(it.getArgs());
                    filters.add(f);
                });
            }
            definition.setFilters(filters);

            // 保存路由
            routeDefinitionWriter.save(Mono.just(definition)).subscribe();
        });

        // 发送更新路由事件
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
        return "success";
    }


    /**
     * 删除路由
     * @param routeId
     */
    public void deleteRoute(String routeId){
        routeDefinitionWriter.delete(Mono.just(routeId)).subscribe();

        // 发送更新路由事件
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
    }
}