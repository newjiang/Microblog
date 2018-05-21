package com.example.jiang.microblog.json;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jiang on 2018/5/22.
 */

public class Test {

    private boolean hasvisible;
    private int max_id;
    private int next_cursor;
    private int previous_cursor;
    private int since_id;
    private StatusBean status;
    private int total_number;

    public boolean isHasvisible() {
        return hasvisible;
    }

    public void setHasvisible(boolean hasvisible) {
        this.hasvisible = hasvisible;
    }

    public int getMax_id() {
        return max_id;
    }

    public void setMax_id(int max_id) {
        this.max_id = max_id;
    }

    public int getNext_cursor() {
        return next_cursor;
    }

    public void setNext_cursor(int next_cursor) {
        this.next_cursor = next_cursor;
    }

    public int getPrevious_cursor() {
        return previous_cursor;
    }

    public void setPrevious_cursor(int previous_cursor) {
        this.previous_cursor = previous_cursor;
    }

    public int getSince_id() {
        return since_id;
    }

    public void setSince_id(int since_id) {
        this.since_id = since_id;
    }

    public StatusBean getStatus() {
        return status;
    }

    public void setStatus(StatusBean status) {
        this.status = status;
    }

    public int getTotal_number() {
        return total_number;
    }

    public void setTotal_number(int total_number) {
        this.total_number = total_number;
    }

    public static class StatusBean {
        private int attitudes_count;
        private long biz_feature;
        private boolean can_edit;
        private CommentManageInfoBean comment_manage_info;
        private int comments_count;
        private int content_auth;
        private String created_at;
        private int edit_count;
        private boolean favorited;
        private String gif_ids;
        private int hasActionTypeCard;
        private long id;
        private String idstr;
        private String in_reply_to_screen_name;
        private String in_reply_to_status_id;
        private String in_reply_to_user_id;
        private boolean isLongText;
        private boolean is_paid;
        private int is_show_bulletin;
        private int mblog_vip_type;
        private String mid;
        private int mlevel;
        private int more_info_type;
        private int page_type;
        private int pending_approval_count;
        private int positive_recom_flag;
        private int reposts_count;
        private String source;
        private int source_allowclick;
        private int source_type;
        private String text;
        private int textLength;
        private boolean truncated;
        private UserBean user;
        private int userType;
        private VisibleBean visible;
        private List<AnnotationsBean> annotations;
        private List<Integer> biz_ids;
        private List<?> darwin_tags;
        private List<?> hot_weibo_tags;
        private List<?> pic_urls;
        private List<?> text_tag_tips;

        public int getAttitudes_count() {
            return attitudes_count;
        }

        public void setAttitudes_count(int attitudes_count) {
            this.attitudes_count = attitudes_count;
        }

        public long getBiz_feature() {
            return biz_feature;
        }

        public void setBiz_feature(long biz_feature) {
            this.biz_feature = biz_feature;
        }

        public boolean isCan_edit() {
            return can_edit;
        }

        public void setCan_edit(boolean can_edit) {
            this.can_edit = can_edit;
        }

        public CommentManageInfoBean getComment_manage_info() {
            return comment_manage_info;
        }

        public void setComment_manage_info(CommentManageInfoBean comment_manage_info) {
            this.comment_manage_info = comment_manage_info;
        }

        public int getComments_count() {
            return comments_count;
        }

        public void setComments_count(int comments_count) {
            this.comments_count = comments_count;
        }

        public int getContent_auth() {
            return content_auth;
        }

        public void setContent_auth(int content_auth) {
            this.content_auth = content_auth;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public int getEdit_count() {
            return edit_count;
        }

        public void setEdit_count(int edit_count) {
            this.edit_count = edit_count;
        }

        public boolean isFavorited() {
            return favorited;
        }

        public void setFavorited(boolean favorited) {
            this.favorited = favorited;
        }

        public String getGif_ids() {
            return gif_ids;
        }

        public void setGif_ids(String gif_ids) {
            this.gif_ids = gif_ids;
        }

        public int getHasActionTypeCard() {
            return hasActionTypeCard;
        }

        public void setHasActionTypeCard(int hasActionTypeCard) {
            this.hasActionTypeCard = hasActionTypeCard;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getIdstr() {
            return idstr;
        }

        public void setIdstr(String idstr) {
            this.idstr = idstr;
        }

        public String getIn_reply_to_screen_name() {
            return in_reply_to_screen_name;
        }

        public void setIn_reply_to_screen_name(String in_reply_to_screen_name) {
            this.in_reply_to_screen_name = in_reply_to_screen_name;
        }

        public String getIn_reply_to_status_id() {
            return in_reply_to_status_id;
        }

        public void setIn_reply_to_status_id(String in_reply_to_status_id) {
            this.in_reply_to_status_id = in_reply_to_status_id;
        }

        public String getIn_reply_to_user_id() {
            return in_reply_to_user_id;
        }

        public void setIn_reply_to_user_id(String in_reply_to_user_id) {
            this.in_reply_to_user_id = in_reply_to_user_id;
        }

        public boolean isIsLongText() {
            return isLongText;
        }

        public void setIsLongText(boolean isLongText) {
            this.isLongText = isLongText;
        }

        public boolean isIs_paid() {
            return is_paid;
        }

        public void setIs_paid(boolean is_paid) {
            this.is_paid = is_paid;
        }

        public int getIs_show_bulletin() {
            return is_show_bulletin;
        }

        public void setIs_show_bulletin(int is_show_bulletin) {
            this.is_show_bulletin = is_show_bulletin;
        }

        public int getMblog_vip_type() {
            return mblog_vip_type;
        }

        public void setMblog_vip_type(int mblog_vip_type) {
            this.mblog_vip_type = mblog_vip_type;
        }

        public String getMid() {
            return mid;
        }

        public void setMid(String mid) {
            this.mid = mid;
        }

        public int getMlevel() {
            return mlevel;
        }

        public void setMlevel(int mlevel) {
            this.mlevel = mlevel;
        }

        public int getMore_info_type() {
            return more_info_type;
        }

        public void setMore_info_type(int more_info_type) {
            this.more_info_type = more_info_type;
        }

        public int getPage_type() {
            return page_type;
        }

        public void setPage_type(int page_type) {
            this.page_type = page_type;
        }

        public int getPending_approval_count() {
            return pending_approval_count;
        }

        public void setPending_approval_count(int pending_approval_count) {
            this.pending_approval_count = pending_approval_count;
        }

        public int getPositive_recom_flag() {
            return positive_recom_flag;
        }

        public void setPositive_recom_flag(int positive_recom_flag) {
            this.positive_recom_flag = positive_recom_flag;
        }

        public int getReposts_count() {
            return reposts_count;
        }

        public void setReposts_count(int reposts_count) {
            this.reposts_count = reposts_count;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public int getSource_allowclick() {
            return source_allowclick;
        }

        public void setSource_allowclick(int source_allowclick) {
            this.source_allowclick = source_allowclick;
        }

        public int getSource_type() {
            return source_type;
        }

        public void setSource_type(int source_type) {
            this.source_type = source_type;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getTextLength() {
            return textLength;
        }

        public void setTextLength(int textLength) {
            this.textLength = textLength;
        }

        public boolean isTruncated() {
            return truncated;
        }

        public void setTruncated(boolean truncated) {
            this.truncated = truncated;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }

        public VisibleBean getVisible() {
            return visible;
        }

        public void setVisible(VisibleBean visible) {
            this.visible = visible;
        }

        public List<AnnotationsBean> getAnnotations() {
            return annotations;
        }

        public void setAnnotations(List<AnnotationsBean> annotations) {
            this.annotations = annotations;
        }

        public List<Integer> getBiz_ids() {
            return biz_ids;
        }

        public void setBiz_ids(List<Integer> biz_ids) {
            this.biz_ids = biz_ids;
        }

        public List<?> getDarwin_tags() {
            return darwin_tags;
        }

        public void setDarwin_tags(List<?> darwin_tags) {
            this.darwin_tags = darwin_tags;
        }

        public List<?> getHot_weibo_tags() {
            return hot_weibo_tags;
        }

        public void setHot_weibo_tags(List<?> hot_weibo_tags) {
            this.hot_weibo_tags = hot_weibo_tags;
        }

        public List<?> getPic_urls() {
            return pic_urls;
        }

        public void setPic_urls(List<?> pic_urls) {
            this.pic_urls = pic_urls;
        }

        public List<?> getText_tag_tips() {
            return text_tag_tips;
        }

        public void setText_tag_tips(List<?> text_tag_tips) {
            this.text_tag_tips = text_tag_tips;
        }

        public static class CommentManageInfoBean {

            private int approval_comment_type;
            private int comment_manage_button;
            private int comment_permission_type;

            public int getApproval_comment_type() {
                return approval_comment_type;
            }

            public void setApproval_comment_type(int approval_comment_type) {
                this.approval_comment_type = approval_comment_type;
            }

            public int getComment_manage_button() {
                return comment_manage_button;
            }

            public void setComment_manage_button(int comment_manage_button) {
                this.comment_manage_button = comment_manage_button;
            }

            public int getComment_permission_type() {
                return comment_permission_type;
            }

            public void setComment_permission_type(int comment_permission_type) {
                this.comment_permission_type = comment_permission_type;
            }
        }

        public static class UserBean {

            private boolean allow_all_act_msg;
            private boolean allow_all_comment;
            private String avatar_hd;
            private String avatar_large;
            private int bi_followers_count;
            private int block_app;
            private int block_word;
            private String city;
            @SerializedName("class")
            private int classX;
            private String cover_image_phone;
            private String created_at;
            private int credit_score;
            private String description;
            private String domain;
            private int favourites_count;
            private boolean follow_me;
            private int followers_count;
            private boolean following;
            private int friends_count;
            private String gender;
            private boolean geo_enabled;
            private long id;
            private String idstr;
            private InsecurityBean insecurity;
            private String lang;
            private boolean like;
            private boolean like_me;
            private String location;
            private int mbrank;
            private int mbtype;
            private String name;
            private int online_status;
            private int pagefriends_count;
            private String profile_image_url;
            private String profile_url;
            private String province;
            private int ptype;
            private String remark;
            private String screen_name;
            private int star;
            private int statuses_count;
            private int story_read_state;
            private int urank;
            private String url;
            private int user_ability;
            private int vclub_member;
            private boolean verified;
            private String verified_reason;
            private String verified_reason_url;
            private String verified_source;
            private String verified_source_url;
            private String verified_trade;
            private int verified_type;
            private String weihao;
            private int baseObjId;

            public boolean isAllow_all_act_msg() {
                return allow_all_act_msg;
            }

            public void setAllow_all_act_msg(boolean allow_all_act_msg) {
                this.allow_all_act_msg = allow_all_act_msg;
            }

            public boolean isAllow_all_comment() {
                return allow_all_comment;
            }

            public void setAllow_all_comment(boolean allow_all_comment) {
                this.allow_all_comment = allow_all_comment;
            }

            public String getAvatar_hd() {
                return avatar_hd;
            }

            public void setAvatar_hd(String avatar_hd) {
                this.avatar_hd = avatar_hd;
            }

            public String getAvatar_large() {
                return avatar_large;
            }

            public void setAvatar_large(String avatar_large) {
                this.avatar_large = avatar_large;
            }

            public int getBi_followers_count() {
                return bi_followers_count;
            }

            public void setBi_followers_count(int bi_followers_count) {
                this.bi_followers_count = bi_followers_count;
            }

            public int getBlock_app() {
                return block_app;
            }

            public void setBlock_app(int block_app) {
                this.block_app = block_app;
            }

            public int getBlock_word() {
                return block_word;
            }

            public void setBlock_word(int block_word) {
                this.block_word = block_word;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public int getClassX() {
                return classX;
            }

            public void setClassX(int classX) {
                this.classX = classX;
            }

            public String getCover_image_phone() {
                return cover_image_phone;
            }

            public void setCover_image_phone(String cover_image_phone) {
                this.cover_image_phone = cover_image_phone;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public int getCredit_score() {
                return credit_score;
            }

            public void setCredit_score(int credit_score) {
                this.credit_score = credit_score;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getDomain() {
                return domain;
            }

            public void setDomain(String domain) {
                this.domain = domain;
            }

            public int getFavourites_count() {
                return favourites_count;
            }

            public void setFavourites_count(int favourites_count) {
                this.favourites_count = favourites_count;
            }

            public boolean isFollow_me() {
                return follow_me;
            }

            public void setFollow_me(boolean follow_me) {
                this.follow_me = follow_me;
            }

            public int getFollowers_count() {
                return followers_count;
            }

            public void setFollowers_count(int followers_count) {
                this.followers_count = followers_count;
            }

            public boolean isFollowing() {
                return following;
            }

            public void setFollowing(boolean following) {
                this.following = following;
            }

            public int getFriends_count() {
                return friends_count;
            }

            public void setFriends_count(int friends_count) {
                this.friends_count = friends_count;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public boolean isGeo_enabled() {
                return geo_enabled;
            }

            public void setGeo_enabled(boolean geo_enabled) {
                this.geo_enabled = geo_enabled;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getIdstr() {
                return idstr;
            }

            public void setIdstr(String idstr) {
                this.idstr = idstr;
            }

            public InsecurityBean getInsecurity() {
                return insecurity;
            }

            public void setInsecurity(InsecurityBean insecurity) {
                this.insecurity = insecurity;
            }

            public String getLang() {
                return lang;
            }

            public void setLang(String lang) {
                this.lang = lang;
            }

            public boolean isLike() {
                return like;
            }

            public void setLike(boolean like) {
                this.like = like;
            }

            public boolean isLike_me() {
                return like_me;
            }

            public void setLike_me(boolean like_me) {
                this.like_me = like_me;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public int getMbrank() {
                return mbrank;
            }

            public void setMbrank(int mbrank) {
                this.mbrank = mbrank;
            }

            public int getMbtype() {
                return mbtype;
            }

            public void setMbtype(int mbtype) {
                this.mbtype = mbtype;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getOnline_status() {
                return online_status;
            }

            public void setOnline_status(int online_status) {
                this.online_status = online_status;
            }

            public int getPagefriends_count() {
                return pagefriends_count;
            }

            public void setPagefriends_count(int pagefriends_count) {
                this.pagefriends_count = pagefriends_count;
            }

            public String getProfile_image_url() {
                return profile_image_url;
            }

            public void setProfile_image_url(String profile_image_url) {
                this.profile_image_url = profile_image_url;
            }

            public String getProfile_url() {
                return profile_url;
            }

            public void setProfile_url(String profile_url) {
                this.profile_url = profile_url;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public int getPtype() {
                return ptype;
            }

            public void setPtype(int ptype) {
                this.ptype = ptype;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getScreen_name() {
                return screen_name;
            }

            public void setScreen_name(String screen_name) {
                this.screen_name = screen_name;
            }

            public int getStar() {
                return star;
            }

            public void setStar(int star) {
                this.star = star;
            }

            public int getStatuses_count() {
                return statuses_count;
            }

            public void setStatuses_count(int statuses_count) {
                this.statuses_count = statuses_count;
            }

            public int getStory_read_state() {
                return story_read_state;
            }

            public void setStory_read_state(int story_read_state) {
                this.story_read_state = story_read_state;
            }

            public int getUrank() {
                return urank;
            }

            public void setUrank(int urank) {
                this.urank = urank;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public int getUser_ability() {
                return user_ability;
            }

            public void setUser_ability(int user_ability) {
                this.user_ability = user_ability;
            }

            public int getVclub_member() {
                return vclub_member;
            }

            public void setVclub_member(int vclub_member) {
                this.vclub_member = vclub_member;
            }

            public boolean isVerified() {
                return verified;
            }

            public void setVerified(boolean verified) {
                this.verified = verified;
            }

            public String getVerified_reason() {
                return verified_reason;
            }

            public void setVerified_reason(String verified_reason) {
                this.verified_reason = verified_reason;
            }

            public String getVerified_reason_url() {
                return verified_reason_url;
            }

            public void setVerified_reason_url(String verified_reason_url) {
                this.verified_reason_url = verified_reason_url;
            }

            public String getVerified_source() {
                return verified_source;
            }

            public void setVerified_source(String verified_source) {
                this.verified_source = verified_source;
            }

            public String getVerified_source_url() {
                return verified_source_url;
            }

            public void setVerified_source_url(String verified_source_url) {
                this.verified_source_url = verified_source_url;
            }

            public String getVerified_trade() {
                return verified_trade;
            }

            public void setVerified_trade(String verified_trade) {
                this.verified_trade = verified_trade;
            }

            public int getVerified_type() {
                return verified_type;
            }

            public void setVerified_type(int verified_type) {
                this.verified_type = verified_type;
            }

            public String getWeihao() {
                return weihao;
            }

            public void setWeihao(String weihao) {
                this.weihao = weihao;
            }

            public int getBaseObjId() {
                return baseObjId;
            }

            public void setBaseObjId(int baseObjId) {
                this.baseObjId = baseObjId;
            }

            public static class InsecurityBean {
                /**
                 * sexual_content : false
                 */

                private boolean sexual_content;

                public boolean isSexual_content() {
                    return sexual_content;
                }

                public void setSexual_content(boolean sexual_content) {
                    this.sexual_content = sexual_content;
                }
            }
        }

        public static class VisibleBean {

            private int list_id;
            private int type;

            public int getList_id() {
                return list_id;
            }

            public void setList_id(int list_id) {
                this.list_id = list_id;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }

        public static class AnnotationsBean {

            private String client_mblogid;
            private boolean mapi_request;

            public String getClient_mblogid() {
                return client_mblogid;
            }

            public void setClient_mblogid(String client_mblogid) {
                this.client_mblogid = client_mblogid;
            }

            public boolean isMapi_request() {
                return mapi_request;
            }

            public void setMapi_request(boolean mapi_request) {
                this.mapi_request = mapi_request;
            }
        }
    }
}
