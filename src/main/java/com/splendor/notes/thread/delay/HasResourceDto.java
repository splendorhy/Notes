package com.splendor.notes.thread.delay;

import com.splendor.notes.thread.reactor.HasResourceInfo;
import lombok.*;

import java.io.Serializable;

/**
 * @author splendor.s
 * @create 2022/4/28 下午12:01
 */
@Data
@Builder
@ToString(callSuper = false)
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class HasResourceDto implements Serializable {

    private HasResourceInfo hasResourceInfo;
}

