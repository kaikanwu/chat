package com.kk.pojo.vo;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * VO 对象代表后端返回给前端的对象
 * 所以我们需要注释掉一些不需要返回的属性
 */
public class UsersVO {

    private String id;

    private String username;



    private String faceImage;

    private String faceImageBig;

    private String nickname;
    private String qrcode;

    //private String password;
    //private String cid;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFaceImage() {
        return faceImage;
    }

    public void setFaceImage(String faceImage) {
        this.faceImage = faceImage;
    }

    public String getFaceImageBig() {
        return faceImageBig;
    }

    public void setFaceImageBig(String faceImageBig) {
        this.faceImageBig = faceImageBig;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }
}