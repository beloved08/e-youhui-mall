package com.eyh.mall.easyexcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;
import com.eyh.mall.mapper.CommodityMapper;
import com.eyh.mall.service.CommodityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author 李平
 * @NAME EasyExcelListener
 * @PRODUCT_NAME IntelliJ IDEA
 * @PROJECT_NAME graduation_design
 * @MONTH_NAME_FULL 三月
 * @@DAY_NAME_FULL 星期日
 * @Date 2023-03-19 10:49:54
 * @Description easyExcel监听类
 */
@Slf4j
public class EasyExcelListener<T> implements ReadListener<T> {

    @Autowired
    private CommodityService commodityService;

    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;
    /**
     * 缓存的数据
     */
    private List<T> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    private final List<T> dataList = new ArrayList<T>();

    /**
     * 调用
     * 这个每一条数据解析都会来调用
     *
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context 上下文
     */
    @Override
    public void invoke(T data, AnalysisContext context) {
        log.info("解析到一条数据:{}", JSON.toJSONString(data));
        cachedDataList.add(data);
        dataList.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    /**
     * 毕竟做分析
     * 所有数据解析完成了 都会来调用
     *
     * @param context 上下文
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        log.info("---------------------所有数据解析完成---------------------");
    }

    private void saveData() {
        log.info("{}条数据，开始存储数据库！", cachedDataList.size());

        // commodityService.saveBatch();

        log.info("存储数据库成功！");
    }

    /**
     * 获取缓存数据列表
     *
     * @return {@link List}<{@link T}>
     */
    public List<T> getCachedDataList(){
        return dataList;
    }

}
