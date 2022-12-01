package com.changgou.goods.feign;

import com.changgou.common.Result;
import com.changgou.goods.pojo.Spu;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value="goods")
@RequestMapping("/spu")
public interface SpuFeign {
    @GetMapping("/{id}")
    public Result<Spu> findById(@PathVariable(name = "id") Long id);
}