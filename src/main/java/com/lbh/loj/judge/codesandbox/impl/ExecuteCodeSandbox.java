package com.lbh.loj.judge.codesandbox.impl;

import com.lbh.loj.judge.codesandbox.CodeSandbox;
import com.lbh.loj.judge.codesandbox.model.ExecuteCodeRequest;
import com.lbh.loj.judge.codesandbox.model.ExecuteCodeResponse;
import com.lbh.loj.judge.codesandbox.model.JudgeInfo;
import com.lbh.loj.model.enums.JudgeInfoMessageEnum;
import com.lbh.loj.model.enums.QuestionSubmitStatusEnum;

import java.util.List;

/**
 * 示例代码沙箱
 * ClassName: ExecuteCodeSandbox
 * Package: com.lbh.loj.judge.codesandbox.impl
 * Description:
 *
 * @Author luboheng
 * @Create 2023/10/20 18:48
 * @Version 1.0
 */
public class ExecuteCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("示例代码沙箱");
        List<String> inputList = executeCodeRequest.getInputList();
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(inputList);
        executeCodeResponse.setMessage("测试执行成功");
        executeCodeResponse.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getText());
        judgeInfo.setMemory(100L);
        judgeInfo.setTime(100L);
        executeCodeResponse.setJudgeInfo(judgeInfo);
        return executeCodeResponse;
    }
}
