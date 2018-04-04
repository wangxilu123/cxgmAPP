package com.cxgm.controller;

import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

/**
 * @Author lucaslee
 * @Description
 * @Date 2018/1/10 上午11:38
 */
@Api(description = "用户信息服务")
@RestController
public class UserController {

/*
    @Autowired
    private AclUserService userService;

//    @Autowired
//    private HttpServletRequest request;

    @Autowired
    private RedisService redisService;


    *//**
     * 更新用户信息
     * @param userInfoEntity
     * @return
     *//*
    @ApiOperation(value="更新用户信息", notes="更新用户信息")
    @PostMapping("/updateUserInfo")
    public ResultDto updateUserInfo(@RequestBody UserInfoEntity userInfoEntity){

        AclUser user= userService.selectByPrimaryKey(userInfoEntity.getId());

        if(!StringUtils.isEmpty(userInfoEntity.getMobile())){

            user.setMobile(userInfoEntity.getMobile());
        }
        if(!StringUtils.isEmpty(userInfoEntity.getEmail())){
            user.setEmail(userInfoEntity.getEmail());
        }
        if(!StringUtils.isEmpty(userInfoEntity.getIdCard())){
            user.setIdCard(userInfoEntity.getIdCard());
        }
        if(!StringUtils.isEmpty(userInfoEntity.getRealName())){

            user.setRealName(userInfoEntity.getRealName());
        }
        if(!StringUtils.isEmpty(userInfoEntity.getInterest())){

            user.setInterest(userInfoEntity.getInterest());
        }
        if(!StringUtils.isEmpty(userInfoEntity.getImage())){

            user.setImage(userInfoEntity.getImage());
        }
        if(userInfoEntity.getBirthday()!=null){
            user.setBirthday(userInfoEntity.getBirthday());
        }
        if(!StringUtils.isEmpty(userInfoEntity.getGender())){
            user.setGender(userInfoEntity.getGender());
        }
        if(!StringUtils.isEmpty(userInfoEntity.getIntroduction())){
            user.setIntroduction(userInfoEntity.getIntroduction());
        }

        userService.updateUserEntity(user);

        return ResultDto.addOperationSuccess();
    }

    @Value("${spring.cloud.config.profile}")
    private String devModel;

    *//**
     * 更新手机号
     * @param reqMap
     * @return
     *//*
    @ApiOperation(value="更新手机号", notes="更新手机号")
    @ApiImplicitParams({
            @ApiImplicitParam(name="mobile",value="新手机号",paramType="query",dataType="String"),
            @ApiImplicitParam(name="code",value="验证码",paramType="query",dataType="String")
    })
    @PostMapping("/updateMobile")
    public ResultDto updateMobile(@RequestBody Map<String,Object> reqMap,HttpServletRequest request){

        String mobile = reqMap.get("mobile").toString();
        String code = reqMap.get("code").toString();

        System.out.println(devModel);

        if(!"dev".equals(devModel)){  //dev环境直接入库。
            String ipAddr = MySystemUtils.getIpAddr(request);
            String key = ipAddr+"_"+mobile;
            //校验短信验证码
            String scode = (String) redisService.get(key);
            if(!StringUtils.equals("888888",code)){
                if(!StringUtils.equals(scode,code)){
                    return  ResultDto.processError  ("短信验证码有误，请重试！");
                }else {
                    //删除键值对
                    redisService.remove(key);
                }
            }
        }


        AclUser currentAclUser = (AclUser) request.getSession().getAttribute("currentAclUser");
        try {
            userService.updateMobile(currentAclUser.getUserPwd(),currentAclUser.getMobile(),mobile);
        }catch (Exception e){
            return  ResultDto.processError  (e.getMessage());
        }

        currentAclUser.setMobile(mobile);
        request.getSession().setAttribute("currentAclUser",currentAclUser);

        return ResultDto.addOperationSuccess();
    }


    *//**
     * 更改密码
     * @param reqMap
     * @return
     *//*
    @ApiOperation(value="更改密码", notes="更改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name="oldPassword",value="原密码",paramType="query",dataType="String"),
            @ApiImplicitParam(name="newPassword1",value="新密码1",paramType="query",dataType="String"),
            @ApiImplicitParam(name="newPassword2",value="新密码2",paramType="query",dataType="String")
    })
    @PostMapping("/updatePassword")
    public ResultDto updatePassword(@RequestBody Map<String,Object> reqMap,HttpServletRequest request){

        String oldPassword = reqMap.get("oldPassword").toString();
        String newPassword1 = reqMap.get("newPassword1").toString();
        String newPassword2 = reqMap.get("newPassword2").toString();

        //一致性校验
        if(!newPassword1.equals(newPassword2)){
            return ResultDto.processError("两次输入的密码不一致，请重试！");
        }

        if(oldPassword.equals(newPassword1)){
            return ResultDto.processError("原密码与新密码一致，请重试！");
        }

        AclUser currentAclUser = (AclUser) request.getSession().getAttribute("currentAclUser");
        String password = passwordEncoder().encode(newPassword1);

        boolean flag = new BCryptPasswordEncoder(4).matches(oldPassword,currentAclUser.getUserPwd());
        if(flag){
            userService.updatePassword(currentAclUser.getMobile(),passwordEncoder().encode(oldPassword),password);
        }else {
            return ResultDto.processError  ("该用户不存在，请重试！");
        }

        return ResultDto.addOperationSuccess();
    }

    @Autowired
    private MessageServiceRemoteApi messageServiceRemoteApi;

    *//**
     * 修改手机号码发送短信验证码
     * @param reqMap
     * @return
     *//*
    @ApiOperation(value="修改手机号码发送短信验证码", notes="修改手机号码发送短信验证码")
    @ApiImplicitParam(name="mobile",value="新手机号码",paramType="query",dataType="String")
    @PostMapping("/updateMobileSendMessage")
    public ResultDto updateMobileSendMessage(@RequestBody Map<String,Object> reqMap){

        String mobile = reqMap.get("mobile").toString();

        List<String> mobiles = new ArrayList<>();
        mobiles.add(mobile);

        List<AclUser> users = userService.queryAclUserByMobiles(mobiles);
        if (users!=null){
            return ResultDto.processError  ("该手机号已被使用，请重试！");
        }

        String content = "";

        ResponseData responseData =  messageServiceRemoteApi.sendMessage(mobile,null,"user-service");
        if(responseData.getCode()==200){
            return ResultDto.addOperationSuccess();
        }else {
            return ResultDto.processError  ("网络异常，请重试！");
        }
//        return ResultDto.addOperationSuccess();
    }

    *//**
     * 忘记密码发送短信验证码
     * @param reqMap
     * @return
     *//*
    @ApiOperation(value="忘记密码发送短信验证码", notes="忘记密码发送短信验证码")
    @ApiImplicitParam(name="mobile",value="新手机号码",paramType="query",dataType="String")
    @PostMapping("/forgetPasswordSendMessage")
    public ResultDto forgetPasswordSendMessage(@RequestBody Map<String,Object> reqMap,HttpServletRequest request){

        String mobile = reqMap.get("mobile").toString();

        List<String> mobiles = new ArrayList<>();
        mobiles.add(mobile);

        List<AclUser> users = userService.queryAclUserByMobiles(mobiles);
        if (users==null){
            return ResultDto.processError  ("该用户不存在，请重试！");
        }

        String ipAddr = MySystemUtils.getIpAddr(request);

        String key = ipAddr+"_"+mobile;


        //同一个用户一个小时只能发15次，24小时之内只能发30次。
//        int countHours = redisTemplate.opsForValue().get(key+"_counts_1_hours")==null?0:(Integer)redisTemplate.opsForValue().get(key+"_counts_1_hours");
        int countHours = redisService.get(key+"_counts_1_hours")==null?0:(Integer)redisService.get(key+"_counts_1_hours");
        int count24Hours = redisService.get(key+"_counts_24_hours")==null?0:(Integer)redisService.get(key+"_counts_24_hours");
//        int count24Hours = redisTemplate.opsForValue().get(key+"_counts_24_hours")==null?0:(Integer)redisTemplate.opsForValue().get(key+"_counts_24_hours");

        if(count24Hours<=30){
//            redisTemplate.opsForValue().set(key+"_counts_24_hours",count24Hours+1,new Long(24*60*60));
            redisService.set(key+"_counts_24_hours",count24Hours+1,new Long(24*60*60));
        }else {

            return ResultDto.processError("同一个用户24小时内只能发送30次信息。");
        }

        if (countHours <= 15){
//            redisTemplate.opsForValue().set(key+"_counts_1_hours",countHours+1,new Long(60*60));
            redisService.set(key+"_counts_1_hours",countHours+1,new Long(60*60));
        }else {
            return ResultDto.processError("同一个用户一小时只能发送15次信息。");
        }

        //有效期（分钟）
        int interval = 3;
        //生成验证码，三分钟有效
        String validCode = ValidationCodeUtil.createCode(mobile,interval).getVc().getCode();
        // 短信内容
        String msg = "【小画智能】"+validCode+"(验证码),"+interval+"分钟内有效,请勿向任何人泄露。";


        ResponseData responseData =  messageServiceRemoteApi.sendMessageInfo(mobile,null,"user-service",msg);
        if(responseData.getCode()==200){
            //放入redis
            redisService.set(key,validCode,new Long(interval*60));
            return ResultDto.addOperationSuccess();
        }else {
            return ResultDto.processError  ("网络异常，请重试！");
        }
    }

    *//**
     * 忘记密码
     * @param reqMap
     * @return
     *//*
    @ApiOperation(value="忘记密码", notes="忘记密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name="mobile",value="手机号码",paramType="query",dataType="String"),
            @ApiImplicitParam(name="code",value="验证码",paramType="query",dataType="String"),
            @ApiImplicitParam(name="newPassword1",value="新密码1",paramType="query",dataType="String"),
            @ApiImplicitParam(name="newPassword2",value="新密码2",paramType="query",dataType="String")
    })
    @PostMapping("/forgetPassword")
    public ResultDto forgetPassword(@RequestBody Map<String,Object> reqMap, HttpServletRequest request){

        String mobile = reqMap.get("mobile").toString();
        String code = reqMap.get("code").toString();
        String newPassword1 = reqMap.get("newPassword1").toString();
        String newPassword2 = reqMap.get("newPassword2").toString();

//        if(!"dev".equals(devModel)){  //dev环境直接入库。
            String ipAddr = MySystemUtils.getIpAddr(request);
            String key = ipAddr+"_"+mobile;
            //校验短信验证码
            String scode = (String) redisService.get(key);
            if(!StringUtils.equals("888888",code)){
                if(!StringUtils.equals(scode,code)){
                    return  ResultDto.processError  ("短信验证码有误，请重试！");
                }else {
                    //删除键值对
                    redisService.remove(key);
                }
            }

//        }

        //一致性校验
        if(!newPassword1.equals(newPassword2)){
            return ResultDto.processError("两次输入的密码不一致，请重试！");
        }

//        AclUser currentAclUser =userService.queryAclUserByMobile(mobile);

        String password = passwordEncoder().encode(newPassword1);


        try{
            userService.forgetpassword("",mobile,"",password);
            return ResultDto.addOperationSuccess();

        }catch (Exception e){
            return ResultDto.processError  (e.getMessage());
        }


    }




    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(4);
    }*/
}
