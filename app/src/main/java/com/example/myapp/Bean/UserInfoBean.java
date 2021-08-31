package com.example.myapp.Bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by laihm on 2021/8/27
 */
public class UserInfoBean {

    @SerializedName("data")
    private UserInfoDataBean userInfoDataBean;
    @SerializedName("errorCode")
    private Integer errorCode;
    @SerializedName("errorMsg")
    private String errorMsg;

    public UserInfoDataBean getData() {
        return userInfoDataBean;
    }

    public void setData(UserInfoDataBean userInfoDataBean) {
        this.userInfoDataBean = userInfoDataBean;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public class UserInfoDataBean {
        @SerializedName("coinInfo")
        private CoinInfoData coinInfo;
        @SerializedName("userInfo")
        private UserInfo userInfo;

        public CoinInfoData getCoinInfo() {
            return coinInfo;
        }

        public void setCoinInfo(CoinInfoData coinInfo) {
            this.coinInfo = coinInfo;
        }

        public UserInfo getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(UserInfo userInfo) {
            this.userInfo = userInfo;
        }

        public class CoinInfoData {
            @SerializedName("coinCount")
            private Integer coinCount;
            @SerializedName("level")
            private Integer level;
            @SerializedName("nickname")
            private String nickname;
            @SerializedName("rank")
            private String rank;
            @SerializedName("userId")
            private Integer userId;
            @SerializedName("username")
            private String username;

            public Integer getCoinCount() {
                return coinCount;
            }

            public void setCoinCount(Integer coinCount) {
                this.coinCount = coinCount;
            }

            public Integer getLevel() {
                return level;
            }

            public void setLevel(Integer level) {
                this.level = level;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getRank() {
                return rank;
            }

            public void setRank(String rank) {
                this.rank = rank;
            }

            public Integer getUserId() {
                return userId;
            }

            public void setUserId(Integer userId) {
                this.userId = userId;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }
        }

        public class UserInfo {
            @SerializedName("admin")
            private Boolean admin;
            @SerializedName("chapterTops")
            private List<?> chapterTops;
            @SerializedName("coinCount")
            private Integer coinCount;
            @SerializedName("collectIds")
            private List<Integer> collectIds;
            @SerializedName("email")
            private String email;
            @SerializedName("icon")
            private String icon;
            @SerializedName("id")
            private Integer id;
            @SerializedName("nickname")
            private String nickname;
            @SerializedName("password")
            private String password;
            @SerializedName("publicName")
            private String publicName;
            @SerializedName("token")
            private String token;
            @SerializedName("type")
            private Integer type;
            @SerializedName("username")
            private String username;

            public Boolean getAdmin() {
                return admin;
            }

            public void setAdmin(Boolean admin) {
                this.admin = admin;
            }

            public List<?> getChapterTops() {
                return chapterTops;
            }

            public void setChapterTops(List<?> chapterTops) {
                this.chapterTops = chapterTops;
            }

            public Integer getCoinCount() {
                return coinCount;
            }

            public void setCoinCount(Integer coinCount) {
                this.coinCount = coinCount;
            }

            public List<Integer> getCollectIds() {
                return collectIds;
            }

            public void setCollectIds(List<Integer> collectIds) {
                this.collectIds = collectIds;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getPublicName() {
                return publicName;
            }

            public void setPublicName(String publicName) {
                this.publicName = publicName;
            }

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }

            public Integer getType() {
                return type;
            }

            public void setType(Integer type) {
                this.type = type;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }
        }
    }
}
