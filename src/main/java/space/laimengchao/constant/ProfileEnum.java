package space.laimengchao.constant;

/**
 * 环境
 */
public enum ProfileEnum {

    LOCAL("local"),
    DEV("dev");

    private String profileName;

    ProfileEnum(String profileName){
        this.profileName = profileName;
    }

    public String getProfileName() {
        return profileName;
    }

}
