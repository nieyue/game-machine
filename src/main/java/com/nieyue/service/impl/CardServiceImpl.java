package com.nieyue.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.nieyue.bean.*;
import com.nieyue.exception.CommonRollbackException;
import com.nieyue.service.*;
import com.nieyue.util.MyDom4jUtil;
import com.nieyue.util.SnowflakeIdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CardServiceImpl extends BaseServiceImpl<Card,Long> implements CardService {
    @Autowired
    private AccountService accountService;
    @Autowired
    private ReceiptInfoService receiptInfoService;
    @Autowired
    private MerService merService;
    @Autowired
    private MerOrderService merOrderService;
    @Autowired
    private MerOrderDetailService merOrderDetailService;
    @Transactional(propagation= Propagation.REQUIRED)
    @Override
    public Card compose(Long accountId, Long cardId) {
        //判断账户
        Account account = accountService.load(accountId);
        if(ObjectUtils.isEmpty(account)){
            throw new CommonRollbackException("账户不存在");
        }
        //判断卡片
        Card card= super.load(cardId);
        if(ObjectUtils.isEmpty(card)){
            throw new CommonRollbackException("卡片不存在");
        }
        if(card.getCardNumber1()<=0
        ||card.getCardNumber2()<=0
        ||card.getCardNumber3()<=0
        ||card.getCardNumber4()<=0
        ){
            throw new CommonRollbackException("合成缺少卡片");
        }
        // 判断收货地址
        Wrapper<ReceiptInfo> wrapper=new EntityWrapper<>();
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("account_id", accountId);
        wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
        List<ReceiptInfo> receiptInfoList = receiptInfoService.simplelist(wrapper);
        if(receiptInfoList.size()<=0|| StringUtils.isEmpty(receiptInfoList.get(0).getAddress())){
            throw new CommonRollbackException("请完善配置中心");
        }
        Long merId = card.getMerId();
        Mer mer = merService.load(merId);
        card.setMer(mer);
        if(ObjectUtils.isEmpty(mer)){
            throw new CommonRollbackException("商品不存在");
        }
        //合成减数量
        card.setCardNumber1(card.getCardNumber1()-1);
        card.setCardNumber2(card.getCardNumber2()-1);
        card.setCardNumber3(card.getCardNumber3()-1);
        card.setCardNumber4(card.getCardNumber4()-1);
        card.setUpdateDate(new Date());
        super.update(card);
        //生成订单
        MerOrder merOrder=new MerOrder();
        merOrder.setAccountId(accountId);
        merOrder.setCreateDate(new Date());
        merOrder.setUpdateDate(new Date());
        merOrder.setOrderNumber(SnowflakeIdWorker.getId().toString());
        merOrderService.add(merOrder);
        MerOrderDetail merOrderDetail=new MerOrderDetail();
        merOrderDetail.setCreateDate(new Date());
        merOrderDetail.setUpdateDate(new Date());
        merOrderDetail.setAddress(receiptInfoList.get(0).getAddress());
        merOrderDetail.setMerName(mer.getName());
        merOrderDetail.setImgAddress(mer.getImgAddress());
        merOrderDetail.setMerOrderId(merOrder.getMerOrderId());
        merOrderDetail.setStatus(1);//订单状态，1待发货，2已发货
        merOrderDetailService.add(merOrderDetail);
        return card;
    }
}
