package cc.mrbird.febs.common.enums;

import java.io.Serializable;

public interface BaseEnum extends Serializable {

    boolean isSuccess();

    int  getId();

    String getCode();

    String getLabel();
}
