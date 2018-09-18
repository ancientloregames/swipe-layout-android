package ru.rambler.libs.swipe_layout;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class SwipeLayoutManager
{
	private Map<String, SwipeLayout> layouts = Collections.synchronizedMap(new HashMap<String, SwipeLayout>());

	private final Object syncObject = new Object();

	public void bind(SwipeLayout swipeLayout, String id)
	{
		layouts.put(id, swipeLayout);
	}

	public void closeAll(boolean animate)
	{
		synchronized (syncObject)
		{
			for (SwipeLayout layout: layouts.values())
			{
				reset(layout, animate);
			}
		}
	}

	public void closeOthers(SwipeLayout excludedLayout, boolean animate)
	{
		synchronized (syncObject)
		{
			for (SwipeLayout layout: layouts.values())
			{
				if (layout != excludedLayout)
					reset(layout, animate);
			}
		}
	}

	public void closeLayout(SwipeLayout layout, boolean animate)
	{
		synchronized (syncObject)
		{
			reset(layout, animate);
		}
	}

	public void closeLayout(String id, boolean animate)
	{
		synchronized (syncObject)
		{
			SwipeLayout layout = layouts.get(id);
			if (layout != null)
				reset(layout, animate);
		}
	}

	private void reset(SwipeLayout layoutToClose, boolean animate)
	{
		if (animate) layoutToClose.animateReset();
		else layoutToClose.reset();
	}
}
