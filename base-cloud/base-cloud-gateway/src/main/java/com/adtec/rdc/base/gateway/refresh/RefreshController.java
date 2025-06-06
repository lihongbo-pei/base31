package com.adtec.rdc.base.gateway.refresh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adtec.rdc.base.gateway.event.RefreshRouteService;

/**
 * @author: JTao
 * @date: 2018/9/29 18:18
 * @description: 刷新zuul路由
 */
@RestController
public class RefreshController {

    @Autowired
    private RefreshRouteService refreshRouteService;

    @GetMapping("/refresh")
    public String refresh() {
        refreshRouteService.refreshRoute();
        return "refresh";
    }

}
