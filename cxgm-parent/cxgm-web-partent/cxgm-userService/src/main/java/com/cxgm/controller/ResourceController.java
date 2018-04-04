package com.cxgm.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resources")
public class ResourceController {
/*
    @Autowired
    private AclResourcesService aclResourcesService;

    *//**
     * 获取所有资源
     *//*
    @GetMapping
    public ResultDto getAllResources(){
        List<AclResources> resources =  aclResourcesService.selectAllResources();
        return new ResultDto("200","success",resources);
    }
*/
}
