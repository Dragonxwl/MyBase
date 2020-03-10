package com.xwl.mybase.Base.Config;

import com.xwl.mybase.Base.Application;
import com.xwl.mybase.Base.Utils.SharePreferenceDataUtil;

public class myConfig {
	private static myConfig instance = null;

	public static myConfig getInstance() {
		if (null == instance) {
			instance = new myConfig();
		}
		return instance;
	}

	// 整个android系统是否展示pad页面
	private static final String IS_SHOWTAB = "androidShowTab";//0显示，1隐藏
	// 我自己是否展示pad页面
	private static final String IS_MY_SHOWTAB = "isMyShowTab";

	public int getAndroidShowTab() {
		return SharePreferenceDataUtil.getSharedIntData(Application.getContext(), IS_SHOWTAB);
	}

	/*public void setAndroidShowTab(int androidShowTab) {
		SharePreferenceDataUtil.setSharedIntData(Application.getContext(), IS_SHOWTAB, androidShowTab);
	}*/

	public boolean getIsMyShowTab() {
		return SharePreferenceDataUtil.getSharedBooleanData(Application.getContext(), IS_MY_SHOWTAB, true);
	}

	/*public void setIsMyShowTab(boolean isMyShowTab) {
		SharePreferenceDataUtil.setSharedBooleanData(Application.getContext(), IS_MY_SHOWTAB, isMyShowTab);
	}*/

}
