/*
Copyright (c) 2007-2009, Yusuke Yamamoto
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
 * Neither the name of the Yusuke Yamamoto nor the
      names of its contributors may be used to endorse or promote products
      derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY Yusuke Yamamoto ``AS IS'' AND ANY
EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL Yusuke Yamamoto BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package weibo4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import microblog.MyUtil;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import weibo4j.http.HTMLEntity;
import weibo4j.http.Response;
import weibo4j.org.json.JSONException;
import weibo4j.org.json.JSONObject;

/**
 * A data class representing one single status of a user.
 * 
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
public class MBlog extends WeiboResponse implements java.io.Serializable
{


	
	public String userId;
	public String mblogId;
	public String nick;
	public String portrait;
	public int vip;
	public int rtNum;
	public int commentNum;
	public String content;
	public String rtRootUserId;
	public String rtRootNick;
	public int rtRootVip;
	public String rtReason;
	public Date time;
	public String pic;
	public String source;

	private static final long serialVersionUID = 1608000492860584608L;

	/* package */MBlog(Response res, Weibo twitter) throws WeiboException
	{

		super(res);

		Element elem = res.asDocument().getDocumentElement();

		init(res, elem, twitter);

	}

	/* package */MBlog(Response res, Element elem, Weibo twitter)
			throws WeiboException
	{
		super(res);
		init(res, elem, twitter);
	}

	private void init(Response res, Element elem, Weibo twitter)
			throws WeiboException
	{

		
		userId = getChildText("uid", elem);
		mblogId = getChildText("mblogid", elem);
		nick = getChildText("nick", elem);
		portrait = getChildText("portrait", elem);
		vip = getChildInt("vip", elem);
		rtNum = getChildInt("rtnum", elem);
		commentNum = getChildInt("commentnum", elem);
		content = getChildText("content", elem);
		rtRootUserId = getChildText("rtrootuid", elem);
		rtRootNick = getChildText("rtrootnick", elem);
		rtRootVip = getChildInt("rtrootvip", elem);
		rtReason = getChildText("rtreason", elem);
		time = new Date(getChildLong("time", elem) * 1000);
		pic = getChildText("pic", elem);
		source = getChildText("source", elem);

	}

	/* package */
	public static List<MBlog> constructMBlogs(Response res, Weibo twitter)
			throws WeiboException
	{
		Document doc = res.asDocument();

		if (isRootNodeNilClasses(doc))
		{
			return new ArrayList<MBlog>(0);
		}
		else
		{
			try
			{
				ensureRootNodeNameIs("rss", doc);
				NodeList list = doc.getDocumentElement().getElementsByTagName(
						"mblog");

				int size = list.getLength();
				List<MBlog> mblogs = new ArrayList<MBlog>(size);

				for (int i = 0; i < size; i++)
				{
					Element mblog = (Element) list.item(i);

					mblogs.add(new MBlog(res, mblog, twitter));
				}

				return mblogs;
			}
			catch (WeiboException te)
			{

				ensureRootNodeNameIs("nil-classes", doc);
				return new ArrayList<MBlog>(0);
			}

		}

	}

	@Override
	public int hashCode()
	{
		return mblogId.hashCode();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (null == obj)
		{
			return false;
		}
		if (this == obj)
		{
			return true;
		}
		return obj instanceof MBlog && ((MBlog) obj).mblogId.equals(this.mblogId);
	}

	public String asString() throws Exception
	{
		return res.asString();
	}
}
