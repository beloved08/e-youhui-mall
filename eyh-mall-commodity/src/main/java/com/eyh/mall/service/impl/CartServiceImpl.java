package com.eyh.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eyh.mall.entity.Cart;
import com.eyh.mall.entity.Classification;
import com.eyh.mall.entity.Commodity;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.merchantStores.Business;
import com.eyh.mall.entity.tdo.CardDto;
import com.eyh.mall.entity.tdo.CartDetailDto;
import com.eyh.mall.entity.tdo.MyCart;
import com.eyh.mall.entity.vo.CardPage;
import com.eyh.mall.feign.client.commodity.CommodityApiClient;
import com.eyh.mall.feign.client.merchantStores.MerchantStoresApiClient;
import com.eyh.mall.feign.client.user.UserApiClient;
import com.eyh.mall.feign.entity.MallUser;
import com.eyh.mall.mapper.CartMapper;
import com.eyh.mall.mapper.CommodityMapper;
import com.eyh.mall.service.CartService;
import com.eyh.mall.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Author 李平
 * @NAME CartServiceImpl
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期六
 * @Date 2023-03-25 13:40:14
 * @Description 购物车接口实现类
 */
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private CommodityMapper commodityMapper;

    @Autowired
    private UserApiClient userApiClient;

    @Autowired
    private CommodityApiClient commodityApiClient;

    /**
     * 添加购物车
     *
     * @param cart 车
     * @return {@link Result}
     */
    @Override
    public Result addCart(Cart cart) {
        LambdaQueryWrapper<Cart> cartLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //该用户
        cartLambdaQueryWrapper.eq(!"".equals(cart.getUserId()),Cart::getUserId,cart.getUserId());
        //该商品
        cartLambdaQueryWrapper.eq(!"".equals(cart.getCommodityId()),Cart::getCommodityId,cart.getCommodityId());
        //该商家
        cartLambdaQueryWrapper.eq(!"".equals(cart.getBusinessId()),Cart::getBusinessId,cart.getBusinessId());
        Cart cart1 = cartMapper.selectOne(cartLambdaQueryWrapper);
        if (cart1 == null){
            //没有添加过
            cart.setId(UUID.randomUUID().toString());
            cart.setCartId(UUID.randomUUID().toString());
            cart.setAddTime(TimeUtil.getCurrentTime());
            int insert = cartMapper.insert(cart);
            return insert > 0 ? Result.ok("添加购物车成功") : Result.err("添加购物车失败");
        }else{
            //添加过
            return Result.ok("已添加");
        }


    }

    /**
     * 删除购物车
     *
     * @param cart 车
     * @return {@link Result}
     */
    @Override
    public Result deleteCart(Cart cart) {
        LambdaQueryWrapper<Cart> cartLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //该用户
        cartLambdaQueryWrapper.eq(!"".equals(cart.getUserId()),Cart::getUserId,cart.getUserId());
        //该商品
        cartLambdaQueryWrapper.eq(!"".equals(cart.getCommodityId()),Cart::getCommodityId,cart.getCommodityId());
        //该商家
        cartLambdaQueryWrapper.eq(!"".equals(cart.getBusinessId()),Cart::getBusinessId,cart.getBusinessId());

        // 删除
        int delete = cartMapper.delete(cartLambdaQueryWrapper);
        return delete > 0 ? Result.ok("已移除") : Result.err("移除失败");
    }

    /**
     * 得到所有购物车页面
     *
     * @param cardPage 卡页面
     * @return {@link Result}
     */
    @Override
    public Result getAllCartByPage(CardPage cardPage) {
        IPage<Cart> page = new Page<Cart>(cardPage.getCurrentPage(), cardPage.getPageSize());

        //条件查询
        LambdaQueryWrapper<Cart> wrapper = new LambdaQueryWrapper<>();

        //查询用户
        if (!"".equals(cardPage.getUserName())){
            MallUser mallUserByName = userApiClient.getMallUserByName(cardPage.getUserName());
            wrapper.eq(!"".equals(mallUserByName.getUserId()),Cart::getUserId,mallUserByName.getUserId());
        }

        wrapper.ge(!"".equals(cardPage.getStartTime()),Cart::getAddTime,cardPage.getStartTime());
        wrapper.le(!"".equals(cardPage.getEndTime()),Cart::getAddTime,cardPage.getEndTime());

        IPage<Cart> selectPage = cartMapper.selectPage(page, wrapper);
        CardDto cardDto = new CardDto();
        cardDto.setTotal(selectPage.getTotal());
        cardDto.setCurrentPage(selectPage.getCurrent());
        cardDto.setPageSize(selectPage.getPages());
        cardDto.setSize(selectPage.getSize());

        ArrayList<CartDetailDto> dtos = new ArrayList<>();
        selectPage.getRecords().forEach(c -> {
            CartDetailDto d = new CartDetailDto();
            d.setAddTime(c.getAddTime());
            MallUser mallUser = userApiClient.getMallUserByUserId(c.getUserId());
            d.setUserName(mallUser.getRealName());
            d.setMallUser(mallUser);
            LambdaQueryWrapper<Commodity> commodityLambdaQueryWrapper = new LambdaQueryWrapper<>();
            commodityLambdaQueryWrapper.eq(!"".equals(c.getCommodityId()),Commodity::getCommodityId,c.getCommodityId());
            Commodity commodity = commodityMapper.selectOne(commodityLambdaQueryWrapper);
            d.setCommodityName(commodity.getCommodityName());
            d.setCommodity(commodity);

            Business business = commodityApiClient.getBusinessById(c.getBusinessId());
            d.setBusiness(business);
            d.setBusinessName(business.getBusinessName());
            dtos.add(d);
        });

        cardDto.setCartDetailDtoList(dtos);
        return Result.ok(cardDto);
    }

    /**
     * 获取用户购物车
     *
     * @param userId 用户id
     * @return {@link Result}
     */
    @Override
    public Result getUserCart(String userId) {
        LambdaQueryWrapper<Cart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!"".equals(userId),Cart::getUserId,userId);
        List<Cart> cartList = cartMapper.selectList(queryWrapper);

        List<MyCart> myCarts = new ArrayList<MyCart>();
        cartList.forEach(c -> {
            MyCart myCart = new MyCart();
            Commodity commodity = commodityMapper.selectOne(
                    new LambdaQueryWrapper<Commodity>()
                            .eq(!"".equals(c.getCommodityId()), Commodity::getCommodityId, c.getCommodityId()));
            myCart.setBusiness(commodityApiClient.getBusinessById(c.getBusinessId()));
            myCart.setCommodity(commodity);
            myCart.setQuantity(1);
            myCart.setSelected(true);
            myCarts.add(myCart);

        });

        return Result.ok(myCarts);
    }
}
