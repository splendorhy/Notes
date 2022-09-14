package com.splendor.notes.design.patterns.template.check;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author splendor.s
 * @create 2022/9/14 下午8:14
 * @description 申诉相关业务逻辑
 */
public class AppealService {

    @Autowired
    private AppealCheckService appealCheckService;
    @Autowired
    private AppealCreateCheck appealCreateCheck;
    @Autowired
    private AppealUpdateCheck appealUpdateCheck;
    @Autowired
    private AppealDeleteCheck appealDeleteCheck;

    /**
     * 申诉内容留档
     *
     * @param appealMatters 申诉内容
     */
    public void saveAppealMatters(AppealMatters appealMatters) throws Exception {
        /*1.基本必要参数检查*/
        CheckResponse checkResponse = appealCheckService.checkParam(appealMatters);
        if (!checkResponse.isPass()) {
            throw new Exception(checkResponse.getErrorMsg());
        }

        /*2.实际业务操作*/
        if (checkPosInteger(appealMatters.getId())) {
            checkResponse = appealCreateCheck.checkProcess(appealMatters);
            if (!checkResponse.isPass()) {
                throw new Exception(checkResponse.getErrorMsg());
            }
            /*业务处理，省略*/
        }
        checkResponse = appealUpdateCheck.checkProcess(appealMatters);
        if (!checkResponse.isPass()) {
            throw new Exception(checkResponse.getErrorMsg());
        }
        /*业务处理，省略*/
    }

    /**
     * 申诉内容留档
     *
     * @param appealMattersId 申诉内容id
     */
    public void deleteAppealMatters(Long appealMattersId) throws Exception {
        /*1.基本必要参数检查*/
        CheckResponse checkResponse = appealDeleteCheck.checkProcess(appealMattersId);
        if (!checkResponse.isPass()) {
            throw new Exception(checkResponse.getErrorMsg());
        }
        /*业务处理，省略*/
    }

    /**
     * ID信息合法性:必须为正整数
     *
     * @param id id信息
     * @return true-合法；false-非法
     */
    public static boolean checkPosInteger(Long id) {
        return null != id && id > 0L;
    }

}
