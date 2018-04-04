package com.cxgm.controller;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController{
   /* 
    @Autowired
    private CoreMessageSource messageSource;

    *//**
     * 获取CsrfToken
     *
     * @param request
     * @return
     *//*
    @ApiOperation(value = "获取CsrfToken和SessionId", httpMethod = "GET", response = ResultDto.class, notes = "获取CsrfToken和SessionId")
    @RequestMapping(value = "/getCsrfToken", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getCsrfToken(HttpServletRequest request) {
        request.getSession().getAttributeNames();
//             CsrfToken csrfToken = (CsrfToken) request.getAttribute("_csrf");
        String sessionId=request.getSession().getId();
        Map<String,Object> map =  new HashMap<String,Object>();
//             map.put("csrfToken", csrfToken);
        map.put("CSESSIONID", sessionId);
        map.put("code", 200);
        ResponseEntity response = ResultDto.OK(map);

        return response;

    }
//    
//    @RequestMapping(value = "/login", method = RequestMethod.GET)
//    public String login() throws SQLException{
//        
//        return "请重新登录";
//        
//    }
    
    @RequestMapping(value = "/getToken", method = RequestMethod.GET)
    public String getToken(HttpServletRequest request) throws SQLException{
        //TODO 将ValidationCodeWrap.image 已流的形式直接输出给前台
        ValidationCodeWrap codeWrap = ValidationCodeUtil.getSesionCode();
        request.getSession().setAttribute("_validationCode", codeWrap.getVc());
        return codeWrap.getVc().getCode();
        
    }
    
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index() throws SQLException{
        
        return new ModelAndView("index");
    }
    
    @RequestMapping(value = "/loginexpired", method = RequestMethod.GET)
    public String loginexpired() throws SQLException{
        
        return "被踢出了";
    }
    
    *//**  
     * 切换国际化语言<p>
     * lang=zh_CN 为中文<br>
     * lang=en_US 为英文
     * <p>
     * zh_CN和en_US 为国际化配置文件后缀
     * @Title changeLang  
     * @param lang
     * @return String   
     *//*  
    @GetMapping("/changeLang")
    public String changeLang(@RequestParam(required = false) String lang){
        return messageSource.getMessage("test.addSuccess");
    }


    @Autowired
    private AclUserService userService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private HttpServletRequest request;

    @Value("${spring.cloud.config.profile}")
    private String devModel;

    @Autowired
    private AsyncTask asyncTask;

    *//**
     * 用户注册
     * @param registerEntity
     * @return
     *//*
    @PostMapping("/register")
    public ResultDto register(@Valid @RequestBody RegisterEntity registerEntity , BindingResult bindingResult) throws InterruptedException {

        if(bindingResult.hasErrors()){
            return  ResultDto.error(bindingResult.getFieldError().getDefaultMessage());
        }


        //校验密码一致性
        if(!registerEntity.getPassword().equals(registerEntity.getPassword2())){
//            throw new RuntimeServiceException("两次输入密码不一致！");
            return  ResultDto.error("两次输入密码不一致！");
        }


        AclUser user =  userService.queryAclUserByMobile(registerEntity.getMobile());
        if(user!=null){
            return  ResultDto.error("该手机号已存在，请重试！");
        }

        AclUser user1 = userService.findAclUserByName(registerEntity.getUserName());
        if(user1!=null){
            return  ResultDto.error("该用户名已存在，请重试！");
        }



        System.out.println(devModel);

        if(!"dev".equals(devModel)){//dev环境直接入库。
            String ipAddr = MySystemUtils.getIpAddr(request);

            String key = ipAddr+"_"+registerEntity.getMobile();
            //校验短信验证码
            String code = (String) redisService.get(key);
            //删除键值对
            redisService.remove(key);
            if(!StringUtils.equals("888888",registerEntity.getMobileValidCode())&&
                    !StringUtils.equals(code,registerEntity.getMobileValidCode())){
                return  ResultDto.error("短信验证码有误，请重试！");
            }
        }


        AclUser user2 = new AclUser();
        user2.setUserName(registerEntity.getUserName());
        String password = passwordEncoder().encode(registerEntity.getPassword());
        user2.setUserPwd(password);
        user2.setRoleIds("4");
        user2.setMobile(registerEntity.getMobile());
        int id = userService.addUser(user2);

        asyncTask.createAccount(registerEntity.getMobile());

        Map<String,Object> map =  new HashMap<String,Object>();
        if(id <0){
            ResponseEntity response = ResultDto.INTERNAL_SERVER_ERROR();
            return ResultDto.error("网络连接超时");
        }
        return new ResultDto("注册成功");
    }

    *//**
     * 用户兴趣爱好
     * @param reqMap
     * @return
     *//*
    @PostMapping("/interest")
    public ResponseEntity interest(@RequestBody Map<String,Object> reqMap ){

        String userName = reqMap.get("userName").toString();
        String types = reqMap.get("types").toString();

        try {
            String[] num = types.split(",");
            for (String s : num) {
                Integer.parseInt(s);
            }

        }catch (Exception e){
            return  ResultDto.BAD_REQUEST("传入参数有误，请重试！"+e.getMessage());
        }



        if(StringUtils.isEmpty(userName)){
            return  ResultDto.BAD_REQUEST("传入参数有误，请重试！");
        }

        AclUser user = userService.findAclUserByName(userName);
        if(user==null){
            return  ResultDto.BAD_REQUEST("该手机号已存在，请重试！");
        }

        user.setInterest(types);

        userService.updateUserByUserName(user);

        Map<String,Object> map =  new HashMap<String,Object>();
        map.put("code", 200);
        map.put("message","interest success");
        ResponseEntity response = ResultDto.OK(map);
        return response;
    }

//    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(4);
    }
*/


}
