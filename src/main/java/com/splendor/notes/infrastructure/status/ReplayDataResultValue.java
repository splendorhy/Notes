package com.splendor.notes.infrastructure.status;

import lombok.Getter;
import lombok.Setter;

/**
 * @author splendor.s
 * @create 2022/12/29 下午6:59
 * @description
 */
@Getter
@Setter
public class ReplayDataResultValue {

    private String masterSecondBdfPath;

    private String masterFirstBdfPath;

    private String featureBdfPath;
}
