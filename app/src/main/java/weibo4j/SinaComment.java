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
public class SinaComment extends WeiboResponse implements
		java.io.Serializable
{

	public String uid;
	public String nick;
	public String commentrt;
	public String content;
	public String cmtid;
	public String cmtuid;
	public Date time;

	private static final long serialVersionUID = 1608000492860584608L;

	/* package */SinaComment(Response res, Weibo twitter)
			throws WeiboException
	{

		super(res);

		Element elem = res.asDocument().getDocumentElement();

		init(res, elem, twitter);

	}

	/* package */SinaComment(Response res, Element elem, Weibo twitter)
			throws WeiboException
	{
		super(res);
		init(res, elem, twitter);
	}

	private void init(Response res, Element elem, Weibo twitter)
			throws WeiboException
	{

		uid = getChildText("uid", elem);
		nick = getChildText("nick", elem);
		commentrt = getChildText("commentrt", elem);
		content = getChildText("content", elem);
		cmtid = getChildText("cmtid", elem);
		cmtuid = getChildText("cmtuid", elem);
		time = new Date(getChildLong("time", elem) * 1000);
	}

	/* package */
	public static List<SinaComment> constructComments(Response res, Weibo twitter)
			throws WeiboException
	{
		Document doc = res.asDocument();

		if (isRootNodeNilClasses(doc))
		{
			return new ArrayList<SinaComment>(0);
		}
		else
		{
			try
			{
				ensureRootNodeNameIs("rss", doc);
				NodeList list = doc.getDocumentElement().getElementsByTagName(
						"comment");

				int size = list.getLength();
				List<SinaComment> comments = new ArrayList<SinaComment>(size);

				for (int i = 0; i < size; i++)
				{
					Element comment = (Element) list.item(i);

					comments.add(new SinaComment(res, comment, twitter));
				}

				return comments;
			}
			catch (WeiboException te)
			{

				ensureRootNodeNameIs("nil-classes", doc);
				return new ArrayList<SinaComment>(0);
			}

		}

	}

	@Override
	public int hashCode()
	{
		return cmtid.hashCode();
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
		return obj instanceof SinaComment
				&& ((SinaComment) obj).cmtid.equals(this.cmtid);
	}

	public String asString() throws Exception
	{
		return res.asString();
	}
}
