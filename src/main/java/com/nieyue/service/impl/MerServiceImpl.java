package com.nieyue.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.nieyue.bean.Account;
import com.nieyue.bean.Card;
import com.nieyue.bean.Mer;
import com.nieyue.bean.Number;
import com.nieyue.exception.CommonRollbackException;
import com.nieyue.service.AccountService;
import com.nieyue.service.CardService;
import com.nieyue.service.MerService;
import com.nieyue.service.NumberService;
import com.nieyue.util.MyDom4jUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MerServiceImpl extends BaseServiceImpl<Mer,Long> implements MerService {
    @Autowired
    private NumberService numberService;
    @Autowired
    private CardService cardService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private MerService merService;

    @Transactional(propagation= Propagation.REQUIRED)
    @Override
    public Number merCatch(Long accountId, Long merId, Integer cardType) {
        //判断mer
        Mer mer = merService.load(merId);
        if(ObjectUtils.isEmpty(mer)){
            throw new CommonRollbackException("不存在");
        }
        //判断账户
        Account account = accountService.load(accountId);
        if(ObjectUtils.isEmpty(account)){
            throw new CommonRollbackException("账户不存在");
        }
        // 减次数
        Wrapper<Number> wrapper=new EntityWrapper<>();
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("account_id", accountId);
        wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
        List<Number> numberList = numberService.simplelist(wrapper);
        Number number = null;
        if(numberList.size()>0){
            number = numberList.get(0);
            if(number.getSurplusNumber()<=0){
                throw new CommonRollbackException("次数不够");
            }
            number.setUseNumber(number.getUseNumber()+1);
            number.setSurplusNumber(number.getSurplusNumber()-1);
            number.setUpdateDate(new Date());
            numberService.update(number);
        }
        //卡片类型，0失败，1袋身卡，2面料卡，3手挽卡,4五金卡
        Wrapper<Card> wrapper2=new EntityWrapper<>();
        Map<String,Object> map2=new HashMap<String,Object>();
        map2.put("account_id", accountId);
        map2.put("mer_id", merId);
        wrapper2.allEq(MyDom4jUtil.getNoNullMap(map2));
        if(cardType.equals(0)){
            //抓取失败，
        }else {
            List<Card> cardList = cardService.simplelist(wrapper2);
            if(cardList.size()>0){
                Card card = cardList.get(0);
                card.setUpdateDate(new Date());
                if(cardType.equals(1)){
                    card.setCardNumber1(card.getCardNumber1()+1);
                }else if(cardType.equals(2)){
                    card.setCardNumber2(card.getCardNumber2()+1);
                }else if(cardType.equals(3)){
                    card.setCardNumber3(card.getCardNumber3()+1);
                }else if(cardType.equals(4)){
                    card.setCardNumber4(card.getCardNumber4()+1);
                }
                cardService.update(card);
            }else{
                Card card = new Card();
                card.setCreateDate(new Date());
                card.setUpdateDate(new Date());
                card.setCardNumber1(0);
                card.setCardNumber2(0);
                card.setCardNumber3(0);
                card.setCardNumber4(0);
                if(cardType.equals(1)){
                    card.setCardNumber1(card.getCardNumber1()+1);
                }else if(cardType.equals(2)){
                    card.setCardNumber2(card.getCardNumber2()+1);
                }else if(cardType.equals(3)){
                    card.setCardNumber3(card.getCardNumber3()+1);
                }else if(cardType.equals(4)){
                    card.setCardNumber4(card.getCardNumber4()+1);
                }
                card.setMerId(merId);
                card.setAccountId(accountId);
                cardService.add(card);
            }
        }
        return number;
    }
}
