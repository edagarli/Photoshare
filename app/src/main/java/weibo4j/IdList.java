package weibo4j;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import weibo4j.http.Response;

public class IdList extends WeiboResponse
{

	private List<String> ids = new ArrayList<String>();
	private int nextCursor;
	private int previousCursor;

	private IdList(Response response, Weibo twitter) throws WeiboException
	{
		super(response);
		
	}


	static IdList constructCount(Response response, Weibo twitter)
			throws WeiboException
	{
		Document doc = response.asDocument();
		ensureRootNodeNameIs("id_list", doc);
		IdList idList = new IdList(response, twitter);
		NodeList list = doc.getDocumentElement().getElementsByTagName("ids");
		for (int i = 0; i < list.getLength(); i++)
		{
			Element element = (Element) list.item(i);
			idList.addUserId(element.getTextContent());

		}
		idList.setNextCursor(getChildInt("next_cursor", doc
				.getDocumentElement()));
		idList.setPreviousCursor(getChildInt("previous_cursor", doc
				.getDocumentElement()));

		return idList;
	}

	public void addUserId(String id)
	{
		ids.add(id);
	}

	public int getNextCursor()
	{
		return nextCursor;
	}

	public void setNextCursor(int nextCursor)
	{
		this.nextCursor = nextCursor;
	}

	public int getPreviousCursor()
	{
		return previousCursor;
	}

	public void setPreviousCursor(int previousCursor)
	{
		this.previousCursor = previousCursor;
	}

}
