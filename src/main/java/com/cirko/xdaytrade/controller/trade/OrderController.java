package com.cirko.xdaytrade.controller.trade;

import com.cirko.xdaytrade.helpers.response.Resp;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
    @RequestMapping("list")
    public Object list() {
        Map<String, String> map = new HashMap<>();
        map.put("1", "q");
        map.put("2", "b");

        return Resp.success(map);
    }
}
