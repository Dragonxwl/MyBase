package com.xwl.mybase.Base.IsTablet;

import com.xwl.mybase.Base.Application;
import com.xwl.mybase.Base.Config.myConfig;
import com.xwl.mybase.R;

public class UiUtil {
	public static boolean isTablet() {
		if (myConfig.getInstance().getAndroidShowTab() != 0) {
			return false;
		}
		if (!myConfig.getInstance().getIsMyShowTab()) {
			return false;
		}
		if (Application.getContext().getResources() != null) {
			return Application.getContext().getResources().getBoolean(R.bool.is_tab);
		}
		return false;
	}
	/*public static boolean isPhone() {
		return !isTablet();
	}*/
}
