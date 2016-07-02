package cn.ifreedomer.com.androidguide.constants;

/**
 * @atuhor:eavawu
 * @date:4/28/16
 * @todo:
 */
public class HttpConstants {
    public static final String NAME = "name";
    public static final int SUCCESS = 1;
    public static final String USER = "user";
    public static final int FAILED = 0;
    public static final String PHONE = "phone";
    public static final String SEX = "sex";
    public static final String AVATAR = "avatar";
    public static final int MAN = 0;
    public static final int WOMEN = 1;

    public static final String PASSWORD = "password";
    public static final String TOKEN = "token";
    public static final int FOLLOWED = 1;
    public static final int UNFOLLOWED = 0;


    public static final String USERID_PATH = "/{userId}";
    public static final String FOLLOW = "follow/";

    public static final String FOLLOW_USER_PATH = FOLLOW+USERID_PATH;



    public static final String SIGN = "sign/";
    public static final String ISPHONE_REGISTER = HttpConstants.SIGN+"isPhoneRegister";
    public static final String SIGN_IN = HttpConstants.SIGN+"signIn";
    public static final String SIGN_UP = HttpConstants.SIGN+"signUp";


    public static final String SOCIAL_PATH = "social";
    public static final String GET_SOCIALS = SOCIAL_PATH+ "/getSocials";
    public static final String LIKE_SOCIAL = SOCIAL_PATH+ "/like";

    public static final String FOLLOW_STATUS_KEY = "followStatus";
    public static final String USERID = "userId";

    public static final int UNLIKE_STATUS = 0;
    public static final int LIKE_STATUS = 1;
    public static final String STATUS = "status";
    public static final String SOCIAL_ID = "socialId";
    public static final String UPDATE_USER = USER+ "/updateUser";
    public static final String COVER = "cover";
    public static final String SIGNTURE = "signture";
    public static final String GET_FOLLOWLIST =FOLLOW+ "getFollowList";
    public static final String PAGEINDEX = "pageIndex";
    public static final String GET_MINE_SOCIAL =SOCIAL_PATH+ "/getMineSocial";
    public static final String DEPLOY_SOCIAL = SOCIAL_PATH + "/deploySocial";
    public static final int SOCIAL_TYPE = 1;
    public static final String EMAIL = "email";
    public static final String CONFIRM_PWD = "confirm_pwd";
    public static final String ACCOUNT = "account";
}
