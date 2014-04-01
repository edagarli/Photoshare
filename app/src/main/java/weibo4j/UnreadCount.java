package weibo4j;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import weibo4j.http.Response;

public class UnreadCount extends WeiboResponse
{
	private long id;
	private int comments;
	private int mentions;
	private int dm;
	private int followers;

	public int getComments()
	{
		return comments;
	}

	public void setComments(int comments)
	{
		this.comments = comments;
	}

	public int getMentions()
	{
		return mentions;
	}

	public void setMentions(int mentions)
	{
		this.mentions = mentions;
	}

	public int getDm()
	{
		return dm;
	}

	public void setDm(int dm)
	{
		this.dm = dm;
	}

	public int getFollowers()
	{
		return followers;
	}

	public void setFollowers(int followers)
	{
		this.followers = followers;
	}

	private UnreadCount(Response response, Element element, Weibo twitter)
			throws WeiboException
	{
		super(response);
		init(response, element, twitter);
	}

	private void init(Response response, Element elem, Weibo twitter)
			throws WeiboException
	{

		ensureRootNodeNameIs("count", elem);

		comments = getChildInt("comments", elem);
		mentions = getChildInt("mentions", elem);
		dm = getChildInt("dm", elem);
		followers = getChildInt("followers", elem);

	}

	static UnreadCount constructCount(Response response, Weibo twitter)
			throws WeiboException
	{
		Document doc = response.asDocument();
		return new UnreadCount(response, doc.getDocumentElement(), twitter);

	}

	

}
