package com.sioo.point.bo;


import lombok.*;

/****
 * @description：
 * @version：1.0.0
 * @author fanghuaiming
 * @data Created in 2019/10/18 1:24 PM
 *
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class IdempotentEntity {

    private Integer channelTemplateId;
    private String messageName;
    private String mobiles;

}
