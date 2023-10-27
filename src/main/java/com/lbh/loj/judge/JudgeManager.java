package com.lbh.loj.judge;


import com.lbh.loj.judge.codesandbox.model.JudgeInfo;
import com.lbh.loj.judge.strategy.DefaultJudgeStrategy;
import com.lbh.loj.judge.strategy.JavaLanguageJudgeStrategy;
import com.lbh.loj.judge.strategy.JudgeContext;
import com.lbh.loj.judge.strategy.JudgeStrategy;
import com.lbh.loj.model.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

/**
 * 判题管理（简化调用）
 */
@Service
public class JudgeManager {

    /**
     * 执行判题
     *
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext) {
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if ("java".equals(language)) {
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }

}
