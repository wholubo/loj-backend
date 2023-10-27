package com.lbh.loj.judge.strategy;

import com.lbh.loj.judge.codesandbox.model.JudgeInfo;
//策略模式
/**
 *判题策略
 * @Author luboheng
 * @Create 2023/10/21 19:50
 * @Version 1.0
 */
public interface JudgeStrategy {
        JudgeInfo doJudge(JudgeContext judgeContext);
}
