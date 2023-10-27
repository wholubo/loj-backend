package com.lbh.loj.judge.strategy;

import com.lbh.loj.judge.codesandbox.model.JudgeInfo;
import com.lbh.loj.model.dto.question.JudgeCase;
import com.lbh.loj.model.entity.Question;
import com.lbh.loj.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * ClassName: JudgeContext
 * Package: com.lbh.loj.judge.strategy
 * Description:
 *
 * @Author luboheng
 * @Create 2023/10/21 19:53
 * @Version 1.0
 */
@Data
public class JudgeContext {
    private JudgeInfo judgeInfo;

    private List<String> inputList;

    private List<String> outputList;

    private List<JudgeCase> judgeCaseList;

    private Question question;

    private QuestionSubmit questionSubmit;
}
