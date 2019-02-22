/**
 * 业务方法
 */
export default {
    
    /*
    ** 判断是否超级管理员，是就显示账户管理
    **
    */
    getIsSuperAdmin(){
        let isSuperAdmin=false;
        if(sessionStorage.getItem("account")){
            let account=JSON.parse(sessionStorage.getItem("account"));
            if(account.role.name=="超级管理员"){
                isSuperAdmin=true;
            }
        } 
        return isSuperAdmin;
    },
    /*
    ** 判断是否车主，是就显示账户管理
    **
    */
    getIsCarAdmin(){
        let isCarAdmin=false;
        if(sessionStorage.getItem("account")){
            let account=JSON.parse(sessionStorage.getItem("account"));
            if(account.role.name=="车主"){
                isCarAdmin=true;
            }
        } 
        return isCarAdmin;
    },
    /*
    ** 判断是否用户，是就显示账户管理
    **
    */
    getIsUserAdmin(){
        let isUserAdmin=false;
        if(sessionStorage.getItem("account")){
            let account=JSON.parse(sessionStorage.getItem("account"));
            if(account.role.name=="用户"){
                isUserAdmin=true;
            }
        } 
        return isUserAdmin;
    },
    /*
    ** 获取账户
    **
    */
    getAccount(){
        let account=JSON.parse(sessionStorage.getItem("account"));
        return account;
    }
    
}