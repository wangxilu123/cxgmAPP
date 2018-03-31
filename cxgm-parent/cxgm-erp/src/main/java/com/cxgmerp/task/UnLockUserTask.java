package com.cxgmerp.task;
/**
* @Description 定时任务解锁用户
*/

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UnLockUserTask {

    private static Logger log = LoggerFactory.getLogger(UnLockUserTask.class);
    
    
    @Scheduled(cron = "0 0/1 * * * ? ")// 每分钟执行
    public void unLockUser() {
        
//        List<AclUser> aclUsers = aclUserService.getNeedLockedUser();
//        if (aclUsers != null) {
//            for (AclUser aclUser : aclUsers) {
//                log.info("unLockUser ==" + aclUser.getUserName());
//                aclUserService.unLockUserByMobile(aclUser.getMobile());
//            }
//        }
//        
    }
    
}
