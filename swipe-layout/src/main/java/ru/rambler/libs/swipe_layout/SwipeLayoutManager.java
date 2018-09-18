package ru.rambler.libs.swipe_layout;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class SwipeLayoutManager
{
	private Map<String, SwipeLayout> layouts = Collections.synchronizedMap(new HashMap<String, SwipeLayout>());

	private final Object syncObject = new Object();

	private boolean openOnlyOne;

	public SwipeLayoutManager()
	{
		this(false);
	}

	public SwipeLayoutManager(boolean openOnlyOne)
	{
		this.openOnlyOne = openOnlyOne;
	}

	public void bind(SwipeLayout swipeLayout, String id)
	{
		layouts.put(id, swipeLayout);

		swipeLayout.setOnSwipeListener(new SwipeLayout.OnSwipeListener() {

			@Override
			public void onBeginSwipe(SwipeLayout swipeLayout, boolean moveToRight)
			{
				if (openOnlyOne)
					closeOthers(swipeLayout, true);
			}

			@Override
			public void onSwipeClampReached(SwipeLayout swipeLayout, boolean moveToRight)
			{

			}

			@Override
			public void onLeftStickyEdge(SwipeLayout swipeLayout, boolean moveToRight)
			{

			}

			@Override
			public void onRightStickyEdge(SwipeLayout swipeLayout, boolean moveToRight)
			{

			}
		});
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

	private void reset(SwipeLayout layoutToClose, boolean animate)
	{
		if (animate) layoutToClose.animateReset();
		else layoutToClose.reset();
	}

	public void setOpenOnlyOne(boolean openOnlyOne)
	{
		this.openOnlyOne = openOnlyOne;
	}

	public boolean isOpenOnlyOne()
	{
		return openOnlyOne;
	}
}
