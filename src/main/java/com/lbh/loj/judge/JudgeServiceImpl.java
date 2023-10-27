package com.lbh.loj.judge;

import cn.hutool.json.JSONUtil;
import com.lbh.loj.common.ErrorCode;
import com.lbh.loj.exception.BusinessException;
import com.lbh.loj.judge.codesandbox.CodeSandbox;
import com.lbh.loj.judge.codesandbox.CodeSandboxFactory;
import com.lbh.loj.judge.codesandbox.model.ExecuteCodeRequest;
import com.lbh.loj.judge.codesandbox.model.ExecuteCodeResponse;
import com.lbh.loj.judge.codesandbox.model.JudgeInfo;
import com.lbh.loj.judge.strategy.JudgeContext;
import com.lbh.loj.model.dto.question.JudgeCase;
import com.lbh.loj.model.dto.question.JudgeConfig;
import com.lbh.loj.model.entity.Question;
import com.lbh.loj.model.entity.QuestionSubmit;
import com.lbh.loj.model.enums.JudgeInfoMessageEnum;
import com.lbh.loj.model.enums.QuestionSubmitStatusEnum;
import com.lbh.loj.model.vo.QuestionSubmitVO;
import com.lbh.loj.service.QuestionService;
import com.lbh.loj.service.QuestionSubmitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName: JudgeServiceImpl
 * Package: com.lbh.loj.judge
 * Description:
 *
 * @Author luboheng
 * @Create 2023/10/21 15:03
 * @Version 1.0
 */
@Service
public class JudgeServiceImpl implements JudgeService {
    @Resource
    private QuestionService questionService;
    @Resource
    private QuestionSubmitService questionSubmitService;
    @Value("${codesandbox.type:example}")
    private String type;
    @Resource
    private JudgeManager judgeManager;
    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        //1)传入题目提交id.获取到对应题目的信息,包括代码、编程语言
        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "请求信息不存在");
        }
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存在");
        }
        //2)判断题目状态是否为等待中,若不为等待中则不执行,防止用户重复提交
        if (!questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目正在判题中!");
        }
        //3)题目提交后,立即更改题目状态为"判题中",防止重复判题,同时用户可以实时看到题目状态
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean update = questionSubmitService.updateById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新错误");
        }
        //4)调用代码沙箱,获取到执行结果
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
        String code = questionSubmit.getCode();
        String language = questionSubmit.getLanguage();
        String judgeCaseStr = question.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        List<String> outputList = executeCodeResponse.getOutputList();
        //5)根据执行结果,更改题目的状态和信息
 /*用策略模式优化
       //首先判断代码沙箱输出结果数量和预期结果数量是否一致
        JudgeInfoMessageEnum judgeInfoMessageEnum = JudgeInfoMessageEnum.WAITING;
        if(outputList.size() != inputList.size()) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
            return null;
        }
        //比对每一项输出结果与预期结果是否一致
        for (int i = 0; i < judgeCaseList.size(); i++) {
            JudgeCase judgeCase = judgeCaseList.get(i);
            if (!outputList.get(i).equals(judgeCase.getOutput())) {
                judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
                return null;
            }
        }
        //是否满足题目限制
        JudgeInfo judgeInfo = executeCodeResponse.getJudgeInfo();
        Long memory = judgeInfo.getMemory();
        Long time = judgeInfo.getTime();
        String judgeConfigStr = question.getJudgeConfig();
        JudgeConfig needJudgeInfo = JSONUtil.toBean(judgeConfigStr, JudgeConfig.class);
        Long needTimeLimit = needJudgeInfo.getTimeLimit();
        Long needMemoryLimit = needJudgeInfo.getMemoryLimit();
        if (memory > needMemoryLimit) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED;
            return null;
        }
        if (time > needTimeLimit) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED;
            return null;
        }
        //可能还有其他异常情况
        return null;*/
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(outputList);
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setQuestion(question);
        judgeContext.setQuestionSubmit(questionSubmit);
        JudgeInfo judgeInfo = judgeManager.doJudge(judgeContext);
        // 6）修改数据库中的判题结果
        questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        update = questionSubmitService.updateById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新错误");
        }
        QuestionSubmit questionSubmitResult = questionSubmitService.getById(questionId);
        return questionSubmitResult;
    }
}
