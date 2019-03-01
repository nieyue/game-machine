package com.nieyue.service;

import com.nieyue.bean.Card;

/**
 * 卡片逻辑层接口
 * @author yy
 *
 */
public interface CardService extends BaseService<Card, Long>{
    /**
     * 卡片合成
     */
    Card compose(Long accountId, Long cardId);
}
