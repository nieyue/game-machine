package com.nieyue.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.nieyue.bean.Account;
import com.nieyue.bean.Finance;
import com.nieyue.exception.AccountIsNotExistException;
import com.nieyue.exception.CommonRollbackException;
import com.nieyue.exception.PayException;
import com.nieyue.service.AccountService;
import com.nieyue.service.FinanceService;
import com.nieyue.util.Arith;
import com.nieyue.util.MyDom4jUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FinanceServiceImpl extends BaseServiceImpl<Finance,Long> implements FinanceService {
    @Autowired
    AccountService accountService;
    //管理员充值
    @Transactional(propagation= Propagation.REQUIRED)
    @Override
    public Finance rechargeFinance(Long accountId, Integer method, Double money) {
        if(money<=0){
            throw new CommonRollbackException("金额必须大于0");	//账户不存在
        }
        Account a = accountService.load(accountId);
        if(a==null){
            throw new AccountIsNotExistException();	//账户不存在
        }
		/*if(a.getAuth()==null||a.getAuth()==0){//没认证
			throw new AccountNotAuthException();//账户未认证
		}*/
		/*if(a.getAuth()==1){//审核中
			throw new AccountAuthAuditException();//账户审核中
		}*/
        //财务增加
        Wrapper<Finance> wrapper=new EntityWrapper<>();
        Map<String,Object> map=new HashMap<>();
        map.put("account_id", accountId);
        wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
        List<Finance> fl = super.list(1, 1, null, null, wrapper);
        if(fl.size()<=0){
            throw new PayException();
        }
        Finance finance = fl.get(0);
        finance.setUpdateDate(new Date());
        finance.setMoney(Arith.add(finance.getMoney(),money));
        finance.setRecharge(Arith.add(finance.getRecharge(),money));
        boolean b = super.update(finance);
        if(!b){
            throw new PayException();
        }
        return finance;
    }
}
