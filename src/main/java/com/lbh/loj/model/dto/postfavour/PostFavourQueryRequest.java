package com.lbh.loj.model.dto.postfavour;

import com.lbh.loj.common.PageRequest;
import com.lbh.loj.model.dto.post.PostQueryRequest;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 帖子收藏查询请求
 *
 * @author <a href="https://gitee.com/luboheng">wholuobo</a>
 * @from <a href="https://gitee.com/luboheng">编程导航知识星球</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PostFavourQueryRequest extends PageRequest implements Serializable {

    /**
     * 帖子查询请求
     */
    private PostQueryRequest postQueryRequest;

    /**
     * 用户 id
     */
    private Long userId;

    private static final long serialVersionUID = 1L;
}