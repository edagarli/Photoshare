package com.photoshare.entity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import microblog.commons.Const;
import microblog.net.interfaces.MyLog;
import weibo4j.Comment;
import weibo4j.Count;
import weibo4j.DirectMessage;
import weibo4j.MBlog;
import weibo4j.Msg;
import weibo4j.Paging;
import weibo4j.Result;
import weibo4j.Status;
import weibo4j.Weibo;
import weibo4j.WeiboException;
import weibo4j.UnreadCount;
import weibo4j.User;
import weibo4j.UserInfo;
import weibo4j.http.ImageItem;

public class MyMicroBlog implements Const
{

	protected Weibo mWeibo;
	protected String mAccount;
	protected String mPassword;
	protected String mAlias;
	protected MicroBlogListener mMicroBlogListener;
	protected int mLanguage = LANGUAGE_ZH_CN;
	protected static KeySecret mKeySecret;

	public static MyLog myLog;

	public String getAccount()
	{
		return mWeibo.getUserId();
	}

	public String getPassword()
	{
		return mWeibo.getPassword();
	}

	public void setAccount(String account)
	{
		this.mAccount = account;
		mWeibo.setUserId(account);
	}

	public void setPassword(String password)
	{
		mPassword = password;
		mWeibo.setPassword(password);
	}



	public String getAlias()
	{
		return mAlias;
	}

	public void setAlias(String alias)
	{
		this.mAlias = alias;
	}

	public static KeySecret getKeySecret()
	{
		return mKeySecret;
	}

	public MicroBlogListener getMicroBlogListener()
	{
		return mMicroBlogListener;
	}

	public void setMicroBlogListener(MicroBlogListener microBlogListener)
	{
		mMicroBlogListener = microBlogListener;
	}

	public int getLanguage()
	{
		return mLanguage;
	}

	public void setLanguage(int language)
	{
		this.mLanguage = mLanguage;
	}

	public MyMicroBlog(String account, String password, KeySecret keySecret)
	{

		if (keySecret == null)
		{
			mKeySecret = new KeySecret();
		}
		else
		{
			mKeySecret = keySecret;
		}
		setKeySecret();
		mWeibo = new Weibo(account, password, getBaseURL(), this);
	}

	public MyMicroBlog(String account, String password)
	{
		this(account, password, null);
	}

	// /////////////public////////////////

	public User login() throws Exception
	{

		User user = mWeibo.verifyCredentials();
		return user;

	}

	public Status updateStatus(String msg) throws Exception
	{

		return mWeibo.updateStatus(msg, "");

	}

	public Status updateStatus(String msg, ImageItem item) throws Exception
	{

		return mWeibo.uploadStatus(msg, item);
	}

	public Status updateStatus(String msg, InputStream is) throws Exception
	{

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int count = 0;
		while ((count = is.read(buffer)) > 0)
		{
			baos.write(buffer, 0, count);
		}

		ImageItem item = new ImageItem("pic", baos.toByteArray());
		Status status = mWeibo.uploadStatus(msg, item);
		baos.close();
		return status;

	}

	public Status updateStatus(String msg, byte[] image) throws Exception
	{
		ByteArrayInputStream bais = new ByteArrayInputStream(image);

		return updateStatus(msg, bais);

	}

	public Status updateStatus(String msg, String filename) throws Exception
	{

		FileInputStream fis = new FileInputStream(filename);
		Status status = updateStatus(msg, fis);
		fis.close();
		return status;

	}

	public List<Status> getHomeTimeline() throws Exception
	{
		return getFriendsTimeline();
	}

	public List<Status> getHomeTimeline(Paging paging) throws Exception
	{
		List<Status> statuses = mWeibo.getFriendsTimeline(paging);
		return statuses;
	}

	public List<Status> getFriendsTimeline() throws Exception
	{
		List<Status> statuses = mWeibo.getFriendsTimeline();

		// myLog.write("sssssssssss", statuses.get(2).getText());
		return statuses;
	}

	public List<Status> getUserTimeline() throws Exception
	{

		List<Status> statuses = mWeibo.getUserTimeline();
		return statuses;
	}

	public List<Status> getUserTimeline(Paging paging) throws Exception
	{

		List<Status> statuses = mWeibo.getUserTimeline(paging);
		return statuses;
	}

	public List<Status> getMentions() throws Exception
	{
		List<Status> statuses = mWeibo.getMentions();
		return statuses;
	}

	public List<Status> getMentions(Paging paging) throws Exception
	{
		List<Status> statuses = mWeibo.getMentions(paging);
		return statuses;
	}

	public List<Status> getPublicTimeline() throws Exception
	{

		List<Status> statuses = mWeibo.getPublicTimeline();
		return statuses;
	}

	public List<Comment> getCommentsTimeline() throws Exception
	{

		List<Comment> comments = mWeibo.getCommentsTimeline();
		return comments;
	}

	public List<Comment> getCommentsTimeline(Paging paging) throws Exception
	{

		List<Comment> comments = mWeibo.getCommentsTimeline(paging);
		return comments;
	}

	// �������Լ������Լ���comment
	public List<Comment> getComments(String statusId) throws Exception
	{

		List<Comment> comments = mWeibo.getComments(statusId);
		return comments;
	}

	public List<Comment> getComments(String statusId, Paging paging)
			throws Exception
	{

		List<Comment> comments = mWeibo.getComments(statusId, paging);
		return comments;
	}

	public List<Comment> getCommentsByMe() throws Exception
	{

		List<Comment> comments = mWeibo.getCommentsByMe();
		return comments;
	}

	public List<Comment> getCommentsByMe(Paging paging) throws Exception
	{

		List<Comment> comments = mWeibo.getCommentsByMe(paging);
		return comments;
	}

	public Count getCount(String id) throws Exception
	{
		Count count = mWeibo.getCount(id);
		return count;
	}

	public List<Count> getCountList(String... ids) throws Exception
	{

		List<Count> countList = mWeibo.getCountList(ids);

		return countList;
	}

	public UnreadCount getUnreadCount() throws Exception
	{
		UnreadCount unreadCount = mWeibo.getUnreadCount();
		return unreadCount;
	}

	public Status showStatus(String id) throws Exception
	{
		Status status = mWeibo.showStatus(id);

		return status;
	}

	public String getWebUrl(String statusId, String userId) throws Exception
	{

		return mWeibo.getWebUrl(statusId, userId);
	}

	public Status destroyStatus(String statusId) throws Exception
	{

		return mWeibo.destroyStatus(statusId);
	}

	// ת��
	public Status repost(String statusId, String status) throws Exception
	{

		if (status == null)
			return mWeibo.retweetStatus(statusId);
		else
			return mWeibo.retweetStatus(statusId, status);

	}

	public Result sinaRepost(String id, String mbloguid, String status)
			throws Exception
	{
		return mWeibo.retweetSinaStatus(id, mbloguid, status);
	}

	public Status repost(String statusId) throws Exception
	{

		mWeibo.retweetStatus(statusId, null);
		return null;
	}

	public Comment updateComment(String comment, String statusId)
			throws Exception
	{

		return updateComment(comment, statusId, null, false, null);
	}

	public Comment updateComment(String comment, String statusId,
			boolean updateStatus, String status) throws Exception
	{

		return updateComment(comment, statusId, null, updateStatus, status);
	}

	public Result updateSinaComment(String comment, String srcid, String srcuid)
			throws WeiboException
	{
		return mWeibo.updateSinaComment(comment, srcid, srcuid);
	}

	public Comment updateComment(String comment, String statusId,
			String commentId) throws Exception
	{

		return updateComment(comment, statusId, commentId, false, null);

	}

	public Comment updateComment(String comment, String statusId,
			String commentId, boolean updateStatus, String status)
			throws Exception
	{
		if (updateStatus)
		{
			mWeibo.updateStatus(status, Long.parseLong(statusId));

		}

		return mWeibo.updateComment(comment, statusId, commentId);
	}

	public Comment reply(String commentStr, String status, String statusId,
			String commentId, boolean postStatus) throws Exception
	{

		Comment comment = mWeibo.reply(commentStr, statusId, commentId);
		if (postStatus)
		{
			mWeibo.retweetStatus(statusId, status);
		}

		return comment;
	}

	public User showUser(String userId) throws Exception
	{

		return mWeibo.showUser(userId);
	}

	public List<User> getFriendList(String userId) throws Exception
	{
		return mWeibo.getFriendList(userId, 20, 1);
	}

	public List<User> getFriendList(String userId, int count, int page)
			throws Exception
	{
		return mWeibo.getFriendList(userId, count, page);
	}

	public List<User> getFollowerList(String userId) throws Exception
	{
		return mWeibo.getFollowerList(userId, 20, 1);
	}

	public List<User> getFollowerList(String userId, int count, int page)
			throws Exception
	{

		return mWeibo.getFollowerList(userId, count, page);
	}

	public DirectMessage sendDirectMessage(String id, String text)
			throws Exception
	{
		DirectMessage directMessage = mWeibo.sendDirectMessage(id, text);

		return directMessage;
	}

	public List<DirectMessage> getDirectMessages(Paging paging)
			throws Exception
	{
		List<DirectMessage> directMessages = mWeibo.getDirectMessages(paging);
		return directMessages;
	}

	public List<DirectMessage> getDirectMessages() throws Exception
	{
		List<DirectMessage> directMessages = mWeibo.getDirectMessages();

		return directMessages;
	}

	public List<DirectMessage> getSentDirectMessages(Paging paging)
			throws Exception
	{
		List<DirectMessage> directMessages = mWeibo
				.getSentDirectMessages(paging);
		return directMessages;
	}

	public List<DirectMessage> getSentDirectMessages() throws Exception

	{
		List<DirectMessage> directMessages = mWeibo.getSentDirectMessages();
		// mWeibo.destroyDirectMessage(id)
		return directMessages;
	}

	public DirectMessage destroyDirectMessage(String id) throws Exception
	{

		return mWeibo.destroyDirectMessage(id);
	}

	public User createFriendship(String id) throws Exception
	{
		return mWeibo.createFriendship(id);
	}

	public User destroyFriendship(String id) throws Exception
	{

		return mWeibo.destroyFriendship(id);
	}

	public int showFriendship(String userID1, String userID2) throws Exception
	{
		return mWeibo.getFriendship(userID1, userID2);

	}

	public Status createFavorite(String statusId) throws Exception
	{

		return mWeibo.createFavorite(statusId);
	}

	public Result createSinaFavorite(String id) throws WeiboException
	{
		return mWeibo.createSinaFavorite(id);
	}

	public Status destroyFavorite(String statusId) throws Exception
	{

		return mWeibo.destroyFavorite(statusId);
	}

	public List<Status> getFavorites() throws Exception
	{
		return mWeibo.getFavorites();
	}

	public List<Status> getFavorites(int page) throws Exception
	{
		return mWeibo.getFavorites(page);
	}

	public List<Status> getFavorites(Paging paging) throws Exception
	{
		return mWeibo.getFavorites(paging);
	}

	public boolean register() throws Exception
	{

		return mWeibo.register();
	}

	public User updateProfile(String name, String email, String url,
			String location, String description) throws Exception
	{
		return mWeibo.updateProfile(name, email, url, location, description);
	}

	public User updateProfileImage(String profileImage) throws Exception
	{
		FileInputStream fis = new FileInputStream(profileImage);
		return updateProfileImage(fis);
	}

	public User updateProfileImage(InputStream is) throws Exception
	{

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int count = 0;
		while ((count = is.read(buffer)) > 0)
		{
			baos.write(buffer, 0, count);
		}

		ImageItem imageItem = new ImageItem("image", baos.toByteArray());
		is.close();
		baos.close();
		return mWeibo.updateProfileImage(imageItem);
	}

	// ///////////////////////////////////

	// /////////////private///////////////
	public String getBaseURL()
	{
		return "api.t.sina.com.cn";
	}

	public void setKeySecret()
	{

		Weibo.CONSUMER_KEY = mKeySecret.getConsumerKey();
		Weibo.CONSUMER_SECRET = mKeySecret.getConsumerSecret();

	}

	public List<Msg> getDirectMessagesExt(Paging paging) throws WeiboException
	{
		return mWeibo.getDirectMessagesExt(paging);
	}

	public List<Msg> getDirectMessagesExt(Paging paging, String userId)
			throws WeiboException
	{
		return mWeibo.getDirectMessagesExt(paging, userId);
	}

	public List<MBlog> searchMBlog(Paging paging, String keyword)
			throws Exception
	{
		return mWeibo.searchMBlog(paging, keyword);
	}

	public List<UserInfo> searchUser(Paging paging, String keyword)
			throws Exception
	{
		return mWeibo.searchUser(paging, keyword);
	}

	// ///////////////////////////////////
	public List<Status> searchMicroBlog(Paging paging) throws Exception
	{
		return mWeibo.searchMicroBlog(paging);
	}

	public List<User> searchUsers(Paging paging) throws Exception
	{
		return mWeibo.searchUsers(paging);
	}
}
