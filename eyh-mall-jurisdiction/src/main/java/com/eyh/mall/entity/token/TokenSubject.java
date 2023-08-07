package com.eyh.mall.entity.token;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 李平
 * @Date 2023-2-14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenSubject {

    private String userName;
    private String userRole;
    private String sessionId;
    private String lastAccessTime;

}
