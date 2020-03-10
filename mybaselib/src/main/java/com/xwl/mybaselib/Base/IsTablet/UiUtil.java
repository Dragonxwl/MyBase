package com.xwl.mybaselib.Base.IsTablet;

import com.xwl.mybaselib.Base.BaseApplication;
import com.xwl.mybaselib.Base.Config.myConfig;
import com.xwl.mybaselib.R;

public class UiUtil {
	public static boolean isTablet() {
		if (myConfig.getInstance().getAndroidShowTab() != 0) {
			return false;
		}
		if (!myConfig.getInstance().getIsMyShowTab()) {
			return false;
		}
		if (BaseApplication.getContext().getResources() != null) {
			return BaseApplication.getContext().getResources().getBoolean(R.bool.is_tab);
		}
		return false;
	}
	/*public static boolean isPhone() {
		return !isTablet();
	}*/
}
