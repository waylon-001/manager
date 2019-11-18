package cc.mrbird.febs.common.enums;

/**
 * Created by zhang on 16/8/5.
 */
public enum CommonParameterEnum implements BaseEnum {

    GROUP_ROOM_TYPE_NN (0,"GROUP_ROOM_TYPE_NN","群房间类型 牛牛"),
    GROUP_ROOM_TYPE_JH (1,"GROUP_ROOM_TYPE_JH","群房间类型 金花"),

    GROUP_ROOM_STATUS_ZC (0,"GROUP_ROOM_TYPE_NN","正常"),
    GROUP_ROOM_STATUS_JZ (1,"GROUP_ROOM_TYPE_JH","禁用"),
    ;

    private int id;

    private String code;

    private String label;

    CommonParameterEnum(int id,String code,String label){
        this.id = id;
        this.code = code;
        this.label = label;
    }

    @Override
    public boolean isSuccess() {
        return true;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getLabel() {
        return this.label;
    }
}
