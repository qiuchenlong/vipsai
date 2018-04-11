package com.vs.vipsai.bean;

import java.io.Serializable;

/**
 * Author: cynid
 * Created on 4/8/18 3:08 PM
 * Description:
 */

public class popularData implements Serializable {

    private String slug;
    private Tournament tournament;
    private String time_left;

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public String getTime_left() {
        return time_left;
    }

    public void setTime_left(String time_left) {
        this.time_left = time_left;
    }





    public class Tournament implements Serializable {

        private String title;
        private Image_urls image_urls;
        private Owner owner;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Image_urls getImage_urls() {
            return image_urls;
        }

        public void setImage_urls(Image_urls image_urls) {
            this.image_urls = image_urls;
        }

        public Owner getOwner() {
            return owner;
        }

        public void setOwner(Owner owner) {
            this.owner = owner;
        }


    }



    public static class Image_urls implements Serializable {
        private String i360;
        private String i720;

        public String getI360() {
            return i360;
        }

        public void setI360(String i360) {
            this.i360 = i360;
        }

        public String getI720() {
            return i720;
        }

        public void setI720(String i720) {
            this.i720 = i720;
        }
    }


    public static class Owner implements Serializable {

        private String username;
        private Profile profile;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public Profile getProfile() {
            return profile;
        }

        public void setProfile(Profile profile) {
            this.profile = profile;
        }
    }

    public static class Profile implements Serializable {

        private String avatar_url;

        public String getAvatar_url() {
            return avatar_url;
        }

        public void setAvatar_url(String avatar_url) {
            this.avatar_url = avatar_url;
        }

    }
}
