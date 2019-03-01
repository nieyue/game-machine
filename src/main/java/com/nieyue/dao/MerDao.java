package com.nieyue.dao;

import com.nieyue.bean.Mer;
import com.nieyue.bean.Number;

/**
 * 商品数据库接口
 * @author yy
 *
 */
public interface MerDao extends BaseDao<Mer> {
    /**
     * 商品抓取
     */
    Number merCatch(Long accountId, Long merId, Integer cardType);

}
