package com.lbh.loj.judge.codesandbox;

import com.lbh.loj.judge.codesandbox.impl.RemoteCodeSandbox;
import com.lbh.loj.judge.codesandbox.model.ExecuteCodeRequest;
import com.lbh.loj.judge.codesandbox.model.ExecuteCodeResponse;
import com.lbh.loj.model.enums.QuestionSubmitLanguageEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;

import java.util.Arrays;
import java.util.List;

/**
 * ClassName: CodeSandboxTest
 * Package: com.lbh.loj.judge.codesandbox
 * Description:
 *
 * @Author luboheng
 * @Create 2023/10/20 20:49
 * @Version 1.0
 */
@SpringBootTest
public class CodeSandboxTest {
    @Value("${codesandbox.type:example}")
    private String type;

    @Test
    void executeCode() {
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
        String code = "int main() { }";
        String language = QuestionSubmitLanguageEnum.JAVA.getValue();
        List<String> inputList = Arrays.asList("1 2", "3 4");
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        Assertions.assertNotNull(executeCodeResponse);
    }
}
