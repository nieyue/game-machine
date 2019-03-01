package com.nieyue.service;

import com.nieyue.bean.Mer;
import com.nieyue.bean.Number;

/**
 * 商品逻辑层接口
 * @author yy
 *
 */
public interface MerService extends BaseService<Mer, Long>{
    /**
     * 商品抓取
     */
    Number merCatch(Long accountId, Long merId, Integer cardType);
}
