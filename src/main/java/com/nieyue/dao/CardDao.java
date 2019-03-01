package com.nieyue.dao;

import com.nieyue.bean.Card;

/**
 * 卡片数据库接口
 * @author yy
 *
 */
public interface CardDao extends BaseDao<Card> {
    /**
     * 卡片合成
     */
    Card compose(Long accountId, Long cardId);
}
