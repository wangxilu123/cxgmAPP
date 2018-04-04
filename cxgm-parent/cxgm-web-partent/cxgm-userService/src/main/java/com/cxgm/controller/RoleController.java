package com.cxgm.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class RoleController {
/*
    @Autowired
    private AclRoleService roleService;

    @ApiOperation(value="查询所有角色信息", notes="查询所有角色信息")
    @GetMapping("/query")
    public ResultDto queryRoles(int pageNum, int pageSize){

        PageInfo<AclRole>  roleList= roleService.queryAllAclRole(pageNum,pageSize);

        return new ResultDto("200","success",roleList);
    }

    @ApiOperation(value="增加一个角色", notes="增加一个角色")
    @PostMapping("/add")
    public ResultDto addRole(
            @RequestParam(value = "roleName") String roleName,
            @RequestParam(value ="responseUserId") String responseUserId,
            @RequestParam(value ="ids") String[] ids){
        //session 获取当前用户ID
        int localUserId = 0;
        roleService.addAclRole(roleName,Integer.parseInt(responseUserId),ids,localUserId);
        return new ResultDto("200","success");
    }

    @ApiOperation(value="增加一个角色", notes="增加一个角色")
    @PostMapping("/addMap")
    public ResultDto addRoleByMap(@RequestBody Map<String,Object> data){
        //session 获取当前用户ID
        int localUserId = 0;
        String idsString = (String) data.get("ids");
        String[] idss = idsString.split(",");
        roleService.addAclRole(data.get("roleName").toString(),Integer.parseInt(data.get("responseUserId").toString()),idss,localUserId);
        return new ResultDto("200","success");
    }

    @ApiOperation(value="更新角色信息", notes="更新角色信息")
    @PostMapping("/update")
    public ResultDto updateRole(int roleId, String stationName, int responseUserId, String[] ids,int userid){
        //session 获取当前用户ID
        int localUserId = 0;

        roleService.modifyAclRole(roleId,stationName,responseUserId,ids,localUserId);

        return new ResultDto("200","success");
    }

    @ApiOperation(value="删除角色", notes="删除角色")
    @PostMapping("/{id}")
    public ResultDto deleteRole(String id){
        //session 获取当前用户ID
        int localUserId = 0;
        int roleId = Integer.parseInt(id);
        roleService.deleteAclRole(roleId,localUserId);
        return new ResultDto("200","success");
    }
*/

}
